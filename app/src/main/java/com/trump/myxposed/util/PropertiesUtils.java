package com.trump.myxposed.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import de.robv.android.xposed.XposedBridge;

/**
 * Author: TRUMP
 * Date:   2022/2/11 0011 14:41
 * Desc:
 */
public class PropertiesUtils {

    public static final String PRO_FILE = "/storage/emulated/0/MyXposed/" + "stetting_config.txt";

    public PropertiesUtils() {
    }

    public static boolean getBoolean(String key) {
        return Boolean.valueOf(getValue(PRO_FILE, key, "false"));
    }

    public static String getValue(String key, String defaultValue) {
        return getValue(PRO_FILE, key, defaultValue);
    }

    public static String getValue(String fileName, String key, String defaultValue) {
        return getProperties(fileName).getProperty(key, defaultValue);
    }

    public static void putValue(String fileName, String key, String value) {
        try {
            Properties properties = getProperties(fileName);
            OutputStream fos = new FileOutputStream(fileName);
            properties.setProperty(key, value);
            properties.store(fos, "Update value");
        } catch (IOException var5) {
            var5.printStackTrace();
        }
    }

    private static Properties getProperties(String fileName) {
        Properties properties = new Properties();

        try {
            File file = new File(fileName);
            if (!file.exists()) {
                File path = new File(file.getParent());
                if (!path.exists()) {
                    path.mkdirs();
                }

                file.createNewFile();
            }

            InputStream inputStream = new FileInputStream(file);
            properties.load(inputStream);
        } catch (IOException var4) {
            var4.printStackTrace();
            XposedBridge.log(var4);
        }

        return properties;
    }
}