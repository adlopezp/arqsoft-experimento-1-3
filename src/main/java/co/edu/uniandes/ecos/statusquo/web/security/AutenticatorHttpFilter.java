/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.ecos.statusquo.web.security;

import co.edu.uniandes.ecos.statusquo.web.security.utils.ShiroToken;
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
        HttpServletRequest httpRequest = WebUtils.toHttp(request);
        String credencialesHeader = httpRequest.getHeader("Credenciales");
        String authorizationHeader = httpRequest.getHeader("Autorizacion");

        System.out.println("1 " + credencialesHeader);
        System.out.println("2 " + authorizationHeader);
        if (credencialesHeader == null || credencialesHeader.length() == 0) {
            // Create an empty authentication token since there is no
            // Authorization header.
            return new ShiroToken("");
        }

        final StringBuffer peticionBuffer = new StringBuffer();
        final Map<String, String[]> map = request.getParameterMap();
        for (Entry<String, String[]> entry : map.entrySet()) {
            peticionBuffer.append(entry.getKey());
            peticionBuffer.append(':');
            peticionBuffer.append(Arrays.toString(entry.getValue()));
            peticionBuffer.append(';');
        }

        System.out.println("PETICION " + peticionBuffer.toString());

        if (!peticionBuffer.toString().isEmpty() &&!authorizationHeader.equals(new Sha512Hash(peticionBuffer.toString(), key, 1024).toString())) {
            return new ShiroToken("");
        }
        try {
            JsonParserFactory factory = JsonParserFactory.getInstance();
            JSONParser parser = factory.newJsonParser();

            Map jsonData = parser.parseJson(credencialesHeader);
            return new UsernamePasswordToken((String)jsonData.get("username"), (String)jsonData.get("password"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println("PASO");

        return new ShiroToken(credencialesHeader);
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
