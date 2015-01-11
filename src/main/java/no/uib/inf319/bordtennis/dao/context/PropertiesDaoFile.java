package no.uib.inf319.bordtennis.dao.context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import no.uib.inf319.bordtennis.dao.PropertiesDao;

/**
 * An implementation of the {@link PropertiesDao}-interface using Java's
 * {@link Properties}-system.
 *
 * @author Kjetil
 */
public final class PropertiesDaoFile implements PropertiesDao {

    /**
     * Location of properties-file.
     */
    private static final String FILENAME =
            "/usr/share/tomcat/tabletennis/tabletennis.properties";

    /**
     * Properties-object.
     */
    private Properties properties;

    @Override
    public void retriveProperties() throws IOException {
        properties = new Properties();
        InputStream in = null;
        try {
            in = new FileInputStream(FILENAME);
            properties.load(in);
        } catch (IOException e) {
            throw e;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    throw e;
                }
            }
        }
    }

    @Override
    public void persistProperties() throws IOException {
        OutputStream out = null;
        try {
            out = new FileOutputStream(FILENAME);
            properties.store(out, null);
        } catch (IOException e) {
            throw e;
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    throw e;
                }
            }
        }
    }

    @Override
    public String getProperty(final String key) {
        return properties.getProperty(key);
    }

    @Override
    public void setProperty(final String key, final String value) {
        properties.setProperty(key, value);
    }

}
