package org.qa.autotests.utils;

import com.google.common.collect.ImmutableMap;
import org.openqa.selenium.By;
import org.qa.autotests.BaseTest;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class PropParser {
    public static void setProperties(String name) throws IOException {
        Properties props = new Properties();
        InputStream inputStream = BaseTest.class
                .getClassLoader()
                .getResourceAsStream(name);
        try (Reader in = new InputStreamReader(inputStream, StandardCharsets.UTF_8)) {
            props.load(in);
        }

        if (!props.isEmpty()) {
            for (Object propObj : props.keySet()) {
                String prop = String.valueOf(propObj);

                if (!System.getProperties().containsKey(prop)) {
                    System.setProperty(prop, props.getProperty(prop));
                }
            }
        }
    }
}
