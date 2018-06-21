package com.atherys.rpg.utils;

import java.util.Map;

public final class StringUtils {

    /**
     * Format the given string template with the arguments present in the argument map.
     * Argument keys, as written into the template, should start with "${" and end with "}".
     *
     * @param template The template to use
     * @param arguments The arguments to apply
     * @return The formatted string
     */
    public static String format(String template, Map<String, ?> arguments) {
        String result = template;
        for (Map.Entry<String, ?> entry : arguments.entrySet() ) {
            result = result.replace("${" + entry.getKey() + "}", entry.getValue().toString());
        }
        return result;
    }

}
