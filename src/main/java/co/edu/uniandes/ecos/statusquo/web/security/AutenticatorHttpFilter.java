/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.ecos.statusquo.web.security;

import com.codesnippets4all.json.parsers.JSONParser;
import com.codesnippets4all.json.parsers.JsonParserFactory;
import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Sha512Hash;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.util.WebUtils;

/**
 *
 * @author Dev
 */
public class AutenticatorHttpFilter extends AuthenticatingFilter {

    final protected String key = "statusquo";

    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
        final HttpServletRequest httpRequest = WebUtils.toHttp(request);
        final String credencialesHeader = httpRequest.getHeader("Credenciales");
        final String authorizationHeader = httpRequest.getHeader("Autorizacion");
        UsernamePasswordToken token = new UsernamePasswordToken();

        System.out.println("CREDENCIALES " + credencialesHeader);
        System.out.println("AUTORIZACION " + authorizationHeader);

        if (credencialesHeader != null && !credencialesHeader.isEmpty()) {
            final StringBuffer peticionBuffer = new StringBuffer();
            final Map<String, String[]> map = request.getParameterMap();
            for (Entry<String, String[]> entry : map.entrySet()) {
                peticionBuffer.append(entry.getKey());
                peticionBuffer.append(':');
                peticionBuffer.append(Arrays.toString(entry.getValue()));
                peticionBuffer.append(';');
            }
            System.out.println("PETICION " + peticionBuffer.toString());
            if (authorizationHeader.equals(new Sha512Hash(peticionBuffer.toString(), key, 1024).toString())) {

                try {
                    JsonParserFactory factory = JsonParserFactory.getInstance();
                    JSONParser parser = factory.newJsonParser();

                    Map jsonData = parser.parseJson(credencialesHeader);
                    token = new UsernamePasswordToken((String) jsonData.get("username"), (String) jsonData.get("password"));
                    token.setRememberMe(Boolean.valueOf((String) jsonData.get("rememberMe")));
                    System.out.println("AUTENTICÓ");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        }
        return token;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        boolean loggedIn = executeLogin(request, response);

        if (!loggedIn) {
            HttpServletResponse httpResponse = WebUtils.toHttp(response);
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }
        return loggedIn;
    }
}
