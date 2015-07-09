package co.edu.uniandes.ecos.statusquo.controllers;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import co.edu.uniandes.ecos.statusquo.business.PacienteEJB;
import co.edu.uniandes.ecos.statusquo.criteria.PacienteCriteria;
import co.edu.uniandes.ecos.statusquo.persistence.entities.Paciente;

@ManagedBean
@SessionScoped
public class PacienteController {
	
	@EJB
	private PacienteEJB pacienteEJB;
	
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
			pacienteResult = pacienteEJB.consultarIdentificacion(pacienteCriteria.getIdentificacion());
			if (pacienteResult == null) {
				FacesMessage message = new FacesMessage(
						FacesMessage.SEVERITY_WARN,"No hay coincidencia","No existe un paciente con dichos criterios");
				FacesContext.getCurrentInstance().addMessage(null, message);
				return;
			}			
		} catch (Exception e) {
			FacesMessage message = new FacesMessage(
					FacesMessage.SEVERITY_FATAL,"Error Grave","Ocurrió un error grave en el sistema");
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
	}
	

}
