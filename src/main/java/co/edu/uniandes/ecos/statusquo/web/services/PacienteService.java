/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.ecos.statusquo.web.services;

import co.edu.uniandes.ecos.statusquo.business.PacienteEJB;
import co.edu.uniandes.ecos.statusquo.persistence.entities.Paciente;
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
 * @author Alvaro
 */
@Path("/paciente")
@Stateless
@Produces(MediaType.APPLICATION_JSON)
public class PacienteService {

    @EJB
    private PacienteEJB pacienteService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        List<Paciente> pacientes;
        try {
            pacientes = pacienteService.consultar();
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
            pacientes = new ArrayList<Paciente>();
            return Response.status(500).header("Access-Control-Allow-Origin", "*").entity(pacientes).build();
        }
        return Response.status(200).header("Access-Control-Allow-Origin", "*").entity(pacientes).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response crearPaciente(Paciente paciente) {

        final JSONObject rta = new JSONObject();

        try {
            pacienteService.save(paciente);
            rta.put("paciente_id", paciente.getId());
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
            return Response.status(500).header("Access-Control-Allow-Origin", "*").entity(0).build();
        }

        return Response.status(200).header("Access-Control-Allow-Origin", "*").entity(rta.toJSONString()).build();
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response eliminarPaciente(Paciente paciente) {

        final JSONObject rta = new JSONObject();

        try {
            pacienteService.save(paciente);
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
