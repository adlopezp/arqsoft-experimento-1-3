/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.ecos.statusquo.web.services;

import co.edu.uniandes.ecos.statusquo.business.EpisodioEJB;
import co.edu.uniandes.ecos.statusquo.persistence.entities.Episodio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Dev
 */
@Path("/episodioDoctor")
@Stateless
@Produces(MediaType.APPLICATION_JSON)
public class EpisodioDoctorService {

    @EJB
    private EpisodioEJB episodioService;
    @EJB
    private EpisodioPacienteService service;

    @GET
    @Path("/getEpisodio")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEpisodio(String id) {
        return service.getEpisodio(id);
    }
    
    @GET
    @Path("/getAll")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(String paciente) {
        return service.getAll(paciente);
    }
    
    @GET
    @Path("/getEpisodioFechas")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEpisodioFechas(Date fechaInicio,Date fechaFin) {
        List<Episodio> episodio;
        try {
            episodio = episodioService.consultarFechas(fechaInicio, fechaFin);
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
            episodio = new ArrayList<Episodio>();
        }
        return Response.status(200).header("Access-Control-Allow-Origin", "*").entity(episodio).build();
    }

    @OPTIONS
    public Response cors(@javax.ws.rs.core.Context HttpHeaders requestHeaders) {
        return Response.status(200).header("Access-Control-Allow-Origin", "*").header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS").header("Access-Control-Allow-Headers", "AUTHORIZATION, content-type, accept").build();
    }
}
