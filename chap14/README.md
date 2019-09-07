# Chapter 14

First shot at this! I'll have to remember how to run Java programs and get everything set up, so that might take me
longer than the exercises.

First false start: tried to have IntelliJ make the Args project in the `chap14` folder. Better idea to make a new project
folder in `chap14/Args`. IntelliJ knows what's up now.

### Args: The Rough Draft
Getting it running was pretty painless! Bob's example matches up with his final version, so I had to modify it a bit to
get it running with the rough draft:

```java
class Driver {
    public static void main(String[] args) {
        try {
            Args arg = new Args("l,p#,d*", args);
            boolean logging = arg.getBoolean('l');
            int port = arg.getInt('p');
            String directory = arg.getString('d');
            executeApplication(logging, port, directory);
        } catch (Exception e) {
            System.out.printf("Argument error: %s\n%s\n", e.getClass(), e.getMessage());
        }
    }

    private static void executeApplication(boolean logging, int port, String directory){
        System.out.printf("Args: %b %d %s\n", logging, port, directory);
    }
}
```

`ArgsException` is private and `errorMessage()` is on `Args` at the moment, so I'm using a `Exception` and
`getMessage()`. After I got it typed it ran just by right clicking on the driver tab and hitting `Run 'Driver.main()'`,
and then I only had to look up `String.printf`'s format character for an integer for my`executeApplication` implementation.

I then tried with the arguments `-l true -p 10 -d "hello"`, and got this glorious error:

```
Exception in thread "main" java.lang.StackOverflowError
    at java.lang.String.startsWith(String.java:1434)
    at com.cleancode.args.Args.parseArgument(Args.java:108)
    at com.cleancode.args.Args.parseArgument(Args.java:109)
    at com.cleancode.args.Args.parseArgument(Args.java:109)
    at com.cleancode.args.Args.parseArgument(Args.java:109)
    [...]
```

Turns out I had one typo:

```java
private void parseArgument(String arg) throws ArgsException {
    if (arg.startsWith("-"))
        parseArgument(arg); // whoops!
}
```
The call in the if should have been to `parseElements`. IntelliJ had helpfully pointed out that the method with the typo
couldn't throw, and greyed out `ArgsException`. I tried to find the inspection to crank this up to an error 
notification, sadly to no avail. Seems a good one. I wonder if Java has a strict mode ala Typescript? 