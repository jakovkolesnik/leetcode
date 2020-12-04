package com.boleo.internal.tests;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Generics {

    private static class VisitorImpl implements Visitor {
        @Override
        public void accept(Fruit fruit) {
            System.out.println("Fruit");
        }

        @Override
        public void accept(Apple fruit) {
            System.out.println("Apple");
        }

        @Override
        public void accept(Banana fruit) {
            System.out.println("Banana");
        }

    }
    @Test
    public void test() throws Exception {
        List<Fruit> fruitList = new ArrayList<>();
        fruitList.add(new Apple());
        fruitList.add(new Banana());
        Visitor visitor = new VisitorImpl();

        fruitList.forEach(visitor::accept);

        fruitList.forEach(f -> f.accept(visitor));

        System.out.println(1%10);
    }

    private static class Apple implements Fruit {
        @Override
        public void taste() {
            System.out.println("delicious..");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.accept(this);
        }

    }

    private void tasteFruits(Collection<? extends Fruit> fruits) {
        for (Fruit fruit : fruits) {
            fruit.taste();
        }
    }

    private static class Banana implements Fruit {
        @Override
        public void taste() {
            System.out.println("mmm... Banana!");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.accept(this);
        }
    }

    private interface Fruit {
        void taste();
        void accept(Visitor visitor);
    }

    private void addFruit(Collection<? extends Fruit> fruits) {
        Fruit fruit = new Banana();
        //fruits.add(fruit);
    }

    private <T extends Fruit> void addFruit(Collection<T> fruits, Fruit fruit) {
        //fruits.add(fruit);RuntimeException
    }

    private <T extends Fruit> void addSpecificFruit(Collection<T> fruits, T fruit) {
        fruits.add(fruit);
    }

    private interface Visitor {
        void accept(Fruit fruit);

        void accept(Apple fruit);

        void accept(Banana fruit);
    }


}
