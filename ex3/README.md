# Example 3

I recommend using the Vagrant image from the parent directory which
will give you a Linux-based virtual machine with maven
pre-installed. I've tested that works for these examples.

However, you can try to install maven directly on your Mac and run
things locally, but I haven't verified any other installation
instructions:

```
brew install maven
```

This example sets up a full project using Maven. You can take a look
at the [`pom.xml`](pom.xml) to see what a fairly basic project setup
is going to look like. Free free to copy & modify that to get your
initial projects started.

Note that this directory structure is typical of most Java projects,
and it follows the Maven conventions. You technically *can* deviate
from them, but you probably shouldn't unless you know what you're
doing.

Start out reading the code from the
[`Animal.java`](src/main/java/org/adadevelopersacademy/Animal.java) file.
