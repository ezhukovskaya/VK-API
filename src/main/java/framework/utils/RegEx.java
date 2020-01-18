package framework.utils;

import org.apache.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegEx {
    private static Pattern pattern;
    private static final String EMPTY = "";
    static final Logger log = Logger.getLogger(RegEx.class);

    public static String getModifiedValue(String extraValue, String value) {
        log.info(extraValue + " spaces been taken away from the values");
        pattern = Pattern.compile(extraValue);
        Matcher matcher = pattern.matcher(value);
        return matcher.replaceAll(EMPTY);
    }
}
