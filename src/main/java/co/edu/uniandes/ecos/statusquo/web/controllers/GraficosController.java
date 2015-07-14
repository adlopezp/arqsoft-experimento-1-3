package co.edu.uniandes.ecos.statusquo.web.controllers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;

import co.edu.uniandes.ecos.statusquo.business.EpisodioEJB;
import co.edu.uniandes.ecos.statusquo.persistence.entities.Episodio;


@ManagedBean
@RequestScoped
public class GraficosController {
	
	@EJB
	private EpisodioEJB episodioEJB;
	
	private Long idPaciente;
	
	private Date fechaInicio;
	
	private Date fechaFin;
	
	private List<Episodio> episodios;
	
	private LineChartModel datosGrafico;

	public Long getIdPaciente() {
		return idPaciente;
	}

	public void setIdPaciente(Long idPaciente) {
		this.idPaciente = idPaciente;
	}
	
	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}
	
	public LineChartModel getDatosGrafico() {
		return datosGrafico;
	}

	public void setDatosGrafico(LineChartModel datosGrafico) {
		this.datosGrafico = datosGrafico;
	}
	
	public List<Episodio> getEpisodios() {
		return episodios;
	}

	public void setEpisodios(List<Episodio> episodios) {
		this.episodios = episodios;
	}

	@PostConstruct
	public void cargarDatos(){
		
		Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		DateFormat format = new SimpleDateFormat("yyyyMMdd");
		
		idPaciente = new Long(params.get("pacienteId"));
		try {
			fechaInicio = format.parse(params.get("fInicio"));
			fechaFin = format.parse(params.get("fFin"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		episodios = episodioEJB.consultarFechasPaciente(idPaciente, fechaInicio, fechaFin);
 
		datosGrafico = new LineChartModel();
		datosGrafico.setTitle("Evolución del dolor");
		datosGrafico.setLegendPosition("e");
		
		datosGrafico.setShowPointLabels(true);
		datosGrafico.getAxes().put(AxisType.X, new CategoryAxis("Fechas"));
		
		ChartSeries dolorSeries = new ChartSeries();
        dolorSeries.setLabel("Paciente "+ idPaciente);
        
        for (Episodio episodio: episodios) {
        	dolorSeries.set(format.format(episodio.getFecha()), 
        			new Integer(episodio.getNivelDolor()));
        }
        
        datosGrafico.addSeries(dolorSeries); 
		
		System.out.println("GraficosController.cargardatos()");
	}
	
}
