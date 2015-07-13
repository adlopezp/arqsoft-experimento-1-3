/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.ecos.statusquo.web.services;

import co.edu.uniandes.ecos.statusquo.web.security.utils.WebUtil;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.Subject;

/**
 *
 * @author Dev
 */
@Path("/login")
@Stateless
@Produces(MediaType.APPLICATION_JSON)
public class LoginService {

    private final Realm realm = new JdbcRealm();
    private final DefaultSecurityManager sm = new DefaultSecurityManager(realm);

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response userAuth(@QueryParam("username") final String username,
            @QueryParam("password") final String password,
            @QueryParam("remember") final boolean remember) {
        Response r;
        final UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        token.setRememberMe(remember);
        SecurityUtils.setSecurityManager(sm);
        final Subject currentUser = SecurityUtils.getSubject();
        try {
            currentUser.login(token);
            r = Response.ok().entity(token).build();
        } catch (IncorrectCredentialsException ice) {
            r = Response.status(401).entity("Username/password Incorrect").build();
        }

        return r;
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response getLogin() {
        return Response.status(403).entity(WebUtil.FORBIDDEN_PAGE_CONTENT).build();
    }
}
