/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.ecos.statusquo.web.services;

import co.edu.uniandes.ecos.statusquo.business.DiagnosticoEJB;
import co.edu.uniandes.ecos.statusquo.persistence.entities.Diagnostico;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.simple.JSONObject;

/**
 *
 * @author Dev
 */
@Path("/diagnostico")
@Stateless
@Produces(MediaType.APPLICATION_JSON)
public class DiagnosticoService {

    @EJB
    private DiagnosticoEJB diagnosticoService;

    @GET
    @Path("/getAll")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        List<Diagnostico> diagnosticos;
        try {
            diagnosticos = diagnosticoService.consultar();
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
            diagnosticos = new ArrayList<Diagnostico>();
            return Response.status(500).header("Access-Control-Allow-Origin", "*").entity(diagnosticos).build();
        }
        return Response.status(200).header("Access-Control-Allow-Origin", "*").entity(diagnosticos).build();
    }
    
    @GET
    @Path("/getId")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getId(Long id) {
        Diagnostico diagnostico;
        try {
            diagnostico= diagnosticoService.consultarId(id);
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
            diagnostico = new Diagnostico();
            return Response.status(500).header("Access-Control-Allow-Origin", "*").entity(diagnostico).build();
        }
        return Response.status(200).header("Access-Control-Allow-Origin", "*").entity(diagnostico).build();
    }
    
    @GET
    @Path("/getCatalizador")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCatalizador(String catalizador) {
        List<Diagnostico> diagnosticos;
        try {
            diagnosticos = diagnosticoService.consultarCatalizador(catalizador);
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
            diagnosticos = new ArrayList<Diagnostico>();
            return Response.status(500).header("Access-Control-Allow-Origin", "*").entity(diagnosticos).build();
        }
        return Response.status(200).header("Access-Control-Allow-Origin", "*").entity(diagnosticos).build();
    }
    
    @GET
    @Path("/getTop10")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTop10() {
        List<Diagnostico> diagnosticos;
        try {
            diagnosticos = diagnosticoService.consultarTop10();
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
            diagnosticos = new ArrayList<Diagnostico>();
            return Response.status(500).header("Access-Control-Allow-Origin", "*").entity(diagnosticos).build();
        }
        return Response.status(200).header("Access-Control-Allow-Origin", "*").entity(diagnosticos).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response crearDiagnostico(Diagnostico diagnostico) {

        final JSONObject rta = new JSONObject();

        try {
            diagnosticoService.save(diagnostico);
            rta.put("diagnostico_id", diagnostico.getId());
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
            return Response.status(500).header("Access-Control-Allow-Origin", "*").entity(0).build();
        }

        return Response.status(200).header("Access-Control-Allow-Origin", "*").entity(rta.toJSONString()).build();
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response eliminarDiagnostico(Diagnostico diagnostico) {

        final JSONObject rta = new JSONObject();

        try {
            diagnosticoService.save(diagnostico);
            rta.put("diagnostico_id", diagnostico.getId());
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
