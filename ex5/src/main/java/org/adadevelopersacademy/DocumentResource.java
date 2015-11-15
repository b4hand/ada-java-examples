package org.adadevelopersacademy;

// Java's Map is an interface similar to Ruby's Hash, but Java has
// more than one type of Map. If you want something very similar in
// behavior to Ruby's Hash you want either a HashMap or a
// LinkedHashMap. By default in Ruby 2, Ruby's Hash preserves
// insertion order. LinkedHashMap also provides this same behavior in
// Java, but if you don't need it, you can get slightly better
// performance by simply using HashMap, and typically you'll see
// HashMap used in most Java programs instead of LinkedHashMap.
import java.util.Map;
// NavigableMap is an abstract ordered Map collection which provides
// the lastEntry() operation. I use that below to get an unused
// document id for creating new documents.
import java.util.NavigableMap;
// TreeMap is a concrete implementation of a Map based on a binary
// search tree. It's somewhat slower than a HashMap for many use
// cases, but it provides sorted order iteration over the key space.
import java.util.TreeMap;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.PUT;
import javax.ws.rs.WebApplicationException;

/**
 * Jersey maps a resource onto a portion of the URL space via
 * the @Path annotation. The argument to the @Path annotation
 * indicates the mapping onto the URL. This is similar to how you
 * setup routes in Rails to your controller classes.
 *
 * The leading slash in a @Path is ignored, but I'm specifying here it
 * to be explicit this is the top-level resource, so this declaration
 * would be equivalent to @Path("document").
 *
 * DocumentResource implements a simple in memory JSON document store.
 */
@Path("/document")
public class DocumentResource {

    // In the prior example, I used a List and ArrayList. These
    // classes as well as the Map classes are all part of the Java
    // collection framework. You can read more about collections here:
    //
    // http://docs.oracle.com/javase/7/docs/api/java/util/Collection.html
    //
    // Java collections since JDK 5 are both generic and strictly
    // typed which means you must specify what types they can contain
    // at declaration time. You can read more about generics here:
    //
    // https://docs.oracle.com/javase/tutorial/java/generics/
    //
    // I didn't explain the syntax below before, but I'll do so now.
    //
    // This is a mapping from Integers to JsonObjects. Notice that
    // Integer is not the same type as int. Integer is a
    // java.lang.Object. The int type is not. Java generic collections
    // can only contain Objects. Thus, we must use the Object type
    // Integer as the key. Java does provide implicit conversions
    // between primitive types such as int and the corresponding
    // Object type such as Integer. This operation is called "auto
    // boxing", and you can read more about it here:
    //
    // https://docs.oracle.com/javase/tutorial/java/data/autoboxing.html
    //
    // Autoboxing and unboxing allows you to assign to a variable or
    // pass as an argument to a function an int where an Integer is
    // expected and vice verse. All of the primitive types have a
    // matching "boxed" typed. You should know that even though Java
    // provides this automatic conversion, the conversion is not free,
    // and a common optimization is to avoid autoboxing.
    //
    // The types inside the angle brackets are called "type
    // parameters" and these declare what types are allowed to be
    // stored inside the collection.
    //
    // In the following declaration, I'm also using a special syntax
    // that was introduced in Java 7. This is commonly known as the
    // "diamond operator", but officially, is referred to as "type
    // inference":
    //
    // https://docs.oracle.com/javase/tutorial/java/generics/genTypeInference.html
    //
    // Type inference avoids the previously required duplicate type
    // parameters on the right hand side of an assignment when using a
    // new expression. The old Java 6 syntax would require that I also
    // specify "new TreeMap<Integer, JsonObject>();" on the right hand
    // side as well. You may encounter code that still relies on this
    // older syntax for compatibility reasons.
    //
    // If you're wondering why this declaration is static, I recommend
    // removing the static keyword and trying to get the code to
    // compile without it and seeing which tests fail. You'll discover
    // that DocumentResource objects are not persistent across
    // different requests to the same service. Making this static
    // allows us to have a single, common document repository.
    private static final NavigableMap<Integer, JsonObject> DOCUMENTS =
        new TreeMap<>();

