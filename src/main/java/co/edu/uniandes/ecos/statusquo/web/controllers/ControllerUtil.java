/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.ecos.statusquo.web.controllers;

import java.io.IOException;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.omnifaces.util.Faces;

/**
 *
 * @author Alvaro
 */
public final class ControllerUtil {

    private ControllerUtil() {
    }

    public static void printException(final Exception ex) {
        ex.printStackTrace(System.out);
        final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error Grave", "Ocurrió un error en el sistema");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public static void printMensaje(final String msj) {
        final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Mensaje", msj);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public static void redirect(final String url) {
        try {
            Faces.redirect(url);
        } catch (IOException ex) {
            printException(ex);
        }
    }
}
