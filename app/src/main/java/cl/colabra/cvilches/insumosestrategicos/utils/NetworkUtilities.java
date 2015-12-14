package cl.colabra.cvilches.insumosestrategicos.utils;

import org.apache.http.cookie.Cookie;

import java.net.HttpCookie;

/**
 * Project: InsumosEstrategicos.
 * Created by Carlos Vilches on 12/10/15. By appointment
 * of Colabra for client 'Minera Collahuasi'
 */
public abstract class NetworkUtilities {

    /**
     * Returns HttpCookie from org.apache.http.cookie.Cookie
     */
    public static HttpCookie getHttpCookie(Cookie cookie) {
        return new HttpCookie(cookie.getName(), cookie.getValue());
    }

}
