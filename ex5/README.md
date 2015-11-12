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

For right now, this will just give a locally runnable web service example.
