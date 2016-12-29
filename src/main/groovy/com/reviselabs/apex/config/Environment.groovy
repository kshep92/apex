package com.reviselabs.apex.config;

public class Environment {
    public static Boolean isProd() {
        return getEnv().equals("prod");
    }

    public static Boolean isDev() {
        return getEnv().equals("dev");
    }

    private static String getEnv() {
        return (System.getProperty("env") == null) ? "dev" : "prod";
    }
}
