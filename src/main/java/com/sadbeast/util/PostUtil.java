package com.sadbeast.util;

import org.apache.commons.lang3.StringEscapeUtils;
import org.pegdown.PegDownProcessor;

public class PostUtil {
    private static final PegDownProcessor PEG_DOWN = new PegDownProcessor();
    private PostUtil() {}

    public static String process(final String content) {
        String escapedContent = StringEscapeUtils.escapeHtml4(content);
        return PEG_DOWN.markdownToHtml(escapedContent);
    }
}
