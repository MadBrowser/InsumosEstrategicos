package cl.colabra.cvilches.insumosestrategicos.utils;

import org.apache.http.cookie.Cookie;

import java.net.HttpCookie;

/**
 * Project: InsumosEstrategicos.
 * Created by Carlos Vilches on 12/10/15. By appointment
 * of Colabra for client 'Minera Collahuasi'
 */
public abstract class SOAPUtils {
    private static final String LOGIN_URL = "/_vti_bin/authentication.asmx";
    private static final String ACTION_LOGIN = "http://schemas.microsoft.com/sharepoint/soap/Login";
    private static final String LOGIN_ENVELOPE = "" +
            "<?xml version='1.0' encoding='utf-8'?>" +
            "<soap:Envelope xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance' " +
            "xmlns:xsd='http://www.org/2001/XMLSchema' " +
            "xmlns:soap='http://schemas.xmlsoap.org/soap/envelope/'>" +
                "<soap:Body>" +
                    "<Login xmlns='http://schemas.microsoft.com/sharepoint/soap/'>" +
                        "<username>%s</username>" +
                        "<password>%s</password>" +
                    "</Login>" +
                "</soap:Body>" +
            "</soap:Envelope>";

    public static String getLoginUrl() {
        return LOGIN_URL;
    }

    public static String getActionLogin() {
        return ACTION_LOGIN;
    }

    public static String getLoginEnvelope(String username, String password) {
        return String.format(LOGIN_ENVELOPE, username, password);
    }

}
