# Chapter 14

First shot at this! I'll have to remember how to run Java programs and get everything set up, so that might take me
longer than the exercises.

First false start: tried to have IntelliJ make the Args project in the `chap14` folder. Better idea to make a new project
folder in `chap14/Args`. IntelliJ knows what's up now.

### [Args: The Rough Draft](https://github.com/andybp85/clean-code-workspace/commit/1ca2aa19358bef92f36fb6ce8741ba7ffcd589f3)
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

### [ArgumentMarshaller](https://github.com/andybp85/clean-code-workspace/commit/5f1563f15d72b83c968733988deb5a1c739f2eba)

I can't seem to find a code repo for this book, so I don't have the JUint tests he mentions. I'm also not sure how to
look for the FitNesse tests, although I did download and run [FitNesse](http://fitnesse.org/) itself, which looks
really cool.

### [String Arguments](https://github.com/andybp85/clean-code-workspace/commit/f15930121fe9bce0288e8e2f7d4a9f9aef72357a)

This was simple and I was able figure out most of it without referring back to the test. I did screw it up the first
time by copying and pasting the implementation of `getString` from `getBoolean` without changing `booleanArgs` to
`stringArgs`, but I figured it out by following the execution by eye. Let's see how long until I have to figure out
the debugger...

### [Integer Arguments](https://github.com/andybp85/clean-code-workspace/commit/c64cbb52bb6d467169b4fd475436f97ca39e3119)

I tried this on my own and got most of it, besides checking if there should be a default return value for
`getInteger`.

### [Abstract ArgumentMarshaller](https://github.com/andybp85/clean-code-workspace/commit/993cdfa9b6f25fbf5ad15c658a31d1cf81c346f2)

I'm not running any tests so I can't see exactly what Bob's seeing, but I couldn't even run the program until all the
`ArgumentMarshaller`s had implemented the now-abstract methods, because the concrete marshallers no longer extended
the abstract base properly. So, I'm not sure if tests would even run until this whole section is completed.

### [Use ArgumentMarshallers](https://github.com/andybp85/clean-code-workspace/commit/c330a5e7cb1c05794e9df4387140ce8bcdfd927f)

I made sure the driver still runs. We'll be getting rid of the `isxxxArg` functions soon, however I decided to put each
step into its own commit to show exactly what's going on. Obviously I'll be making sure the drive still runs for each
commit.

### [Eliminate duplicate calls to marshallers.get](https://github.com/andybp85/clean-code-workspace/commit/d01237107b899456c7229f99b4afd5493a66beff)

### [Inline isxxxArg methods](https://github.com/andybp85/clean-code-workspace/commit/e9382c76a4ced3f9d6bbd7256fa48a80e166d9cd)

### [Use marshallers map in set functions](https://github.com/andybp85/clean-code-workspace/commit/e859f5cc6943897b9c2b6edb72a2e1e73ea7b8a5)

This actually threw me a bit, because Bob didn't mention that the program won't run until we finish moving everything.
The upshot is, I leaned how to use the debugger, and how print a stack trace from a Java exception (which I added to
the driver). I also learned that Java will throw a null pointer exception if a method with a return type tries to
return null, as the `getInt` method was before everything was in place.

### [Let's look at the whole picture again](https://github.com/andybp85/clean-code-workspace/commit/7a5bcfe7e12086fd4a39706cb36fd49238340dc3)

No code with this one, just my thoughts so far. One thing I've been thinking is how close the functions-do-one-thing
principle is to the much-vaunted (by me) functional programming principle that a function body can only be one
expression. (Of course, the thing I like about FP langs is that it's the only way possible, and in langs like Java and
JS  there's more than enough rope to hang yourself, the next programmer who has to work on it, the whole project, etc.)
Clean code is clean code.

One thing I'll admit is that (and remember I typed this whole thing from the book, no copy-paste) I get in principle
how the schema is parsed, but I couldn't give you the exact execution off the top of my head - in fact, when I was
running the debugger, I was actually getting lost in those parts of the code. It's a lot of string splitting and for
loops, and my brain just goes, "Okay, we know what the input is and if we look at the next function we'll know what
comes out, so this does that and if we need to figure out how later we'll just come back". This, I would actually say,
is one of the goals of clean code in a real project environment. However, I do feel as though the parts of the app that
are hazy made debugging harder. This also isn't helped by everything being in one big class still, but hopefully we'll
fix that soon.

I would also like to write some tests for this, because I think I can see pretty well what to check. But, since Bob
doesn't give us the tests (or say we should write them), I'm going to hold off in the interests of keeping this repo
as close to the book as possible.

### [Refactor setArgument to single call to ArgumentMarshaler.set](https://github.com/andybp85/clean-code-workspace/commit/c08733eebcec5c4122403ecdbc781c6564c10551)

Let's see about these 10 steps:

1. Change `args` and `currentArg` to `argsList` and an `Iterator<String>`, and fix up dependant code.
2. Refactor `setArgument`.
3. Refactor `setBooleanArg`.
4. Add net `set` in `ArgumentMarshaller`, update marshallers with empty method, implement in
`BooleanArgumentMarshaller`. Verify code runs.
5. Move functionality from the other `setxxxArg` functions into marshaller methods and change `setArgument` call set
methods.
6. Remove type-case if/else from `setArgument`.
7. Change `ArgumentMarshaller` to interface.

That's all Bob gives us. Then I had to:

8. Change `extends` to `implements` in relevant cases.
9. Remove unused `setxxxArg` functions.
10. Remove unused `set` methods.

I'm not sure if these were the steps Bob had in mind, but hey, I had to do them.

### [Add Double argument type](https://github.com/andybp85/clean-code-workspace/commit/524d7dac418314c68d9cb858d726653d141850da)

Finally, JUnit! I used the instructions on [IntelliJ's Blog](https://www.jetbrains.com/help/idea/configuring-testing-libraries.html#create_test)
to get set up, which was super painless. I let it create `ArgsTest` in the `Args` package root - I don't know how it's
usually set up with Java, and for my purposes as long as it runs I'm happy. I didn't let it create any method tests, so
I wound up with an empty test class:

```java
package com.cleancode.args;

import static org.junit.jupiter.api.Assertions.*;

class ArgsTest {

}
```

I typed out the test method just like Bob has it, and made a quick stub of the method being tested in `Args`:

```java
public double getDouble(char arg) {
    return 0.0;
}
```

I then created a simple JUnit run configuration for `All tests in Package` and selected `com.cleancode.args` as the
package. Hitting `Run` found no tests, so I took a guess that JUnit 5 needed an `@Test` annotation on the method (I
noticed somewhere that annotations were a big feature), and it found the test and ran it, resulting in the exact error
I expected:

```
java.text.ParseException: Argument: x has invalid format: ##.
    at com.cleancode.args.Args.parseSchemaElement(Args.java:61)
    at com.cleancode.args.Args.parseSchema(Args.java:43)
    at com.cleancode.args.Args.parse(Args.java:31)
    [...]
```

I then typed out the code for a `double` argument and the test passed first try. I also looked up how to get the test
to run on changes, and turns out there's a `Toggle auto-test` button in IntelliJ's test runner (as opposed to having a
`--watch` arg, like Jest). One thing that Bob doesn't mention specifically is that we've now inlined the
`isxxxSchemaElement` methods, so I deleted them.

### [ArgsException module](https://github.com/andybp85/clean-code-workspace/commit/96f699f88552d5655a9b8ba948b829b234f42354)

Finally moving stuff out of the one giant class! This also let me use `ArgsException` in my driver.

### Merge exceptions into ArgsExceptions

And finally, a bunch of tests! I originally started typing it straight away, but then I realized that the spirit of the
exercise is to keep the tests passing. So I commented out everything but the first failing test
(`testCreateWithNoSchemaButWithOneArgument`) and then modified `Args` and `ArgsException` until it passed, then did the
next test, and so on. Lots of flipping around, but it does feel like I understand what's going on far more than when I
just typed everything out.

I moved the `ArgumentMarshaller` classes out of `Args` using IntelliJ's "Refactor -> Move inner class to upper level".
I don't use Webstorm's refactoring tools nealy enough. I did do this pretty early in the process though, arguably
earlier than was justified by the tests, but it sure helped readability!

I'm going to break this up into several commits so my process is visible. I'll name the commits after the last test
implemented:
1. [testCreateWithNoSchemaButWithOneArgument](https://github.com/andybp85/clean-code-workspace/commit/3d20b68f5654517134ad6d0d4170490532cb0371)
2. [testNonLetterSchema](https://github.com/andybp85/clean-code-workspace/commit/64db7ddf5c569c653365b9f43e540223866da358)
3. [testInvalidArgumentFormat](https://github.com/andybp85/clean-code-workspace/commit/bbab75c86938c430bc310a9bd174c0f97c7751f5)
4. [testInvalidInteger](https://github.com/andybp85/clean-code-workspace/commit/08dfab7a9c77be701cd441f857c31170f200640c)
5. [testMissingDouble](https://github.com/andybp85/clean-code-workspace/commit/9b01726cfd67f5a2f34c73169e0a405c40ab273e)

### [ArgsExceptionTest](https://github.com/andybp85/clean-code-workspace/commit/2304d2ae8fadf1c0b767f7a594933b229b96c17c)

Finishing up the tests was pretty painless. I had to add the error messages for double, but this was obvious.

### [Final Refactor](https://github.com/andybp85/clean-code-workspace/commit/789baaad7fec888db706832d379546a378c15af3)

So I have to admit: Bob says to try the final refactor as an exercise, which I tried and gave up on pretty quickly. I
did continuously run the tests while I was doing the refactor, and did figure out a few things myself, but mostly I
looked at the final version and made the changes in sequence. I do see what Bob had in mind though.

I did hit one snag that took me a while: I couldn't get the driver to run! I finally realized that I had set the command
line args up wrong initially: it should have been `-l -p 10 -d "hello"`. the extra `true` in it was getting parsed as
arg, which caused the loop in `parseArgumentStrings` to break and resulted in a null pointer exception being thrown
from the call to `getInt` (since there was no int).

### [StringArrayArgumentMarshaller](https://github.com/andybp85/clean-code-workspace/commit/2c0e914cb89dcc531453105797b1f7576f732c8f)

This isn't in the prose, however there's some code in the `Args` class at the beginning of the chapter, so I decided to
go ahead and implement it.

First I got `ArgsException` set up. I updated the test cases, which of course wouldn't even run at first because the
class wouldn't compile. Implementing the messages was easy. Then I moved onto the `Args` class. This entailed writing
the `StringArrayArgumentMarshaller` class, for which the code isn't given. Here's what I came up with:

```java
class StringArrayArgumentMarshaller implements ArgumentMarshaller {
    private String[] stringArrayValue;

    public void set(Iterator<String> currentArgument) throws ArgsException {
        try {
            stringArrayValue = currentArgument.next();
        } catch (NoSuchElementException e) {
            throw new ArgsException(MISSING_STRING_ARRAY, stringArrayValue.toString());
        }
    }

    public static String[] geValue(ArgumentMarshaller am) {
        if (am != null && am instanceof StringArgumentMarshaller) {
            return ((StringArrayArgumentMarshaller) am).stringArrayValue;
        } else {
            return new String[]{};
        }
    }
}
```

This didn't work, because `currentArgument.next()` produces a string. Looking around for a way to get a string as an
array (ha!), I found something even better: Uncle Bob's [actual implementation](https://github.com/unclebob/javaargs/blob/master/src/com/cleancoder/args/StringArrayArgumentMarshaler.java)
of `StringArrayArgumentMarshaler`! Since my goal here isn't to really learn Java I decided to just use what he made,
with some modifications.

```java
class StringArrayArgumentMarshaller implements ArgumentMarshaller {
    private List<String> strings = new ArrayList<String>();

    public void set(Iterator<String> currentArgument) throws ArgsException {
        try {
            strings.add(currentArgument.next());
        } catch (NoSuchElementException e) {
            throw new ArgsException(MISSING_STRING_ARRAY, strings.toString());
        }
    }

    public static String[] getValue(ArgumentMarshaller am) {
        if (am != null && am instanceof StringArrayArgumentMarshaller)
            return ((StringArrayArgumentMarshaller) am).strings.toArray(new String[0]);
        else
            return new String[0];
    }
}
```

(I'm pretty sure `strings.toString()` isn't the best debugging output for this, but `Arrays.toString` didn't work and
this is just an exercise.)

Almost! I had made tests for `testMissingStringArray` and `testInvalidStringArray`, but I couldn't get
`testInvalidStringArray` to pass. so I decided to update my driver to test the functionality:

```java
class Driver {
    public static void main(String[] args) {
        try {
            Args arg = new Args("l,p#,d*,a[*]", args);
            boolean logging = arg.getBoolean('l');
            int port = arg.getInt('p');
            String[] array = arg.getStringArray('a');
            String directory = arg.getString('d');
            executeApplication(logging, port, directory, array);
        } catch (ArgsException e) {
            System.out.printf("Argument error: %s\n%s\n", e.getClass(), e.getMessage());
            e.printStackTrace();
        }
    }

    private static void executeApplication(boolean logging, int port, String directory, String[] array){
        System.out.printf("Args: %b %d %s %s\n", logging, port, directory, Arrays.toString(array));
    }
}
```

Running it with the args `-l -p 10 -d "hello" -a "Forty","Two"` worked, and I got back
`Args: true 10 hello [Forty,Two]`. Woot. I then tried it with `-a "Forty"`, and got back `[Forty]`. So I could infer
that any string argument would work fine, and removed the test. All my tests passed!
