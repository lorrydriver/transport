package at.ko.tranport.it;

import static com.airhacks.rulz.jaxrsclient.JAXRSClientProvider.buildWithURI;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Rule;
import org.junit.Test;

import com.airhacks.rulz.jaxrsclient.JAXRSClientProvider;

public class CmResourceIT {

	@Rule
	public JAXRSClientProvider provider = buildWithURI("http://localhost:8081/transport-app/api/cmr");

	@Test
    public void crud() {
        JsonObjectBuilder cmrBuilder = Json.createObjectBuilder();
        JsonObject cmrToCreate = cmrBuilder.
                add("kfzLkw", "KR277CI").
                build();

        //create
        Response postResponse = this.provider.target().request().
                post(Entity.json(cmrToCreate));
        assertThat(postResponse.getStatus(), is(201));
        String location = postResponse.getHeaderString("Location");
        System.out.println("location = " + location);

        JsonObject dedicatedcmr = this.provider.client().
                target(location).
                request(MediaType.APPLICATION_JSON).
                get(JsonObject.class);
        assertTrue(dedicatedcmr.getString("kfzLkw").contains("KR277CI"));
        assertThat(dedicatedcmr.keySet(), hasItem("version"));
        System.out.println(dedicatedcmr.toString());
	}
}