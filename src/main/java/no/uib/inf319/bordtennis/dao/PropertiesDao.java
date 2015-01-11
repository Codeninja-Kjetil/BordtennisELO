package no.uib.inf319.bordtennis.dao;

import java.io.IOException;

/**
 * Interface containing methods for retrieving and editing properties.
 *
 * @author Kjetil
 */
public interface PropertiesDao {
    /**
     * Get a property. Remember to load the properties with
     * {@link #retriveProperties()} first.
     *
     * @param key property key
     * @return property value
     */
    String getProperty(String key);

    /**
     * Set a property. Remember to load the properties with
     * {@link #retriveProperties()} first, and persist them with
     * {@link #persistProperties()} after.
     *
     * @param key property key
     * @param value property value
     */
    void setProperty(String key, String value);

    /**
     * Loads the properties from source.
     *
     * @throws IOException IOException
     */
    void retriveProperties() throws IOException;

    /**
     * Persists the properties to source.
     *
     * @throws IOException IOException
     */
    void persistProperties() throws IOException;
}
