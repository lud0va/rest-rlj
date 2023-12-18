package server.common;


import jakarta.inject.Singleton;
import lombok.Getter;

import java.io.IOException;
import java.util.Properties;

@Singleton
@Getter
public class Configuration {

    private static final Configuration instance = null;
    private final Properties p;

    private Configuration() {
        try {
            p = new Properties();
            p.loadFromXML(Configuration.class.getClassLoader().getResourceAsStream(""));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public String getProperty(String key) {
        return p.getProperty(key);
    }

}
