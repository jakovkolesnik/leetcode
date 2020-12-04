package com.boleo.internal.tests;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

public class NumberOfWaysToReachTopOfTheLadder {

    public static int[] getNumberOfWays(int[] terms, int goal) {
        List<Integer> sortedTerms = Arrays.stream(terms).boxed().distinct().sorted().collect(Collectors.toList());

        Map<Integer, Node> nodes = new HashMap<>();
        Collection<Integer> deadEnds = new HashSet<>();
        Stack<Integer> stack = new Stack<>();
        stack.push(goal);

        Node zero = new Node(0, 1);
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
                int childValue = nodeValue - sortedTerms.get(i);
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
            System.out.println(nodeValue + " : " + children);
            if (finished) {
                int numberOfWays = 1;
                List<Node> childrenNodes = new ArrayList<>();
                for (Integer child : children) {
                    Node childNode = nodes.get(child);
                    childrenNodes.add(childNode);
                    numberOfWays = numberOfWays + childNode.getNumberOfWays();
                }
                Node node = new Node(nodeValue, numberOfWays);
                node.children.addAll(childrenNodes);
                nodes.put(nodeValue, node);
                System.out.println(node);
                stack.pop();
            }
        }
        Node node = nodes.get(goal);
        if (node == null) {
            return null;
        }
        System.out.println("Number of ways - " + node.numberOfWays);
        return null;
    }

    @Test
    void figureOutTerms() {
        int[] terms = new int[]{2, 3};
        System.out.println(Arrays.toString(getNumberOfWays(terms, 6)));
    }

    private static class Node {
        private final int value;
        private final List<Node> children;
        private final int numberOfWays;

        private Node(int value, int numberOfWays) {
            this.value = value;
            this.numberOfWays = numberOfWays;
            this.children = new ArrayList<>();
        }

        public int getValue() {
            return value;
        }

        public List<Node> getChildren() {
             return children;
        }

        public int getNumberOfWays() {
            return numberOfWays;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "value=" + value +
                    ", children=" + children +
                    ", numberOfWays=" + numberOfWays +
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
            return Objects.hash(value, children, numberOfWays);
        }
    }
}
