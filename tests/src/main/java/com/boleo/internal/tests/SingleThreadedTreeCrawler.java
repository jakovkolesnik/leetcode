package com.boleo.internal.tests;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SingleThreadedTreeCrawler implements Function<SingleThreadedTreeCrawler.TaskArguments, Map<Integer, SingleThreadedTreeCrawler.Node>> {

    public Map<Integer, SingleThreadedTreeCrawler.Node> apply(TaskArguments taskArguments) {
        Node zero = new Node(0, taskArguments.zeroScore);
        taskArguments.processed.put(0, zero);
        taskArguments.stack.push(taskArguments.top);
        work(taskArguments);
        return taskArguments.processed;
    }

    private void work(TaskArguments taskArguments) {
        LinkedList<Integer> children = new LinkedList<>();
        LinkedList<Integer> unprocessedChildren = new LinkedList<>();
        while (!taskArguments.stack.isEmpty()) {
            Integer nodeValue = taskArguments.stack.pop();
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
                unprocessedChildren.forEach(taskArguments.stack::push);
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
        private final Deque<Integer> stack;
        private final Map<Integer, Node> processed;
        private final Set<Integer> deadEnds;

        private final List<Integer> steps;
        private final BiFunction<Integer, Collection<Node>, Long> scoreFunction;
        private final int top;
        private final long zeroScore;

        public TaskArguments(Collection<Integer> steps, BiFunction<Integer, Collection<Node>, Long> scoreFunction, int top, long zeroScore) {
            this.steps = steps.stream().sorted().collect(Collectors.toList());
            if (steps.stream().anyMatch(step -> step < 1)) {
                throw new IllegalArgumentException("Steps must be greater than 0");
            }
            if (steps.isEmpty()) {
                throw new IllegalArgumentException("Steps are empty");
            }
            this.scoreFunction = scoreFunction;
            this.top = top;
            this.zeroScore = zeroScore;
            this.stack = new LinkedList<>();
            this.processed = new HashMap<>();
            this.deadEnds = new HashSet<>();
        }
    }
}
