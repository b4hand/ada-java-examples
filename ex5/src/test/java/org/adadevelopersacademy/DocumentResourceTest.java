package org.adadevelopersacademy;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.io.StringReader;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.grizzly.http.server.HttpServer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class DocumentResourceTest {

    private HttpServer server;
    private WebTarget target;

    @Before
    public void setUp() throws Exception {
        DocumentResource.clear();

        // start the server
        server = Main.startServer();
        // create the client
        Client c = ClientBuilder.newClient();

        target = c.target(Main.BASE_URI);
    }

    @After
    public void tearDown() throws Exception {
        server.shutdownNow();
    }

    /**
     * Returns 404 for unknown documents.
     */
    @Test
    public void notFoundForUnknownDocuments() {
        final Response response = target
            .path("document/999")
            .request(MediaType.APPLICATION_JSON)
            .get();
        assertThat(
            response.getStatus(),
            equalTo(Response.Status.NOT_FOUND.getStatusCode()));
    }

    /**
     * Can set a new document.
     */
    @Test
    public void canSet() {
        final JsonObject document = Json
            .createObjectBuilder()
            .add("key", "value")
            .build();
        final Response response = target
            .path("document/0")
            .request(MediaType.APPLICATION_JSON)
            .put(Entity.json(document.toString()));
        final JsonObject responseObject =
            response.readEntity(JsonObject.class);
        assertThat(responseObject, equalTo(document));
    }

    /**
     * Can overwrite a document and get the new document back.
     */
    @Test
    public void canOverwriteAndGet() {
        final JsonObject oldDocument = Json
            .createObjectBuilder()
            .add("key", "oldValue")
            .build();
        final JsonObject newDocument = Json
            .createObjectBuilder()
            .add("key", "newValue")
            .build();
        target
            .path("document/0")
            .request(MediaType.APPLICATION_JSON)
            .put(Entity.json(oldDocument.toString()));
        target
            .path("document/0")
            .request(MediaType.APPLICATION_JSON)
            .put(Entity.json(newDocument.toString()));
        final Response response = target
            .path("document/0")
            .request(MediaType.APPLICATION_JSON)
            .get();
        final JsonObject responseObject =
            response.readEntity(JsonObject.class);
        assertThat(responseObject, equalTo(newDocument));
    }

    /**
     * Can create a new document.
     */
    @Test
    public void canCreate() {
        final JsonObject document = Json
            .createObjectBuilder()
            .add("key", "value")
            .build();
        final Response response = target
            .path("document")
            .request(MediaType.APPLICATION_JSON)
            .post(Entity.json(document.toString()));
        final JsonObject responseObject =
            response.readEntity(JsonObject.class);
        assertTrue(responseObject.containsKey("docId"));
    }

    /**
     * Can fetch the original document after creating one.
     */
    @Test
    public void canCreateAndGet() {
        final JsonObject expected = Json
            .createObjectBuilder()
            .add("key", "value")
            .build();
        final Response createResponse = target
            .path("document")
            .request(MediaType.APPLICATION_JSON)
            .post(Entity.json(expected.toString()));
        final int docId =
            createResponse.readEntity(JsonObject.class).getInt("docId");
        final Response getResponse = target
            .path("document/" + Integer.toString(docId))
            .request(MediaType.APPLICATION_JSON)
            .get();
        final JsonObject actual = getResponse.readEntity(JsonObject.class);
        assertThat(actual, equalTo(expected));
    }

    /**
     * Refuses to create when no more document ids.
     */
    @Test
    public void refusesToCreate() {
        final JsonObject document = Json
            .createObjectBuilder()
            .add("key", "value")
            .build();
        target
            .path("document/" + Integer.MAX_VALUE)
            .request(MediaType.APPLICATION_JSON)
            .put(Entity.json(document.toString()));
        final Response response = target
            .path("document")
            .request(MediaType.APPLICATION_JSON)
            .post(Entity.json(document.toString()));
        assertThat(
            response.getStatus(),
            equalTo(Response.Status.FORBIDDEN.getStatusCode()));
    }
}
