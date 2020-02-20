package com.boleo.internal.tests;

import org.junit.jupiter.api.Test;

import java.util.*;

public class MergeIntervalsWithValues {

    public static List<Interval> mergeIntervals(Collection<Interval> input) {
        if (input == null || input.isEmpty()) {
            return new ArrayList<>();
        }
        TreeSet<Interval> intervals = new TreeSet<>(input);

        List<Interval> result = new ArrayList<>();
        Interval top = intervals.pollFirst();
        if (top == null) {
            return result;
        }
        while (!intervals.isEmpty()) {
            Interval interval = intervals.pollFirst();
            if (top.getFinish() <= interval.getStart()) {
                result.add(top);
                top = interval;
                continue;
            }

            if (top.getValue() <= interval.getValue()) {
                if (interval.finish > top.finish) {
                    Interval i = new Interval(interval.value, top.finish, interval.finish);
                    intervals.add(i);
                }
                continue;
            }

            // current interval value is smaller than top's
            if (top.getStart() < interval.start) {
                result.add(new Interval(top.value, top.start, interval.start));
            }
            if (top.finish > interval.finish) {
                intervals.add(new Interval(top.value, interval.finish, top.finish));
            }
            top = interval;
        }
        result.add(top);
        return result;
    }

    @Test
    void figureOutTerms() {
        Interval i1 = new Interval(100, 8, 17);
        Interval i2 = new Interval(110, 9, 17);
        Interval i3 = new Interval(90, 14, 17);
        Interval i4 = new Interval(77, 13, 15);
        List<Interval> result = mergeIntervals(Arrays.asList(i4, i2, i1, i3));
        System.out.println(result);
    }

    private static class Interval implements Comparable<Interval> {
        private final int value;
        private final int start;
        private final int finish;

        private Interval(int value, int start, int finish) {
            if (finish <= start) {
                throw new IllegalArgumentException("finish greater than start");
            }

            this.value = value;
            this.start = start;
            this.finish = finish;
        }

        public int getValue() {
            return value;
        }

        public int getStart() {
            return start;
        }

        public int getFinish() {
            return finish;
        }

        @Override
        public String toString() {
            return "Interval{" +
                    "value=" + value +
                    ", start=" + start +
                    ", finish=" + finish +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Interval)) return false;
            Interval interval = (Interval) o;
            return value == interval.value &&
                    start == interval.start &&
                    finish == interval.finish;
        }

        @Override
        public int hashCode() {
            return Objects.hash(value, start, finish);
        }

        @Override
        public int compareTo(Interval o) {
            if (this == o) return 0;
            if (o == null) return 1;
            if (this.equals(o)) {
                return 0;
            }
            return this.start - o.getStart() == 0 ? 1 : this.start - o.getStart();
        }
    }
}
