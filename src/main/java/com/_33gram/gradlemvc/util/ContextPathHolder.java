package com._33gram.gradlemvc.util;

public class ContextPathHolder {
	
    private static String contextPath = "";

    public static String getContextPath() {
        return contextPath;
    }

    public static String buildContextPath(String value) {
        return value != null ? contextPath + "/" + value : null;
    }

    public static String buildContextPath(String value, String defaultValue) {
        return value != null ? contextPath + "/" + value : contextPath + "/" + defaultValue;
    }

    public static void setContextPath(String path) {
        // contextPath = (!path.equals("/") && !path.endsWith("/")) ? path + "/" : path;
        contextPath = path;
    }

}
