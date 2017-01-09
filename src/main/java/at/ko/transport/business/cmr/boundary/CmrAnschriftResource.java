package at.ko.transport.business.cmr.boundary;

import java.net.URI;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import at.ko.transport.business.cmr.entity.CmrAnschrift;

@Stateless
@Path("cmr/anschrift")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class CmrAnschriftResource {

	@Inject
	CmrAnschriftManager manager;

	@POST
	public Response save(CmrAnschrift cmrAnschrift, @Context UriInfo info) {
		CmrAnschrift saved = this.manager.save(cmrAnschrift);
        long id = saved.getId();
        URI uri = info.getAbsolutePathBuilder().path("/" + id).build();
        return Response.created(uri).build();
	}

}
