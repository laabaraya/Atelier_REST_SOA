package ressourcesrest;

import entities.Option;
import metiers.OptionBusiness;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("options")
public class restOption {
   public static OptionBusiness opB = new OptionBusiness();

   // A.1 - POST /options
   @POST
   @Consumes(MediaType.APPLICATION_JSON)
   public Response addNewOption(Option op) {
      if (op != null && opB.addOption(op)) {
         return Response.status(Response.Status.OK).build();
      }
      return Response.status(Response.Status.NOT_FOUND).build();
   }

   // A.2 et A.3 - GET /options  et  GET /options?domaine=...
   @GET
   @Produces(MediaType.APPLICATION_JSON)
   public Response getOptions(@QueryParam("domaine") String d) {
      List<Option> l;
      if (d == null) {
         l = opB.getListeOptions();
      } else {
         l = opB.getOptionsByDomaine(d);
      }
      return Response.status(Response.Status.OK).entity(l).build();
   }

   // A.4 - DELETE /options/{code}
   @DELETE
   @Path("{code}")
   public Response deleteOption(@PathParam("code") int code) {
      if (opB.deleteOption(code)) {
         return Response.status(Response.Status.NO_CONTENT).build();
      }
      return Response.status(Response.Status.NOT_FOUND).build();
   }

   // A.5 - PUT /options/{code}
   @PUT
   @Path("{code}")
   @Consumes(MediaType.APPLICATION_JSON)
   public Response updateOption(@PathParam("code") int code, Option op) {
      if (opB.updateOption(code, op)) {
         return Response.status(Response.Status.OK).build();
      }
      return Response.status(Response.Status.NOT_FOUND).build();
   }

   // A.6 - GET /options/{code}
   @GET
   @Path("{code}")
   @Produces(MediaType.APPLICATION_JSON)
   public Response getOptionByCode(@PathParam("code") int code) {
      Option op = opB.getOptionByCode(code);
      if (op == null) {
         return Response.status(Response.Status.NOT_FOUND).build();
      }
      return Response.status(Response.Status.OK).entity(op).build();
   }
}