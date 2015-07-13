/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.ecos.statusquo.web.services;

import co.edu.uniandes.ecos.statusquo.business.EpisodioEJB;
import co.edu.uniandes.ecos.statusquo.persistence.entities.Episodio;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.simple.JSONObject;

/**
 *
 * @author Dev
 */
@Path("/episodioPaciente")
@Stateless
@Produces(MediaType.APPLICATION_JSON)
public class EpisodioPacienteService {

    @EJB
    private EpisodioEJB episodioService;

    @GET
    @Path("/getEpisodio")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEpisodio(String id) {
        Episodio episodio;
        try {
            episodio = episodioService.consultarId(Long.parseLong(id));
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
            episodio = new Episodio();
            return Response.status(500).header("Access-Control-Allow-Origin", "*").entity(episodio).build();
        }
        return Response.status(200).header("Access-Control-Allow-Origin", "*").entity(episodio).build();
    }
    
    @GET
    @Path("/getAll")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(String paciente) {
        List<Episodio> episodio;
        try {
            episodio = episodioService.consultarLista(Long.parseLong(paciente));
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
            episodio = new ArrayList<Episodio>();
            return Response.status(500).header("Access-Control-Allow-Origin", "*").entity(episodio).build();
        }
        return Response.status(200).header("Access-Control-Allow-Origin", "*").entity(episodio).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response crearEpisodio(Episodio episodio) {

        final JSONObject rta = new JSONObject();

        try {
            episodioService.save(episodio);
            rta.put("episodio_id", episodio.getId());
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
            return Response.status(500).header("Access-Control-Allow-Origin", "*").entity(0).build();
        }

        return Response.status(200).header("Access-Control-Allow-Origin", "*").entity(rta.toJSONString()).build();
    }
    
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response eliminarEpisodio(Episodio paciente) {

        final JSONObject rta = new JSONObject();

        try {
            episodioService.remove(paciente);
            rta.put("paciente_id", paciente.getId());
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
            return Response.status(500).header("Access-Control-Allow-Origin", "*").entity(0).build();
        }

        return Response.status(200).header("Access-Control-Allow-Origin", "*").entity(rta.toJSONString()).build();
    }

    @OPTIONS
    public Response cors(@javax.ws.rs.core.Context HttpHeaders requestHeaders) {
        return Response.status(200).header("Access-Control-Allow-Origin", "*").header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS").header("Access-Control-Allow-Headers", "AUTHORIZATION, content-type, accept").build();
    }
}