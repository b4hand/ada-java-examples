/**
 * This is an example program in Java. To run it, do the following
 * from the command line:
 *
 * 1) javac Hello.java
 * 2) java Hello
 *
 * Alternatively, you can also pass a command-line argument to print
 * your own name:
 *
 * java Hello Jane
 */
public class Hello {
    /**
     * `name` is a instance variable of the `Hello` class.
     *
     * We'll use it to store to whom we're going to say "Hello".
     *
     * Note, it's not uncommon for the data members to come first in a
     * class. I typically put them at the end, but some people put
     * them first. Others will put them last it just depends. I'll
     * follow the Java convention in this example, but YMMV.
     */
    private String name;

    /**
     * This method is called a constructor. Notice it does not have a
     * return value. You may not use `void` here and it must not be
     * `static` and it must be the same name as the class you are
     * inside. If you give it a return type like `void`, Java will be
     * confused and think you are defining a method named `Hello` on
     * the `Hello` class. That is *not* what you want.
     *
     * @param name The name to whom we will say "Hello".
     */
    public Hello(String name) {
      // `this` is like `self` in Ruby.
      this.name = name;
      // Note, some people consider it bad style to use the same name
      // of the method parameter as a member variable, but others
      // (like myself) find it convenient. The people who don't like
      // it will often use some form of Hungarian notation to
      // distinguish between member variables and argument variables.
      //
      // https://en.wikipedia.org/wiki/Hungarian_notation
      //
      // For example, mName for the member variable and aName for the
      // argument variable.
    }

    /**
     * Prints out a greeting.
     *
     * This is an instance method. Notice it does not say
     * `static`. Instance methods are invoked on instances. Sometimes
     * instance methods are just called _methods_ for short.
     */
    public void say() {
        // Note that I don't have to say `this` inside an instance
        // method. That is assumed.
        System.out.println("Hello, " + name + "!");
    }

    /**
     * The main method is called when invoking a class from the
     * command line. It must have the following signature.
     */
    public static void main(String[] args) {
        // This "name" variable is a local variable which is different
        // from the member variable also named "name". Static methods
        // do not have access to instance's member variables.
        String name = "World";

        // Here "args" are passed on the command line, and is a
        // "built-in" primitive array. Java arrays have a built-in
        // length that tells you their length similar to Ruby's
        // Array#size method.
        if (args.length >= 1) {
           name = args[0];
        }

        // This is a common idiom for the `main` method to create a
        // temporary object and then immediately invoke a single
        // method on that object.
        new Hello(name).say();
    }
}
