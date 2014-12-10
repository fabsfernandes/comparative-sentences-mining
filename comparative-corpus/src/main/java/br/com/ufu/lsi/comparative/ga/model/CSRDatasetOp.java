
package br.com.ufu.lsi.comparative.ga.model;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import br.com.ufu.lsi.comparative.util.PropertiesUtil;
import br.com.ufu.lsi.comparative.util.RandomGenerator;

public class CSRDatasetOp {

    private static final String COMPARATIVE_CLASS = PropertiesUtil.getProperty( "COMPARATIVE_CLASS" );

    private static final Properties properties;
    
    public static final int ATTRIBUTES_NUMBER = 8;

    static {
        properties = new Properties();
        InputStream inputStream = PropertiesUtil.class.getClassLoader().getResourceAsStream( "postags" );
        try {
            properties.load( inputStream );
        } catch ( IOException e ) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings( {"rawtypes", "unchecked"} )
    public static String getPostagValue() {

        Map props = ( Map ) properties;
        HashMap< String, String > map = new HashMap< String, String >( props );

        return RandomGenerator.randKey( map );
    }

    public static String getClassValue() {

        return COMPARATIVE_CLASS;
    }

}
