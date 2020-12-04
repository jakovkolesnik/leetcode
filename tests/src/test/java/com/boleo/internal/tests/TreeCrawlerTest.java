package com.boleo.internal.tests;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.function.BiFunction;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TreeCrawlerTest {

    @Test
    void NumberOfWaysToReachTopOfTheLadder() {
        Set<Integer> steps = new HashSet<>(Arrays.asList(3, 5));
        int top = 18;
        BiFunction<Integer, Collection<TreeCrawler.Node>, Long> scoreFunction = (score, children) -> children.stream().mapToLong(TreeCrawler.Node::getScore).sum();
        TreeCrawler.TaskArguments taskArguments = new TreeCrawler.TaskArguments(steps, scoreFunction, top, 1);

        Map<Integer, TreeCrawler.Node> result = new TreeCrawler().traverse(taskArguments, Executors.newFixedThreadPool(4), 4);

        TreeCrawler.Node node = result.get(top);
        System.out.println("Number of ways is: " + node.getScore());
        assertEquals(4410L, node.getScore());
    }

    @Test
    void CoinChange() {
        Set<Integer> steps = new HashSet<>(Arrays.asList(1, 2, 5, 10, 20, 50));
        int top = 999999;
        BiFunction<Integer, Collection<TreeCrawler.Node>, Long> scoreFunction = (value, children) -> children.stream().mapToLong(TreeCrawler.Node::getScore).min().orElse(0) + 1;
        TreeCrawler.TaskArguments taskArguments = new TreeCrawler.TaskArguments(steps, scoreFunction, top, 0);

        Map<Integer, TreeCrawler.Node> result = new TreeCrawler().traverse(taskArguments, Executors.newFixedThreadPool(2), 1);

        TreeCrawler.Node node = result.get(top);

        List<Integer> change = new ArrayList<>();
        while (node != null && !node.getChildren().isEmpty()) {
            TreeCrawler.Node child = node.getChildren().stream().min(Comparator.comparing(TreeCrawler.Node::getScore)).orElse(null);
            if (child != null) {
                change.add(node.getValue() - child.getValue());
            }
            node = child;
        }

        System.out.println("Best change for " + top + " is : " + change);
    }

    @Test
    void CoinChangeSingleThread() {
        Set<Integer> steps = new HashSet<>(Arrays.asList(1, 2, 5, 10, 20, 50));
        int top = 999999;
        BiFunction<Integer, Collection<SingleThreadedTreeCrawler.Node>, Long> scoreFunction = (value, children) -> children.stream().mapToLong(SingleThreadedTreeCrawler.Node::getScore).min().orElse(0) + 1;
        SingleThreadedTreeCrawler.TaskArguments taskArguments = new SingleThreadedTreeCrawler.TaskArguments(steps, scoreFunction, top, 0);

        Map<Integer, SingleThreadedTreeCrawler.Node> result = new SingleThreadedTreeCrawler().apply(taskArguments);

        SingleThreadedTreeCrawler.Node node = result.get(top);

        List<Integer> change = new ArrayList<>();
        while (node != null && !node.getChildren().isEmpty()) {
            SingleThreadedTreeCrawler.Node child = node.getChildren().stream().min(Comparator.comparing(SingleThreadedTreeCrawler.Node::getScore)).orElse(null);
            if (child != null) {
                change.add(node.getValue() - child.getValue());
            }
            node = child;
        }

        System.out.println("Best change for " + top + " is : " + change);
    }
}