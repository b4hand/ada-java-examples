package org.adadevelopersacademy;

// These import the hamcrest matchers directly into the local
// namespace. In general, it's not a great idea to import lots of
// names statically, but it can be convenient as a form of
// short-hand. It's particularly common to do so for unit testing
// frameworks.
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
// This imports the JUnit assertions. Again wildcard imports are
// generally frowned upon, but JUnit is usually an exception to this
// rule because it is extremely common.
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * This is an example of a JUnit 4 style test. There are older ways of
 * writing unit tests, but this is the current preferred style and at
 * this point, you're unlikely to encounter code still using JUnit 3.
 *
 * Here's the junit website: http://junit.org/
 *
 * I've setup the build to automatically run these tests when you run
 * "mvn package" from the command line. Go ahead and try doing that
 * now.
 *
 * Once you've finished reading this test suite continue on to reading
 * {@see TalkingDog}.
 */
public class DogTest {
    private Dog dog;

    // The @Before decorator says that this method will be run before
    // each and every test method in this class. This is commonly used
    // for setting up test fixtures common to a single test suite.
    //
    // All @Before and @Test methods must be public as they are called
    // externally by the unit test framework.
    //
    // It's traditional to name a @Before method setUp.
    @Before
    public void setUp() {
        dog = new Dog();
    }

    // This @Test decorator declares that this method is a test
    // function and will be invoked automatically by the test suite.
    @Test
    public void getSoundReturnsBark() {
        // The simplest way to write a test assertion is to use the
        // basic JUnit assert* methods.
        //
        // You can find the list of JUnit assertions available here:
        //
        // http://junit.org/apidocs/org/junit/Assert.html
        assertEquals(dog.getSound(), "Bark!");

        // A common alternative is to use the Hamcrest
        // assertions. They are a little more readable as English
        // sentences, and they provide both better error messages and
        // more power than the standard JUnit assertions.
        //
        // Here's a really quick cheat sheet for all of the available
        // Hamcrest assertions:
        //
        // http://bit.ly/1kneZD2
        assertThat(dog.getSound(), is("Bark!"));
    }
}
