package it.cnr.iit.utility;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.logging.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class JsonUtility {
    protected static final Logger LOGGER = Logger.getLogger( JsonUtility.class.getName() );

    private JsonUtility() {}

    public static Optional<String> getJsonStringFromObject( Object obj, boolean pretty ) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter writer = pretty ? mapper.writerWithDefaultPrettyPrinter() : mapper.writer();

        try {
            return Optional.of( writer.writeValueAsString( obj ) );
        } catch( JsonProcessingException e ) {
            LOGGER.severe( "Error marshalling object of class : " + obj.getClass().getName() + ", " + e.getMessage() );
            return Optional.empty();
        }
    }

    public static <T> Optional<T> loadObjectFromJsonString( String jsonString, Class<T> clazz ) {
        ObjectMapper obj = new ObjectMapper();
        try {
            return Optional.of( obj.readValue( jsonString, clazz ) );
        } catch( IOException e ) {
            LOGGER.severe( "Error unmarshalling object of class : " + clazz.getName() + ", " + e.getMessage() );
            return Optional.empty();
        }
    }

    public static <T> Optional<T> loadObjectFromJsonFile( File file, Class<T> clazz ) {
        String data = null;
        try {
            data = new String( Files.readAllBytes( Paths.get( file.getAbsolutePath() ) ), Charset.forName( "UTF-8" ) );
        } catch( IOException e ) {
            LOGGER.severe( "Error reading file : " + file.getAbsolutePath() + ", " + e.getMessage() );
            return Optional.empty();
        }

        return loadObjectFromJsonString( data, clazz );
    }

    public static void dumpObjectToJsonFile( Object obj, String path, boolean pretty ) {
        try (FileOutputStream stream = new FileOutputStream( path )) {
            Optional<String> data = getJsonStringFromObject( obj, pretty );
            if( data.isPresent() ) {
                stream.write( data.get().getBytes() );
            }
        } catch( IOException e ) {
            LOGGER.severe( "Error writing file : " + path + ", " + e.getMessage() );
        }
    }

}
