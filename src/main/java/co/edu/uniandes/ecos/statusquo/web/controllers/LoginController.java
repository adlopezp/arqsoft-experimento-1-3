/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.ecos.statusquo.web.controllers;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Sha512Hash;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.omnifaces.util.Faces;

/**
 *
 * @author Dev
 */
@ManagedBean(name = "login")
@SessionScoped
public class LoginController {

    public static final String HOME_URL = "site/index.xhtml";
    public static final String key = "statusquo";
    private String username;
    private String password;
    private boolean remember;

    public void login() {
        try {
            SecurityUtils.getSubject().login(new UsernamePasswordToken(username, password, remember));
            SavedRequest savedRequest = WebUtils.getAndClearSavedRequest(Faces.getRequest());
            Faces.redirect(savedRequest != null ? savedRequest.getRequestUrl() : HOME_URL);
        } catch (AuthenticationException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Alerta", "Autenticacion incorrecta"));
            e.printStackTrace();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Alerta", "Ha ocurrido un error " + e.getMessage()));
            e.printStackTrace();
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = new Sha512Hash(password, key, 1024).toString();
    }

    public boolean isRemember() {
        return remember;
    }

    public void setRemember(boolean remember) {
        this.remember = remember;
    }
}
