# Example 5

This example is based on [Jersey](https://jersey.java.net/).

I generated this initial project using a Jersey-provided Maven
archetype:

```
mvn archetype:generate -DarchetypeArtifactId=jersey-quickstart-grizzly2 \
    -DarchetypeGroupId=org.glassfish.jersey.archetypes \
    -DinteractiveMode=false -DgroupId=org.adadevelopersacademy \
    -DartifactId=java-example-5 -Dpackage=org.adadevelopersacademy \
    -DarchetypeVersion=2.22.1
```

This is similar to running `rails new` to generate a new application.

You can also see how to deploy your application to Heroku by using a
different archetype and following the
[Getting Started](https://jersey.java.net/documentation/latest/getting-started.html#heroku-webapp)
instructions.

For right now, this will just give a locally runnable web service
example.

Jersey by default does not support JSON out of the box since XML is
the more common transport for REST services in the Java universe. Java
being Java provides three different approaches of supporting the
[JSON media type](https://jersey.java.net/documentation/latest/media.html#d0e7951). The
most common approach is the same way that Java supports almost
everything: via
[POJOs](https://en.wikipedia.org/wiki/Plain_Old_Java_Object).

This approach uses an automatic mapping between Java class members and
the fields in a JSON object. It's subtle, but requires you to create a
class for every possible input and return value for your API.

For a very simple example like this one, that's maybe overkill, so I
chose the more "low-level" JSON-Processing interface which gives
direct access to constructing and parsing JSON objects.

The following [block in my pom.xml](pom.xml#L48-L52) adds the JSON
support to this project:

```xml
<dependency>
  <groupId>org.glassfish.jersey.media</groupId>
  <artifactId>jersey-media-json-processing</artifactId>
  <version>2.22.1</version>
</dependency>
```

The template for this project created the following three files:

* [`Main.java`](https://github.com/b4hand/ada-java-examples/blob/d97b806d020b0fcf7da370726c9250dac79ce1d9/ex5/src/main/java/org/adadevelopersacademy/Main.java)
* [`MyResource.java`](https://github.com/b4hand/ada-java-examples/blob/d97b806d020b0fcf7da370726c9250dac79ce1d9/ex5/src/main/java/org/adadevelopersacademy/MyResource.java)
* [`MyResourceTest.java`](https://github.com/b4hand/ada-java-examples/blob/d97b806d020b0fcf7da370726c9250dac79ce1d9/ex5/src/test/java/org/adadevelopersacademy/MyResourceTest.java)

`Main.java` is mostly boilerplate and not very interesting, but it
does setup the web server and acts as the primary entry point
including the standard `main` method.

I renamed `MyResource.java` and `MyResourceTest.java` to
`DocumentResource.java` and `DocumentResourceTest.java` respectively
in
[commit 0016f4](https://github.com/b4hand/ada-java-examples/commit/0016f4).
These are the main logic and test case for this web service. A
_resource_ is the "R" in
[URI](https://en.wikipedia.org/wiki/Uniform_Resource_Identifier) and
is the basic building block for web services in Jersey. You can read
more about
[resources in the Jersey docs](https://jersey.java.net/documentation/latest/jaxrs-resources.html).
A simple explanation would be that a resource is an object or
collection of objects which you'd like to manipulate via a REST
service. The standard
[CRUD](https://en.wikipedia.org/wiki/Create,_read,_update_and_delete)
operations are all directly modeled in the HTTP protocol via POST,
GET, PUT, and DELETE.

Jersey and the corresponding
[JAX-RS specification](https://en.wikipedia.org/wiki/Java_API_for_RESTful_Web_Services)
on which Jersey is based rely heavily on
[Java annotations](https://en.wikipedia.org/wiki/Java_annotation) to
indicate behavior. We've seen examples of annotations before like the
`@Test` and `@Before` annotations in JUnit and the `@Override`
annotation which is a standard part of the Java language.

Go ahead and begin reading
[`DocumentResource.java`](src/main/java/org/adadevelopersacademy/DocumentResource.java).
