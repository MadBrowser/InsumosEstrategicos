package cl.colabra.cvilches.insumosestrategicos.utils;

/**
 * Project: InsumosEstrategicos.
 * Created by Carlos Vilches on 12/10/15. By appointment
 * of Colabra for client 'Minera Collahuasi'
 */
public abstract class Config {

    // Server URLs
    private static final String SERVER_URL = "http://collahuasinew2.colabra.cl";

    // Connection parameters
    private static final int DEFAULT_TIMEOUT = 16000;
    private static final String CONTENT_XML = "text/xml";
    private static final String CHARSET_UTF8 = "UTF-8";

    public static String getServerUrl(){
        return SERVER_URL;
    }

    public static int getDefaultTimeout() {
        return DEFAULT_TIMEOUT;
    }

    public static String getContentXml() {
        return CONTENT_XML;
    }

    public static String getCharsetUtf8() {
        return CHARSET_UTF8;
    }
}