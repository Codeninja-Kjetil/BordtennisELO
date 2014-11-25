package no.uib.inf319.bordtennis.dao;

import java.io.IOException;
import java.util.Properties;

public interface PropertiesDao {
    String getProperty(String key);
    void setProperty(String key, String value);
    void persistProperties() throws IOException;
}
