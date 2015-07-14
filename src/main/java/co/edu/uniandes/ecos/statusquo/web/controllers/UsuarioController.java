package co.edu.uniandes.ecos.statusquo.web.controllers;

import co.edu.uniandes.ecos.statusquo.business.PacienteEJB;
import co.edu.uniandes.ecos.statusquo.persistence.entities.Paciente;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.apache.shiro.crypto.hash.Sha512Hash;

@ManagedBean(name = "usuarioBean")
@ViewScoped
public class UsuarioController {

    public static final String KEY = "statusquo";
    @EJB
    private PacienteEJB pacienteEJB;
    private List<Paciente> usuarios;
    private Paciente pacienteSeleccionado = new Paciente();
    private String contrasenaNueva;

    @PostConstruct
    public void init() {
        try {
            usuarios = pacienteEJB.consultar();
        } catch (Exception ex) {
            ControllerUtil.printException(ex);
        }
    }

    public List<Paciente> getUsuarios() {
        return usuarios;
    }

    public void seleccionarUsuario(final Paciente paciente) {
        pacienteSeleccionado = paciente;
        pacienteSeleccionado.setPassword("");
        ControllerUtil.redirect("site/usuario.xhtml");
    }

    public void nuevoUsuario() {
        pacienteSeleccionado = new Paciente();
        ControllerUtil.redirect("site/usuario.xhtml");
    }

    public void guardarUsuario() {
        if (!pacienteSeleccionado.getPassword().equals(contrasenaNueva)) {
            ControllerUtil.printMensaje("Contraseñas no coinciden");
        } else {
            pacienteSeleccionado.setPassword(new Sha512Hash(pacienteSeleccionado.getPassword(), KEY, 1024).toString());
        }
        try {
            pacienteEJB.save(pacienteSeleccionado);
            pacienteSeleccionado = new Paciente();
            usuarios = pacienteEJB.consultar();
            ControllerUtil.redirect("site/usuarios.xhtml");
        } catch (Exception ex) {
            ControllerUtil.printException(ex);
        }
    }

    public Paciente getPacienteSeleccionado() {
        return pacienteSeleccionado;
    }

    public String getContrasenaNueva() {
        return contrasenaNueva;
    }

    public void setContrasenaNueva(String contrasenaNueva) {
        this.contrasenaNueva = contrasenaNueva;
    }
}
