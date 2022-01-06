package edu.usfca.vinh;

import java.net.URLEncoder;
import java.util.Map;

public class ParameterStringBuilder {
    public static String getParamsString(Map<String, String> params) {
        StringBuilder result = new StringBuilder();

        for (Map.Entry<String, String> entry : params.entrySet()) {
//            result.append(URLEncoder.encode(entry.getKey(),"UTF-8"));
//            result.append()
        }
        return null;
    }

}
