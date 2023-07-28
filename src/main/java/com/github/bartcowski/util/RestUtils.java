package com.github.bartcowski.util;

import java.util.Map;

public class RestUtils {

    public static String addParamsToUrl(String url, Map<String, String> params) {
        StringBuilder paramsStringBuilder;
        if (urlAlreadyContainsParams(url)) {
            paramsStringBuilder = new StringBuilder("&");
        } else {
            paramsStringBuilder = new StringBuilder("?");
        }

        for (Map.Entry<String, String> param : params.entrySet()) {
            paramsStringBuilder.append(param.getKey()).append("=").append(param.getValue()).append("&");
        }
        return url.concat(paramsStringBuilder.substring(0, paramsStringBuilder.length() - 1));
    }

    private static boolean urlAlreadyContainsParams(String url) {
        return url.contains("?");
    }

}