    /**
     * Method handling HTTP GET requests. The returned object will be
     * sent to the client as "application/json" media type.
     *
     * @return JsonObject returned as an application/json response and
     *     corresponding to the document stored at docId.
     *
     * Notice that Java annotations are stackable. You can have more
     * than one on a single declaration. The whitespace between
     * annotations is irrelevant. You can put them on separate lines
     * or not.
     *
     * I've also created a new @Path declaration. Since this
     * declaration is nested under the prior @Path declaration, there
     * is an implicit slash between the two, so this method will be
     * called for GET requests which match the pattern
     * "/document/[0-9]+". You'll notice this syntax is not all that
     * different from path parameters in Rails routes.
     *
     * The @Produces annotation says this method produces values that
     * use the "application/json" media-type which is the standard
     * MIME type for JSON.
     *
     * This method also has an example of an annotation on a function
     * argument. Specifically, the @PathParam annotation says that the
     * previously declared docId parameter in the URL should be passed
     * as the docId argument to this method.
     */
    @GET @Path("{docId: [0-9]+}")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject get(@PathParam("docId") final int docId) {
        final JsonObject document = DOCUMENTS.get(docId);
        if (document == null) {
            // A more typical Java web service would probably create
            // custom exception classes for each error type, but I
            // didn't do that for this simple example.
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
     * @return JsonObject returned as an application/json response and
     *     containing the updated document value.
     */
    @PUT @Path("{docId: [0-9]+}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject set(
            @PathParam("docId") final int docId,
            final JsonObject document) {
        DOCUMENTS.put(docId, document);
        return document;
    }

    /**
     * Method handling HTTP POST requests. The returned object will be
     * sent to the client as "application/json" media type. Newly
     * created documents are given a new document id greater than any
     * existing documents.
     *
     * @return JsonObject returned as an application/json response and
     *     containing the document id of the newly created document.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject create(final JsonObject document) {
        // This is a very naive way of finding an unallocated document
        // id. We can just take the greatest key currently present and
        // add one to it. For this simple example, it is sufficient,
        // but keep in mind that it can be easily fooled into thinking
        // the entire collection is full by first putting a value at a
        // very large id.
        int docId = 0;
        final Map.Entry<Integer, JsonObject> lastEntry = DOCUMENTS.lastEntry();
        if (lastEntry != null) {
            docId = lastEntry.getKey() + 1;
        }
        // Something that may not be obvious coming from Ruby is that
        // integers are fixed precision, and in Java the size of each
        // of the primitive types is fixed. So an int in Java is
        // exactly 32-bits and a long is 64-bits. They are also both
        // signed, so this means the largest positive number that can
        // be represented for an int is (2**31)-1 or 2147483647. If
        // you try to add two numbers that exceed this value, you will
        // roll over to the negative side. This is called integer
        // overflow.
        //
        // If docId is less than zero, the only way it could have
        // reached that value was due to overflow. Thus, this check
        // prevents negative document IDs.
        if (docId < 0) {
            throw new WebApplicationException(
                "No available docId", Response.Status.FORBIDDEN);
        }
        DOCUMENTS.put(docId, document);
        // Chained method calls like this are very common in
        // Java. They are sometimes called "fluent interfaces":
        //
        // https://en.wikipedia.org/wiki/Fluent_interface
        return Json
            .createObjectBuilder()
            .add("docId", docId)
            .build();
    }

    /**
     * Deletes all stored documents.
     *
     * This method is used by the unit tests to isolate state between
     * tests.
     *
     * Proceed to reading DocumentResourceTest.java
     */
    public static void clear() {
        DOCUMENTS.clear();
    }
}
