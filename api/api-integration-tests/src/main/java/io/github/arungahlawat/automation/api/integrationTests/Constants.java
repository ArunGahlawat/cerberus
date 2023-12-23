package io.github.arungahlawat.automation.api.integrationTests;

import io.github.arungahlawat.automation.core.enums.Env;

import java.util.HashMap;
import java.util.Map;

public class Constants {
    public static final Map<Env,String > URI = new HashMap<>(){{
        put(Env.TEST, "https://test-fo.letstransport.in");
        put(Env.STAGE, "https://stg-fieldops.letstransport.in");
    }};
}
