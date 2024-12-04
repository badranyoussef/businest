package org.util;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.Properties;

import org.exceptions.ApiException;

/**
 * Purpose: Utility class to read properties from a file
 * Author: Thomas Hartmann
 */
public class Utils {
    public static void main(String[] args) {
        System.out.println(getPropertyValue("db.name", "properties-from-pom.properties"));
    }
    public static String getPropertyValue(String propName, String ressourceName)  {
        // REMEMBER TO BUILD WITH MAVEN FIRST. Read the property file if not deployed (else read system vars instead)
        // Read from ressources/config.properties or from pom.xml depending on the ressourceName
        try (InputStream is = Utils.class.getClassLoader().getResourceAsStream(ressourceName)) { //"config.properties" or "properties-from-pom.properties"
            Properties prop = new Properties();
            prop.load(is);
            return prop.getProperty(propName);
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new ApiException(500, "Could not read property %s. Did you remember to build the project with MAVEN?", LocalDate.now().toString());
        }
    }
}
