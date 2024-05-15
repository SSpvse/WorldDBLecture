package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesProvider {

    public static final Properties PROPS;


    // this will execute when the class is loaded in the memory
    static{
        PROPS = new Properties();
        try {
            // use absolute path here:
            PROPS.load(new FileInputStream("/Users/stefanspasenic/Downloads/WorldLectureDB/src/main/resources/worldDB.properties"));
        } catch (IOException e) {
            System.out.println("Unable to load properties:"+e.getMessage());
        }
    }
}
