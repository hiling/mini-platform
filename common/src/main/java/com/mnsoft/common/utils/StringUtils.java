package com.mnsoft.common.utils;

import java.util.Arrays;
import java.util.List;

/**
 * Author by hiling, Email admin@mn-soft.com, Date on 12/13/2018.
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {
    public static List<String> stringToList(String string) {

        String[] scopes = org.apache.commons.lang3.StringUtils.split(string, ",");
        if (scopes == null) {
            return null;
        }
        List<String> scopeList = Arrays.asList(scopes);
        return scopeList;
    }
}
