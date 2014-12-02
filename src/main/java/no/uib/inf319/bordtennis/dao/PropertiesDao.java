package no.uib.inf319.bordtennis.dao;

import java.io.IOException;

public interface PropertiesDao {
    String getProperty(String key);
    void setProperty(String key, String value);
    void retriveProperties() throws IOException;
    void persistProperties() throws IOException;
}
