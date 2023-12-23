package io.github.arungahlawat.automation.api.fieldOps.utils;

import io.github.arungahlawat.automation.api.core.utils.ApiCoreRandomUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

public class FieldOpsApiRandomUtils extends ApiCoreRandomUtils {
    public static Double getCost() {
        return getCost(100D, 5000D);
    }

    public static Double getCost(Double minCost, Double maxCost) {
        return BigDecimal.valueOf(getDouble(minCost, maxCost)).setScale(0, RoundingMode.HALF_UP).doubleValue();
    }

    public static Double getRevenue(Double cost) {
        return BigDecimal.valueOf(getDouble(cost, cost * 1.10)).setScale(0, RoundingMode.HALF_UP).doubleValue();
    }

    public static Map<String, Long> getAdvance(Double cost) {
        Double minBound = cost * 0.05;
        Double maxBound = cost * 0.15;
        Map<String, Long> advanceBreakup = new HashMap<>();
        advanceBreakup.put("cash", getDouble(minBound, maxBound).longValue());
        advanceBreakup.put("fuel", getDouble(minBound, maxBound).longValue());
        advanceBreakup.put("loading", getDouble(minBound, maxBound).longValue());
        advanceBreakup.put("halting", getDouble(minBound, maxBound).longValue());
        advanceBreakup.put("insurance", getDouble(minBound, maxBound).longValue());
        return advanceBreakup;
    }
}
