
package br.com.ufu.lsi.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.ObjectOutputStream;

public class FileUtil {

    public static BufferedWriter openOutputFile( String outputFile ) throws Exception {
        File file = new File( outputFile );
        FileWriter fw = new FileWriter( file.getAbsoluteFile() );
        BufferedWriter bw = new BufferedWriter( fw );
        return bw;
    }

    public static BufferedReader openInputFile( String inputFile ) throws Exception {
        BufferedReader br = new BufferedReader( new FileReader( inputFile ) );
        return br;
    }

    public static void serializeObject( Object object, String file ) {

        try {
            File f = new File( file );

            FileOutputStream out = new FileOutputStream( f );

            ObjectOutputStream stream = new ObjectOutputStream( out );

            stream.writeObject( object );

            stream.close();
            out.close();

        } catch ( Exception e ) {

            e.printStackTrace();
        }

    }

}
