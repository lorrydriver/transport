package at.ko.transport.business.rechnung.boundary;

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

import at.ko.transport.business.cmr.boundary.CmrAnschriftManager;
import at.ko.transport.business.cmr.entity.CmrAnschrift;
import at.ko.transport.business.rechnung.entity.RechnungsAnschrift;

@Stateless
@Path("rechnung/anschrift")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class RechnungsAnschriftResource {

		@Inject
		RechnungsAnschriftManager manager;

		@POST
		public Response save(RechnungsAnschrift rechnungsAnschrift, @Context UriInfo info) {
			RechnungsAnschrift saved = this.manager.save(rechnungsAnschrift);
	        long id = saved.getId();
	        URI uri = info.getAbsolutePathBuilder().path("/" + id).build();
	        return Response.created(uri).build();
		}


}
