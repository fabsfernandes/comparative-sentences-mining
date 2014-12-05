
package br.com.ufu.lsi.comparative.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {

    private static final Properties properties;

    static {
        properties = new Properties();
        InputStream inputStream = PropertiesUtil.class.getClassLoader().getResourceAsStream( "config.properties" );
        try {
            properties.load( inputStream );
        } catch ( IOException e ) {
            e.printStackTrace();
        }
    }

    public static String getProperty( String key ) {
        return ( String ) properties.get( key );
    }
    
    public static Double getDoubleProperty( String key ) {
        String str = (String) properties.get( key );
        return Double.parseDouble( str );
    }
    
    public static Integer getIntProperty( String key ) {
        String str = (String) properties.get( key );
        return Integer.parseInt( str );
    }

}
