package org.adadevelopersacademy;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TalkingDogTest {
    // This is an example of direct initialization of member
    // variables. It happens to be a static member variable. In this
    // context, static means that the data is stored on the class. You
    // can think of this as similar to class variables in Ruby, but
    // they aren't exactly the same thing.
    //
    // This is also an example of a constant value in Java. It happens
    // to be a constant not because of the final keyword, but because
    // the String class is an immutable object. Remember "final"
    // simply means that the variable is assigned to exactly once.
    //
    // java.lang.String is immutable because there are no methods on
    // it that mutate the underlying state inside a String. Notice
    // that our TalkingDog class is not immutable because we can call
    // setName on it.
    private static final String NAME = "NAME";

    // Here's another example of direct initialization.
    //
    // Note that this is equivalent to creating a constructor and
    // doing the initialiation there. This is just a short hand. It's
    // best not to mix both direct initialization and constructor
    // initialization in the same class because the order of what gets
    // initialized when can get confusing.
    //
    // Some people prefer to always setup fixture data in a unit test
    // suite inside of a setUp() method. However, you may also see
    // this style as well.
    private final TalkingDog dog = new TalkingDog(NAME);

    // This is an example of direct initialization of member variables.
    //
    // We're going to use a ByteArrayOutputStream to capture the
    // System.out output that Dog#bark sends.
    //
    // http://docs.oracle.com/javase/8/docs/api/java/io/ByteArrayOutputStream.html
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    private final PrintStream originalOut = System.out;

    @Before
    public void setUp() {
        System.setOut(new PrintStream(outContent));
    }

    // This is an example of a decorator taking an argument. It says
    // that this test expects to throw an
    // IllegalArgumentException. The test fails if the exception _does
    // not_ happen.
    @Test(expected = IllegalArgumentException.class)
    public void throwsOnNullName() {
        new TalkingDog(null);
    }

    @Test
    public void getSoundIncludesName() {
        assertThat(dog.getSound(), containsString("NAME"));
    }

    @Test
    public void getSoundIsGreeting() {
        assertThat(dog.getSound(), is("Hello, my name is NAME."));
    }

    // Note: I could have written a test for bark in DogTest as well,
    // but I didn't to make the first DogTest simpler.
    @Test
    public void barkOutputsGreeting() {
        dog.bark();
        assertThat(
            outContent.toString(),
            is("Hello, my name is NAME.\n"));
    }

    @Test
    public void setNameChangesSound() {
        dog.setName("OTHER");
        assertThat(dog.getSound(), containsString("OTHER"));
    }

    @Test
    public void setNameChangesGreeting() {
        dog.setName("OTHER");
        assertThat(dog.getSound(), is("Hello, my name is OTHER."));
    }

    // @After method's are like @Before methods, but they run _after_
    // each unit test method is ran.
    //
    // @After method's are less common, but sometimes they are
    // necessary. In this case, we want to make sure to reset the
    // System.out back to its default value in case any other classes
    // want to use it.
    @After
    public void tearDown() {
        System.setOut(originalOut);
    }
}
