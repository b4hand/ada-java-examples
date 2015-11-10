/**
 * This is an example first program in Java. To run it, do the
 * following from the command line:
 *
 * 1) javac HelloWorld.java
 * 2) java HelloWorld
 *
 * It's traditional for many languages to give you a first program
 * that just prints out "Hello, World!".
 *
 * By the way, this text is inside a comment, and the double asterisk
 * at the front of the comment says this is a javadoc-style comment.
 *
 * Here's some documentation for the details on javadoc comments:
 *
 * http://www.oracle.com/technetwork/articles/java/index-137868.html
 */
public class HelloWorld {
    /**
     * The main method is called when invoking a class from the
     * command line. It must have the following signature.
     *
     * Note that `void` means this function *does not* return a value.
     */
    public static void main(String[] args) {
        // This line says to print out a String, similar to the `puts`
        // method in Ruby:
        System.out.println("Hello, World!");

        // The fully qualified name for System is java.lang.System.
        // Names inside the java.lang package are automatically
        // imported into your namespace by the Java compiler.

        // Thus, I could have equally written the following, but no
        // one writes it this way:
        //
        // java.lang.System.out.println("Hello, World!");

        // Here's the documentation for the System object:
        //
        // http://docs.oracle.com/javase/7/docs/api/java/lang/System.html#out

        // Note that `out` is a static member variable on the `System`
        // class. If it were not static, you would need to have an
        // instance of the `System` object to reference it.

        // Note that you cannot actually create an instance of the
        // `System` object because it's constructor is private.

        // For example, the following line would give an error message
        // if it were uncommented:

        // System s = new System();

        // HelloWorld.java:51: error: System() has private access in System
        //     System s = new System();

        // The constructor for a class is simply the name of the class
        // itself, so System() is the name of the constructor like
        // `initialize` in Ruby.
    }
}
