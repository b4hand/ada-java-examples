package org.adadevelopersacademy;

import java.net.URI;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Request;

/**
 * Root resource (exposed at "document" path). Implements a simple in
 * memory JSON document store.
 */
@Path("/document")
public class DocumentResource {

    private static final NavigableMap<Integer, JsonObject> documents =
        new TreeMap<>();

    /**
     * Method handling HTTP GET requests. The returned object will be
     * sent to the client as "application/json" media type.
     *
     * @return String that will be returned as a application/json response.
     */
    @GET @Path("{docId: [0-9]+}")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject get(@PathParam("docId") final int docId) {
        final JsonObject document = documents.get(docId);
        if (document == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return document;
    }

    /**
     * Method handling HTTP PUT requests. The returned object will be
     * sent to the client as "application/json" media type.
     *
     * @param docId Document id to insert or modify
     * @param document Content of document to be updated
     * @return String that will be returned as a application/json response.
     */
    @PUT @Path("{docId: [0-9]+}")
    @Consumes(MediaType.APPLICATION_JSON)
    public JsonObject set(
            @PathParam("docId") final int docId,
            final JsonObject document) {
        documents.put(docId, document);
        return document;
    }

    /**
     * Method handling HTTP POST requests. The returned object will be
     * sent to the client as "application/json" media type. Newly
     * created documents are given a new document id greater than any
     * existing documents.
     *
     * @return String that will be returned as a application/json response.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject create(final JsonObject document) {
        int docId = 0;
        Map.Entry<Integer, JsonObject> lastEntry = documents.lastEntry();
        if (lastEntry != null) {
            docId = lastEntry.getKey() + 1;
        }
        if (docId < 0) {
            throw new WebApplicationException(
                "No available docId", Response.Status.FORBIDDEN);
        }
        documents.put(docId, document);
        return Json
            .createObjectBuilder()
            .add("docId", docId)
            .build();
    }

    public static void clear() {
        documents.clear();
    }
}
