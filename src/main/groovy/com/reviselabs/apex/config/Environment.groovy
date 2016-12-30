package com.reviselabs.apex.config;

public class Environment {
    public static Boolean isProd() {
        return getEnv().equals("production");
    }

    public static Boolean isDev() {
        return getEnv().equals("development");
    }

    private static String getEnv() {
        return (System.getProperty("apex.env") == null) ? "development" : "production";
    }
}
