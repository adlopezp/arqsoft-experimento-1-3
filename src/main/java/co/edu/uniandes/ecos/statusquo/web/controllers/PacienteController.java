package co.edu.uniandes.ecos.statusquo.web.controllers;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import co.edu.uniandes.ecos.statusquo.business.EpisodioEJB;
import co.edu.uniandes.ecos.statusquo.business.PacienteEJB;
import co.edu.uniandes.ecos.statusquo.persistence.entities.Episodio;
import co.edu.uniandes.ecos.statusquo.persistence.entities.Paciente;
import co.edu.uniandes.ecos.statusquo.web.criteria.PacienteCriteria;

@ManagedBean
@RequestScoped
public class PacienteController {
	
	@EJB
	private PacienteEJB pacienteEJB;
	
	@EJB
	private EpisodioEJB episodioEJB;
	
	private PacienteCriteria pacienteCriteria = new PacienteCriteria();
	
	private Paciente pacienteResult;
	
	public PacienteCriteria getPacienteCriteria() {
		return pacienteCriteria;
	}

	public void setPacienteCriteria(PacienteCriteria pacienteCriteria) {
		this.pacienteCriteria = pacienteCriteria;
	}
	
	
	/*
	 * Métodos del controlador 
	 */
	
	public Paciente getPacienteResult() {
		return pacienteResult;
	}

	public void setPacienteResult(Paciente pacienteResult) {
		this.pacienteResult = pacienteResult;
	}

	public void buscarPaciente(){
		
		try {
			
			if (pacienteCriteria.getFechaInicio().compareTo(pacienteCriteria.getFechaFin()) >= 0){
				FacesMessage message = new FacesMessage(
						FacesMessage.SEVERITY_ERROR,"Rango de fechas","La fecha final debe ser posterior a la inicial");
				FacesContext.getCurrentInstance().addMessage(null, message);
				return;
			}
			
			pacienteResult = pacienteEJB.consultarIdentificacion(pacienteCriteria.getIdentificacion());
			if (pacienteResult == null) {
				FacesMessage message = new FacesMessage(
						FacesMessage.SEVERITY_WARN,"No hay coincidencia","No existe un paciente con dichos criterios");
				FacesContext.getCurrentInstance().addMessage(null, message);
				return;
			}
			List<Episodio> episodios = episodioEJB.consultarFechasPaciente(
					pacienteResult.getId(),
					pacienteCriteria.getFechaInicio(),
					pacienteCriteria.getFechaFin());
			
			pacienteResult.setEpisodios(episodios);
			
		} catch (Exception e) {
			FacesMessage message = new FacesMessage(
					FacesMessage.SEVERITY_FATAL,"Error Grave","Ocurrió un error grave en el sistema");
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
	}
	

}
