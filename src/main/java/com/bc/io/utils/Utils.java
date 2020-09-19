package com.bc.io.utils;

import java.util.List;

public class Utils {
    public static void trimContents(List<String> list) {
        for (int i = 0; i < list.size(); i++) {
            list.set(i, list.get(i).trim());
        }
    }
}
