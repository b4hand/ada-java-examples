# Example 4

This is a very simple Web client example using the Apache Commons
HttpComponents's client and talking to Loraine's
[Book Duets API](https://github.com/lorainekv/book-duets-api).

It also demonstrates some basic file IO by writing out the results to a file.

You can find many open source software packages for Java on Maven
Central. Technically, Maven Central, provides a search engine. It is
here:

http://search.maven.org/

but I don't think it provides as much information as this other site,
and it seems to pop up in Google results more often anyway:

http://mvnrepository.com/

I happened to know I wanted to use this library, but Apache Commons is
a good place to start looking for useful functionality. Another good
place is Google's Guava library.

I did a Google search for "apache httpcomponents maven". This is a
good way to find out the necessary XML that you need to add to your
`pom.xml` to include the dependency in your project. In this case,
that led me to this page:

http://mvnrepository.com/artifact/org.apache.httpcomponents

In most cases, you'll want to grab the latest version of a library in
Java as they don't update nearly as frequently as the Ruby world. If
you already have a large set of dependencies, it may be more
complicated choosing a version because you have to find one that
doesn't conflict with any of your existing dependencies. This can
essentially be a process of trial and error. Looking at this page:

http://mvnrepository.com/artifact/org.apache.httpcomponents/httpclient/4.5.1

You can see right in the middle of that page what the XML that you
need to copy and paste is:

```xml
<dependency>
  <groupId>org.apache.httpcomponents</groupId>
  <artifactId>httpclient</artifactId>
  <version>4.5.1</version>
</dependency>
```

Another good tip is that most Javadocs are published online, and thus,
you can often just search for a class name or attaching java like
"String java" and find the documentation for it. If the class name
doesn't work, try searching for the fully qualified class name. The
"fully qualified class name" is the class name with the full package
name attached in dot form like `java.lang.String`.
