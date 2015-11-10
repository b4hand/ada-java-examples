// Strictly speaking, you don't need to declare your package name in
// every file; however, it is common practice to do so in Java.
//
// If you do declare your package, it must match the actual directory
// structure in your project.
package org.adadevelopersacademy;

/**
 * This is an "interface". Interfaces declare the available methods on
 * a hierarchy of classes. Interfaces are the opposite of duck typing
 * in Ruby. Java must know what methods are available on an object
 * based solely on its type. Interfaces are a way of declaring a
 * common set of methods across a group of classes.
 *
 * For a concrete example of an Animal, {@see Dog}.
 */
interface Animal {
    /**
     * This says that all Animals have a sound.
     */
    String getSound();
}
