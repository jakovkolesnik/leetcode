package com.boleo.internal.tests;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

public class PossibleTermsToMakeSum {

    public static int[] getTerms(int[] terms, int goal) {
        List<Integer> sortedTerms = Arrays.stream(terms).boxed().distinct().sorted().collect(Collectors.toList());

        Map<Integer, Node> nodes = new HashMap<>();
        Collection<Integer> deadEnds = new HashSet<>();
        Stack<Integer> stack = new Stack<>();
        stack.push(goal);

        Node zero = new Node(0, 0);
        nodes.put(0, zero);

        List<Integer> children = new ArrayList<>();
        while (!stack.empty()) {
            int nodeValue = stack.peek();

            if (nodes.containsKey(nodeValue) || deadEnds.contains(nodeValue)) {
                stack.pop();
                continue;
            }

            children.clear();
            for (int i = 0; i < sortedTerms.size(); i++) {
                int childValue = nodeValue - terms[i];
                if (childValue < 0 || deadEnds.contains(childValue)) {
                    continue;
                }
                children.add(childValue);
            }
            if (children.isEmpty()) {
                deadEnds.add(nodeValue);
                stack.pop();
                continue;
            }
            boolean finished = true;
            for (int child : children) {
                if (!nodes.containsKey(child)) {
                    finished = false;
                    stack.push(child);
                }
            }
            if (finished) {
                int minChild = -1;
                int minSteps = -1;
                for (Integer child : children) {
                    Node childNode = nodes.get(child);
                    if (minSteps < 0 || childNode.getNumberOfSteps() < minSteps) {
                        minSteps = childNode.getNumberOfSteps();
                        minChild = child;
                    }
                }
                Node node = new Node(nodeValue, minSteps + 1);
                node.getChildren().add(nodes.get(minChild));
                nodes.put(nodeValue, node);
                stack.pop();
            }
        }
        Node node = nodes.get(goal);
        if (node == null) {
            return null;
        }

        List<Integer> result = new ArrayList<>();
        while (!node.getChildren().isEmpty()) {
            Node child = node.getChildren().get(0);
            result.add(node.getValue() - child.getValue());
            node = child;
        }

        return result.stream().mapToInt(i -> i).toArray();
    }

    @Test
    void figureOutTerms() {
        Character foo = null;
        System.out.println(foo);
        int[] terms = new int[]{1, 2, 5, 10, 20, 50};
        System.out.println(Arrays.toString(getTerms(terms, 192)));
    }

    private static class Node {
        private final int value;
        private final List<Node> children;
        private final int numberOfSteps;

        private Node(int value, int numberOfSteps) {
            this.value = value;
            this.numberOfSteps = numberOfSteps;
            this.children = new ArrayList<>();
        }

        public int getValue() {
            return value;
        }

        public List<Node> getChildren() {
            return children;
        }

        public int getNumberOfSteps() {
            return numberOfSteps;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "value=" + value +
                    ", children=" + children +
                    ", numberOfSteps=" + numberOfSteps +
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
            return Objects.hash(value, children, numberOfSteps);
        }
    }
}
