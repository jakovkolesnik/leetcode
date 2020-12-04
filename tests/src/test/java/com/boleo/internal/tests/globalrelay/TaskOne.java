package com.boleo.internal.tests.globalrelay;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class TaskOne {

    @Test
    public void test() throws Exception {
        System.out.println(Long.parseUnsignedLong("002"));
    }

    Map<Long, Long> count(Map<String, UserStats>... visits) {
        Map<Long, Long> result = new HashMap<>();
        if (visits == null || visits.length == 0) {
            return result;
        }
        for (Map<String, UserStats> stat : visits) {
            Map<Long, Long> processedData = processStats(stat);
            for (Map.Entry<Long, Long> entry : processedData.entrySet()) {
                Long currentValue = result.get(entry.getKey());
                if (currentValue == null) {
                    result.put(entry.getKey(), entry.getValue());
                } else {
                    result.put(entry.getKey(), currentValue + entry.getValue());
                }
            }
        }
        return result;
    }

    private Map<Long, Long> processStats(final Map<String, UserStats> data) {
        Map<Long, Long> result = new HashMap<>();
        if (data == null || data.isEmpty()) {
            return result;
        }
        for (Map.Entry<String, UserStats> entry : data.entrySet()) {
            Long userId = parseId(entry.getKey());
            if (userId == null) {
                continue;
            }
            Long visitCount = parseUserStats(entry.getValue());
            if (visitCount == null) {
                continue;
            }
            // no protection for duplicate userIds e.g. "1" vs "001" vs "01";
            result.put(userId, visitCount);
        }
        return result;
    }

    private Long parseUserStats(final UserStats value) {
        if (value == null) {
            return null;
        }
        Long result = value.getVisitCount().orElse(null);
        // corrupt counter possible?
        if (result != null && result >= 0) {
            return result;
        }
        return null;
    }

    private Long parseId(final String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        try {
            return Long.parseUnsignedLong(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private class UserStats {
        private Optional<Long> visitCount;

        public Optional<Long> getVisitCount() {
            return visitCount;
        }

        public void setVisitCount(Optional<Long> visitCount) {
            this.visitCount = visitCount;
        }
    }
}
