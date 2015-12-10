package cl.colabra.cvilches.insumosestrategicos.utils;

/**
 * Project: InsumosEstrategicos.
 * Created by Carlos Vilches on 12/10/15. By appointment
 * of Colabra for client 'Minera Collahuasi'
 */
public abstract class Config {

    private static final String serverUrl = "http://collahuasinew2.colabra.cl";

    public static String getServerUrl(){
        return serverUrl;
    }

}