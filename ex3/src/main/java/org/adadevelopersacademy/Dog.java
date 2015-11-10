package org.adadevelopersacademy;

/**
 * Dog implements Animal. This means that Animal is a base type of
 * Dog, so Dog must implement all methods declared in Animal.
 *
 * Java allows you to implement more than one interface. This is
 * sometimes called "multiple interface inheritance."  Java only
 * allows you to _extend_ a single class. This is sometimes called
 * "single implementation inheritance."
 *
 * Note: You can have an abstract class which only partially
 * implements an interface. This is sometimes useful to provide
 * default behavior or functionality for a group of classes.
 *
 * After you've finished reading this class, proceed to reading
 * {@see DogTest}.
 */
public class Dog implements Animal {

    // Note: I haven't declared a Dog() constructor for Dog because I
    // don't need one for this class. Java automatically provides a
    // public default do nothing constructor for all classes unless
    // you declare some other constructor.
    //
    // The body for the automatically generated constructor looks like
    // this:
    //
    // public Dog() {}
    //
    // This is called a default constructor because it takes no
    // arguments.

    // The @Override decorator tells the Java compiler that you intend
    // to have a method that has the same type as a base class or
    // interface. In this case, the base interface is Animal. If you
    // happen to mistype the name of the function or the type
    // signature, the @Override decorator will cause the compiler to
    // generate an error. This is a good thing to do in general.
    @Override
    public String getSound() {
        return "Bark!";
    }

    // Note: This method doesn't override anything, so it doesn't use
    // the @Override decorator.
    public void bark() {
        System.out.println(getSound());
    }
}
