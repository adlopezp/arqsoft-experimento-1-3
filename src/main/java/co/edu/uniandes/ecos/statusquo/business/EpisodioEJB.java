/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.ecos.statusquo.business;

import co.edu.uniandes.ecos.statusquo.persistence.dao.EpisodioDAO;
import co.edu.uniandes.ecos.statusquo.persistence.entities.Episodio;
import co.edu.uniandes.ecos.statusquo.persistence.entities.Paciente;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author Dev
 */
@Stateless
@LocalBean
public class EpisodioEJB {

    @EJB
    private EpisodioDAO facade;

    public Episodio consultarId(Long id) throws Exception {
        return facade.find(id);
    }

    public List<Episodio> consultarLista(final Long pacienteId) throws Exception {
        final HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("paciente", pacienteId);
        return facade.findByNamedQuery("Episodio.findByPaciente", params);
    }
    
    public List<Episodio> consultarFechas(final Date fechaInicio,final Date fechaFin) throws Exception {
        final HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("fechaInicio", fechaInicio);
        params.put("fechaFin", fechaFin);
        return facade.findByNamedQuery("Episodio.findByRangoFecha", params);
    }
    
    public List<Episodio> consultarFechasPaciente(final Long pacienteId, 
    											  final Date fechaInicio,
    											  final Date fechaFin){
    	final Map<String, Object> params = new HashMap<String, Object>();
    	params.put("paciente", new Paciente(pacienteId));
    	params.put("fechaInicio", fechaInicio);
        params.put("fechaFin", fechaFin);
    	return facade.findByNamedQuery("Episodio.findByIdRangoFecha", params);
    }

    public void save(final Episodio entidad) throws Exception {
        if (entidad.getId() == null) {
            facade.create(entidad);
        } else {
            facade.edit(entidad);
        }
    }

    public void remove(final Episodio entidad) throws Exception {
        facade.remove(entidad);
    }
}
