/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.ecos.statusquo.security.Utils;

import org.apache.shiro.authc.AuthenticationToken;

/**
 *
 * @author Dev
 */
public class ShiroToken implements AuthenticationToken {
    
    private String token;

    public ShiroToken(String token) {
    
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object getCredentials() {
        throw new UnsupportedOperationException("Not supported yet.");
    }


}
