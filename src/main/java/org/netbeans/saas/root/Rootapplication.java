/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.netbeans.saas.root;

import java.io.IOException;
import java.io.InputStream;
import org.netbeans.saas.RestConnection;
import org.netbeans.saas.RestResponse;

/**
 * Rootapplication Service
 *
 * @author Alvaro
 */
public class Rootapplication {

    /**
     * Creates a new instance of Rootapplication
     */
    public Rootapplication() {
    }

    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (Throwable th) {
        }
    }

    /**
     *
     * @param content
     * @return an instance of RestResponse
     */
    public static RestResponse userAuth(InputStream content) throws IOException {
        String[][] pathParams = new String[][]{};
        String[][] queryParams = new String[][]{};
        RestConnection conn = new RestConnection("http://localhost:8080/proyecto-arq-soft/service//login", pathParams, queryParams);
        sleep(1000);
        return conn.post(null, content);
    }
}
