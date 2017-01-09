package at.ko.tranport.it;

import static com.airhacks.rulz.jaxrsclient.JAXRSClientProvider.buildWithURI;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import org.junit.Rule;
import org.junit.Test;

import com.airhacks.rulz.jaxrsclient.JAXRSClientProvider;

public class CmrAnschriftResourceIT {

	@Rule
	public JAXRSClientProvider provider = buildWithURI("http://localhost:8080/transport-app/api/cmr/anschrift");

	@Test
    public void crud() {
        JsonObjectBuilder CmAnschriftBuilder = Json.createObjectBuilder();
        JsonObject CmAnschriftToCreate = CmAnschriftBuilder.
                add("line1", "Duslo as.").
                add("line2", "ADMINISTRATIVNA").
                add("line3", "BUDOVA 1236").
                add("line4", "SK-927 03 Sala").
                build();

        //create
        Response postResponse = this.provider.target().request().
                post(Entity.json(CmAnschriftToCreate));
        assertThat(postResponse.getStatus(), is(201));
        String location = postResponse.getHeaderString("Location");
        System.out.println("location = " + location);
	}
	
	@Test
    public void crud1() {
        JsonObjectBuilder CmAnschriftBuilder = Json.createObjectBuilder();
        JsonObject CmAnschriftToCreate = CmAnschriftBuilder.
                add("line1", "Int. Kotzina").
                add("line2", "Taubitz 15").
                add("line3", "3522 Lichtenau").
                build();

        //create
        Response postResponse = this.provider.target().request().
                post(Entity.json(CmAnschriftToCreate));
        assertThat(postResponse.getStatus(), is(201));
        String location = postResponse.getHeaderString("Location");
        System.out.println("location = " + location);
	}
}