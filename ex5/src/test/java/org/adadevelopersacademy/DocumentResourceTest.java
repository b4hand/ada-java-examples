package org.adadevelopersacademy;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

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

public class DocumentResourceTest {

    private static final String DOCUMENT_PATH = "document/";
    private static final String KEY = "key";
    private static final String VALUE = "value";

    private HttpServer server;
    private WebTarget target;

    @Before
    public void setUp() throws Exception {
        // Clearing any existing documents between tests ensures that
        // we have a consistent starting point for our unit
        // tests. Ensuring proper isolation for unit tests is an
        // important quality and not doing it properly can cause tests
        // to fail sporadically or fail when run in a different
        // order. The Maven test runner has support to run your tests
        // in a random order, but I haven't currently enabled
        // that. You may want to see if you can look up how to do that
        // and enable it for this project.
        DocumentResource.clear();

        // start the example server
        server = Main.startServer();
        // create a client for testing
        final Client c = ClientBuilder.newClient();

        // This test fixture creates a Jersey-based HTTP client for
        // testing the server. This gives you yet another way to write
        // a HTTP client from the prior example.
        target = c.target(Main.BASE_URI);
    }

    @After
    public void tearDown() throws Exception {
        // Clean up the server after each test. Again this ensures
        // proper test isolation.
        server.shutdownNow();
    }

    /**
     * Returns 404 for unknown documents.
     */
    @Test
    public void notFoundForUnknownDocuments() {
        // The document repository starts empty, so we should get a
        // 404 Not Found for any ID that we ask about.
        final Response response = target
            .path(DOCUMENT_PATH + 999)
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
        // This is an example of a basic PUT request. We put a new
        // document to ID 0.
        final JsonObject document = Json
            .createObjectBuilder()
            .add(KEY, VALUE)
            .build();
        final Response response = target
            .path(DOCUMENT_PATH + 0)
            .request(MediaType.APPLICATION_JSON)
            .put(Entity.json(document.toString()));
        final JsonObject responseObject =
            response.readEntity(JsonObject.class);
        assertThat(responseObject, equalTo(document));
    }

    /**
     * Can get back the same document after previously setting it.
     */
    @Test
    public void canSetAndGet() {
        // This test round-trips a document with a PUT then a GET.
        final JsonObject document = Json
            .createObjectBuilder()
            .add(KEY, VALUE)
            .build();
        target
            .path(DOCUMENT_PATH + 0)
            .request(MediaType.APPLICATION_JSON)
            .put(Entity.json(document.toString()));
        final Response response = target
            .path(DOCUMENT_PATH + 0)
            .request(MediaType.APPLICATION_JSON)
            .get();
        final JsonObject responseObject =
            response.readEntity(JsonObject.class);
        assertThat(responseObject, equalTo(document));
    }

    /**
     * Can overwrite a document and get the new document back.
     */
    @Test
    public void canOverwriteAndGet() {
        // This test is similar to the prior one except we PUT two
        // different documents to the same ID. We expect the GET to
        // return the final document.
        final JsonObject oldDocument = Json
            .createObjectBuilder()
            .add(KEY, "oldValue")
            .build();
        final JsonObject newDocument = Json
            .createObjectBuilder()
            .add(KEY, "newValue")
            .build();
        target
            .path(DOCUMENT_PATH + 0)
            .request(MediaType.APPLICATION_JSON)
            .put(Entity.json(oldDocument.toString()));
        target
            .path(DOCUMENT_PATH + 0)
            .request(MediaType.APPLICATION_JSON)
            .put(Entity.json(newDocument.toString()));
        final Response response = target
            .path(DOCUMENT_PATH + 0)
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
        // This test creates a new document and ensures we get back a
        // valid document ID to retrieve it with later.
        final JsonObject document = Json
            .createObjectBuilder()
            .add(KEY, VALUE)
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
        // This test round-trips a document with a POST then a GET.
        final JsonObject expected = Json
            .createObjectBuilder()
            .add(KEY, VALUE)
            .build();
        final Response createResponse = target
            .path("document")
            .request(MediaType.APPLICATION_JSON)
            .post(Entity.json(expected.toString()));
        final int docId =
            createResponse.readEntity(JsonObject.class).getInt("docId");
        final Response getResponse = target
            .path(DOCUMENT_PATH + docId)
            .request(MediaType.APPLICATION_JSON)
            .get();
        final JsonObject actual = getResponse.readEntity(JsonObject.class);
        assertThat(actual, equalTo(expected));
    }

    /**
     * Refuses to create a document when the document ID space is
     * exhausted.
     */
    @Test
    public void refusesToCreate() {
        // This test demonstrates the issue with using the maximum ID
        // as a proxy for finding an unoccupied ID in the document
        // storage.
        final JsonObject document = Json
            .createObjectBuilder()
            .add(KEY, VALUE)
            .build();
        // Notice Integer.MAX_VALUE represents the maximum possible
        // positive integer. Thus this is the largest possible
        // document ID. By storing a document at this location, we
        // will no longer be able to create new documents via POST.
        target
            .path(DOCUMENT_PATH + Integer.MAX_VALUE)
            .request(MediaType.APPLICATION_JSON)
            .put(Entity.json(document.toString()));
        final Response response = target
            .path("document")
            .request(MediaType.APPLICATION_JSON)
            .post(Entity.json(document.toString()));
        assertThat(
            response.getStatus(),
            equalTo(Response.Status.FORBIDDEN.getStatusCode()));
        // Technically, you can still PUT documents to arbitrary IDs,
        // but the DocumentResource is unaware that those IDs are
        // unused.
    }
}
