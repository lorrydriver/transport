package at.ko.transport.business.cmr.boundary;

import java.net.URI;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import at.ko.transport.business.cmr.entity.Cmr;

@Stateless
@Path("cmr")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class CmrResource {

	@Inject
	CmrManager manager;

	@POST
	public Response save(Cmr cmr, @Context UriInfo info) {
		Cmr saved = this.manager.save(cmr);
        long id = saved.getId();
        URI uri = info.getAbsolutePathBuilder().path("/" + id).build();
        return Response.created(uri).build();
	}

	@GET
	@Path("{id}")
	public Cmr find(@PathParam("id") long id) {
		return manager.findById(id);
	}

	@GET
	public void find1() {
		System.out.println("found");
	}
}
