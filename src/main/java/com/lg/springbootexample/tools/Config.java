package com.lg.springbootexample.tools;

import com.lg.springbootexample.SpringBootExampleApplication;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class Config {

    private static final Properties p = new Properties();

    private static final Map<Integer, String> cityMap = new HashMap<Integer, String>();

    static {
        try {
            initFromFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getProperty(String key) {
        if (!p.containsKey(key)) {
            return "";
        }
        return p.getProperty(key);
    }

    public static String getString(String name) {
        if (p.containsKey(name)) {
            return p.getProperty(name);
        }
        throw new IllegalArgumentException("Missing required property '" + name + "'");
    }

    public static int getInt(String name) {
        if (p.containsKey(name)) {
            return Integer.valueOf(p.getProperty(name));
        }
        throw new IllegalArgumentException("Missing required property '" + name + "'");
    }

    public static List<String> getList(String name) {
        if (p.containsKey(name)) {
            List<String> keys = new ArrayList<String>();
            String names = p.getProperty(name);
            for (String str : names.split(",")) {
                keys.add(str.trim());
            }

            if (keys.size() > 0) {
                return keys;
            }
        }
        throw new IllegalArgumentException("Missing required property '" + name + "'");
    }

    public static String[] getStringList(String name) {
        if (p.containsKey(name)) {
            String[] keys = p.getProperty(name).split(",");

            if (keys.length > 0) {
                return keys;
            }
        }
        throw new IllegalArgumentException("Missing required property '" + name + "'");
    }


    private static void initFromFile() throws IOException {
        InputStream inputStream =
            SpringBootExampleApplication.class.getClassLoader()
                .getResourceAsStream("client.properties");
        p.load(inputStream);
        inputStream.close();
    }
}
