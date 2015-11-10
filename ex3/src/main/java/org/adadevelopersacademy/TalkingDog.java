package org.adadevelopersacademy;

/**
 * TalkingDog extends Dog. This means that it inherits any
 * implementation details of Dog, but it can still override methods.
 *
 * Once you've finished reading this class continue on to reading
 * {@see TalkingDogTest}.
 */
public class TalkingDog extends Dog {
    private String name;

    /**
     * This class has a non-default constructor. It takes at least one
     * argument. Thus, Java will not automatically generate a default
     * constructor. If you want to construct the object without
     * requiring arguments, you must also declare a _separate_ default
     * constructor.
     *
     * The "final" keyword indicates that the argument variable
     * reference is not reassigned within the method. It can also be
     * used for local and member variables. Note that this is not the
     * same as a "constant". It's more equivalent to it can be
     * assigned only once.
     *
     * A final member variable can only be assigned through either
     * direct initialization or in the constructor. It is an error to
     * not initialize a final member variable in the constructor.
     */
    public TalkingDog(final String name) {
        // IllegalArgumentException is one of the standard Java exceptions:
        //
        // http://bit.ly/1MmFjnr
        //
        // It is an instance of a RuntimeException, and thus is not a
        // "checked" exception. I'll explain the difference between
        // checked and unchecked exceptions later.
        if (name == null) {
            throw new IllegalArgumentException("name must not be null");
        }
        this.name = name;
    }

    /**
     * A TalkingDog can greet you with words.
     *
     * Note that since we've overriden the getSound method, TalkingDog
     * does not behave the same as Dog.  This is one form of
     * polymorphism in object oriented programming.
     */
    @Override
    public String getSound() {
        // This is an example of a format string. The syntax is a
        // mini-language based on C-style printf strings. You can read
        // more about it here:
        //
        // http://bit.ly/1MUYXN9
        return String.format("Hello, my name is %s.", name);
    }

    public void setName(final String name) {
        this.name = name;
    }

}
