package org.adadevelopersacademy;

// Gson is a JSON library for Java written by Google.
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

// I'm going to pull in several standard classes. I'd recommend
// reading about each one after you finish reading this code.
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;

import java.util.Collections;
import java.util.List;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;

/**
 * You'll need to talk to Loraine about getting an API key for the
 * Book Duets API in order to run this code.
 *
 * I've set this package up as a Maven package, so you can run this
 * class using:
 *
 *     BOOK_DUETS_SECRET_KEY='SECRET_KEY_HERE' mvn exec:java \
 *         -Dexec.class=org.adadevelopersacademy.BookDuetsClient \
 *         -Dexec.args=output.txt
 *
 * Alternatively, you'll notice that I've already specified a default
 * value for the exec.class setting in the pom.xml, so you can do this
 * instead:
 *
 *     BOOK_DUETS_SECRET_KEY='SECRET_KEY_HERE' mvn exec:java -Dexec.args=output.txt
 *
 * One thing to keep in mind is that "mvn exec:java" does not rebuild
 * your code, so you'll need to run "mvn package" first, and if you
 * attempt to make any changes to this code, you'll need to run "mvn
 * package" before you are able to see the effects of the changes.
 */
public class BookDuetsClient {
    private static final String SECRET_KEY_ENV_VAR =
        "BOOK_DUETS_SECRET_KEY";
    private static final String SECRET_KEY_HEADER =
        "Book-Duets-Key";
    private static final String BOOK_DUET_URL =
        "http://api.bookduets.com/suggested_pairing?filter_level=";
    private static final String BOOK_DUET_FIELD = "book_duet";

    private final String secretKey;

    /**
     * An enum is a collection of related symbolic constants. In Ruby,
     * you'd probably just use some symbols or maybe constants inside
     * of a module, but enums provide compile-time guarantees that the
     * value is within the expected range.
     */
    public static enum FilterLevel {
        // It's pretty typical for enumeration values to be "screaming
        // snake case" or upper case with underscores.
        NONE,
        MED,
        HI
    }

    public BookDuetsClient(final String secretKey) {
        this.secretKey = secretKey;
    }

    // java.io.IOException is a checked exception. I mentioned the
    // concept of checked exceptions previously, but it means that you
    // must declare any checked exception that is thrown from a
    // function. In this case, suggestedPairing() doesn't directly
    // throw IOException, but it calls several functions which happen
    // to potentially throw an exception.
    public String suggestedPairing(final FilterLevel filterLevel)
        throws IOException {
        // This is a very common, but subtle idiom in Java. Notice,
        // I'm constructing a BasicHeader, but assigning it to a
        // Header. In this context, BasicHeader is an implementation,
        // but Header is the interface. This is commonly referred to
        // as "programming to an interface instead of an
        // implementation." It comes from a principle out of this book:
        //
        // http://www.amazon.com/dp/0201633612/
        final Header header = new BasicHeader(SECRET_KEY_HEADER, secretKey);

        // Java does not have a short-hand literal syntax for creating
        // lists like Ruby does. Instead, you have to build them by
        // hand. However, Java does provide a "convenient" constructor
        // for creating a list of exactly one element:
        //
        // http://bit.ly/1NLr8wW
        final List<Header> headers = Collections.singletonList(header);

        // Anytime something has a close() method, it is your
        // responsibility to ensure that it is called. Most classes
        // will implement the Closeable and/or the AutoCloseable
        // interface to indicate that you need to call close().
        //
        // You can see from the CloseableHttpClient documentation that
        // it implements both the Closeable and AutoCloseable
        // interface:
        //
        // http://bit.ly/1HyMrAm
        //
        // In Java 7 and greater, you can use a shorthand notation,
        // for classes that are AutoCloseable this is called:
        // "try-with-resources", and you can read more about it here:
        //
        // http://bit.ly/1xCmiL1
        //
        // The primary thing to realize is that this structure
        // automatically calls close() for you when the block is
        // finished even if an exception is thrown within the body of
        // the block of code. This is very good for you because it
        // makes your life easier, and you don't have to handle as
        // many error cases. You should use try-with-resources as much
        // as possible unless it is specifically not usable because
        // you are using an older version of Java or the class does
        // not support it via AutoCloseable.
        //
        // Notice also that I'm using the "final" keyword for my local
        // variables. This means that I am assigning to them only
        // once. Doing this simplifies the logic of your code and
        // makes it easier to read. Reassigning the same variable
        // multiple times within the same section of code makes
        // everyone who reads your code follow the logic very
        // closely. It's a common practice to use "final" extensively
        // in Java.
        try (
            final CloseableHttpClient httpclient =
                HttpClients.custom().setDefaultHeaders(headers).build()) {
            final HttpGet httpget = new HttpGet(
                BOOK_DUET_URL + filterLevel.toString().toLowerCase());
            // Notice that CloseableHttpResponse also extends
            // Closeable. You can look up the documentation yourself
            // as an exercise.
            try (final CloseableHttpResponse response = httpclient.execute(httpget)) {
                final HttpEntity entity = response.getEntity();
                // Notice from the documentation that getContent() is
                // an InputStream and that is also Closeable.
                //
                // Notice you can also have multiple resources that
                // are controlled in the same try-with-resources
                // block.
                try (
                    final InputStream content = entity.getContent();
                    final BufferedReader reader = new BufferedReader(
                        new InputStreamReader(content));
                ) {
                    // I'm specifically keeping error handling to a
                    // minimum, but in a real program, you'd probably
                    // want to catch JSON parse errors a few other
                    // things as well.
                    final JsonParser parser = new JsonParser();
                    final JsonElement element = parser.parse(reader);
                    final String duet =
                        element
                            .getAsJsonObject()
                            .get(BOOK_DUET_FIELD)
                            .getAsString();
                    return duet;
                }
            }
        }

        // Notice that any path that doesn't return above is due to
        // some uncaught exception, and I'm choosing to just let those
        // all pass through.
    }

    // So, because main() calls suggestedPairing(), and
    // suggestedPairing() throws IOException, main() must also declare
    // that it potentially throws IOException.
    public static void main(final String[] args) throws IOException {
        if (args.length != 1) {
            System.err.println("usage: BookDuetsClient <output_file>");
            System.exit(1);
        }

        final String secretKey = System.getenv(SECRET_KEY_ENV_VAR);
        if (secretKey == null || secretKey.isEmpty()) {
            System.err.println(
                "Please set the " + SECRET_KEY_ENV_VAR + " environment variable"
            );
            System.exit(1);
        }

        final String duet = new BookDuetsClient(secretKey).suggestedPairing(
            BookDuetsClient.FilterLevel.NONE);

        try (final FileWriter writer = new FileWriter(args[0])) {
            writer.write(duet);
            writer.write("\n");
        }
    }
}
