package com.boleo.internal.tests;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class TreeCrawler {

    public Map<Integer, Node> traverse(TaskArguments taskArguments, ExecutorService threadPool, int nThreads) {
        Node zero = new Node(0, taskArguments.zeroScore);
        taskArguments.processed.put(0, zero);
        taskArguments.stack.push(taskArguments.top);
        Collection<Future<?>> futures = new ArrayList<>(nThreads);
        for (int i = 0; i < nThreads; i++) {
            futures.add(threadPool.submit(() -> this.work(taskArguments)));
        }
        for (Future<?> future : futures) {
            try {
                future.get();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (ExecutionException e) {
                throw new RuntimeException(e.getCause());
            }
        }
        return taskArguments.processed;
    }

    private void work(TaskArguments taskArguments) {
        LinkedList<Integer> children = new LinkedList<>();
        LinkedList<Integer> unprocessedChildren = new LinkedList<>();
        while (!taskArguments.completed.get()) {
            Integer nodeValue = popNext(taskArguments);
            if (nodeValue == null) {
                continue;
            }

            if (taskArguments.processed.containsKey(nodeValue) || taskArguments.deadEnds.contains(nodeValue)) {
                continue;
            }

            children.clear();
            for (Integer step : taskArguments.steps) {
                int childValue = nodeValue - step;
                if (childValue < 0 || taskArguments.deadEnds.contains(childValue)) {
                    continue;
                }
                children.add(childValue);
            }

            if (children.isEmpty()) {
                taskArguments.deadEnds.add(nodeValue);
                continue;
            }

            unprocessedChildren.clear();
            for (int child : children) {
                if (!taskArguments.processed.containsKey(child)) {
                    unprocessedChildren.add(child);
                }
            }
            if (unprocessedChildren.isEmpty()) {
                Collection<Node> childNodes = children.stream().map(taskArguments.processed::get).collect(Collectors.toList());
                Node completedNode = new Node(nodeValue, taskArguments.scoreFunction.apply(nodeValue, childNodes));
                completedNode.getChildren().addAll(childNodes);
                taskArguments.processed.put(nodeValue, completedNode);
            } else {
                unprocessedChildren.addFirst(nodeValue);
                pushMany(unprocessedChildren, taskArguments);
            }
        }
    }

    private Integer popNext(TaskArguments taskArguments) {
        boolean gotLock = false;
        try {
            gotLock = taskArguments.stackLock.tryLock(100, TimeUnit.MILLISECONDS);
            if (!gotLock) {
                return null;
            }
            if (taskArguments.stack.isEmpty()) {
                taskArguments.completed.set(true);
                return null;
            }
            return taskArguments.stack.pop();

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return null;
        } finally {
            if (gotLock) {
                taskArguments.stackLock.unlock();
            }
        }
    }

    private void pushMany(Collection<Integer> data, TaskArguments taskArguments) {
        boolean gotLock = false;
        try {
            gotLock = taskArguments.stackLock.tryLock(100, TimeUnit.MILLISECONDS);
            if (!gotLock) {
                return;
            }
            data.forEach(taskArguments.stack::push);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            if (gotLock) {
                taskArguments.stackLock.unlock();
            }
        }
    }

    public static class Node {
        private final int value;
        private final Collection<Node> children;
        private final long score;

        private Node(int value, long score) {
            this.value = value;
            this.score = score;
            this.children = new LinkedList<>();
        }

        public int getValue() {
            return value;
        }

        public Collection<Node> getChildren() {
            return children;
        }

        public long getScore() {
            return score;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "value=" + value +
                    ", children=" + children +
                    ", score=" + score +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Node)) return false;
            Node node = (Node) o;
            return value == node.value;
        }

        @Override
        public int hashCode() {
            return Objects.hash(value, children, score);
        }
    }

    public static class TaskArguments {
        private final ReentrantLock stackLock = new ReentrantLock();
        private final LinkedList<Integer> stack;
        private final Map<Integer, Node> processed;
        private final ConcurrentSkipListSet<Integer> deadEnds;

        private final AtomicBoolean completed = new AtomicBoolean(false);
        private final List<Integer> steps;
        private final BiFunction<Integer, Collection<Node>, Long> scoreFunction;
        private final int top;
        private final long zeroScore;

        public TaskArguments(Collection<Integer> steps, BiFunction<Integer, Collection<Node>, Long> scoreFunction, int top, long zeroScore) {
            this.steps = steps.stream().sorted().collect(Collectors.toList());
            this.scoreFunction = scoreFunction;
            this.top = top;
            this.zeroScore = zeroScore;
            this.stack = new LinkedList<>();
            this.processed = new ConcurrentHashMap<>();
            this.deadEnds = new ConcurrentSkipListSet<>();
        }
    }
}
