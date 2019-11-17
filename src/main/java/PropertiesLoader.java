import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesLoader {
    private Properties props;
    private static PropertiesLoader propertiesLoader;

    private PropertiesLoader() {
        props = new Properties();
        try {
            props.load(PropertiesLoader.class.getResourceAsStream("kafka.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static PropertiesLoader getPropertiesLoaderInstance() {
        if(propertiesLoader == null) {
            propertiesLoader = new PropertiesLoader();
        }
        return propertiesLoader;
    }

    public String getProperty(String key) {
        return this.props.getProperty(key);
    }
}
