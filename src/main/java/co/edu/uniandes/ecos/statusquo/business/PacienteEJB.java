/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.ecos.statusquo.business;

import co.edu.uniandes.ecos.statusquo.persistence.dao.PacienteDAO;
import co.edu.uniandes.ecos.statusquo.persistence.entities.Paciente;
import java.util.HashMap;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author Dev
 */
@Stateless
@LocalBean
public class PacienteEJB {

    @EJB
    private PacienteDAO facade;

    public List<Paciente> consultar() throws Exception {
        return facade.findAll();
    }

    public Paciente consultarId(final Long id) throws Exception {
        return facade.find(id);
    }

    public Paciente consultarIdentificacion(Long identificacion) throws Exception {
        final HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("identificacion", identificacion);
        List<Paciente> pacientes = facade.findByNamedQuery("Paciente.findByIdentificacion", params);
        if (pacientes != null && pacientes.size() >0) {
        	return pacientes.get(0);
        }
        return null;
    }

    public void save(final Paciente entidad) throws Exception {
        if (entidad.getId() == null) {
            facade.create(entidad);
        } else {
            facade.edit(entidad);
        }
    }

    public void remove(final Paciente entidad) throws Exception {
        facade.remove(entidad);
    }
}
