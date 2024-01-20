package common;


import jakarta.inject.Singleton;
import lombok.Getter;

import java.io.IOException;
import java.util.Properties;

@Singleton
@Getter
public class Configuration {


    private String host;
    private String user;
    private String password;


    public Configuration() {
        try {
            Properties p = new Properties();
            p.loadFromXML(Configuration.class.getClassLoader().getResourceAsStream("config/correoinfo.xml"));
            this.host = p.getProperty(ConstantsServer.HOSTPROP);
            this.user = p.getProperty(ConstantsServer.USERPROP);
            this.password = p.getProperty(ConstantsServer.PASSWORDPROP);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }


}


