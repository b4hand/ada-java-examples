# ada-java-examples

These examples are written for Ada Academy graduates to help get
started in Java.

I've created a Vagrant image to build these examples in case you don't
want to bother installing all of the necessary Java packages on your
Mac directly.

To install Vagrant, you'll need to first install cask:

```
brew install cask
brew cask install vagrant
```

Then run `vagrant up` followed by `vagrant ssh` in order to ssh into
the virtual machine.

Once inside the virtual machine, you'll need to `cd /vagrant` in order
to see the project source tree.

To exit from the virtual machine ssh session you can either use the
command `exit` or simply press Ctrl-D.

When you're done, you may want to run `vagrant halt` to stop the
virtual machine. Finally, to free up the disk space used by the
virtual machine, you may want to run `vagrant destroy` to clean
everything up.
