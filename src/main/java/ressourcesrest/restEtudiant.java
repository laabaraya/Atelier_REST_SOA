package ressourcesrest;

import entities.Etudiant;
import entities.EtudiantList;
import entities.Option;
import metiers.EtudiantBusiness;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("etudiants")
public class restEtudiant {
    public static EtudiantBusiness etB = new EtudiantBusiness();

    // B.1 - POST /etudiants
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addNewEtudiant(Etudiant e) {
        if (e != null && e.getOption() != null && etB.addEtudiant(e)) {
            return Response.status(Response.Status.OK).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    // B.2 - GET /etudiants
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllEtudiants() {
        List<Etudiant> l = etB.getAllEtudiants();
        return Response.status(Response.Status.OK).entity(l).build();
    }

    // B.3 - GET /etudiants/{id}
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEtudiantById(@PathParam("id") String id) {
        Etudiant e = etB.getEtudiantByIdentifiant(id);
        if (e == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.status(Response.Status.OK).entity(e).build();
    }

    // B.4 - DELETE /etudiants/{id}
    @DELETE
    @Path("{id}")
    public Response deleteEtudiant(@PathParam("id") String id) {
        if (etB.deleteEtudiant(id)) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    // B.5 - PUT /etudiants/{id}
    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateEtudiant(@PathParam("id") String id, Etudiant e) {
        if (e != null && etB.updateEtudiant(id, e)) {
            return Response.status(Response.Status.OK).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    // B.6 - GET /etudiants/option?codeOption=1  (réponse en XML)
    @GET
    @Path("option")
    @Produces(MediaType.APPLICATION_XML)
    public Response getEtudiantsByOption(@QueryParam("codeOption") int code) {
        Option op = restOption.opB.getOptionByCode(code);
        if (op == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        List<Etudiant> l = etB.getEtudiantsByOption(op);
        return Response.status(Response.Status.OK).entity(new EtudiantList(l)).build();
    }
}