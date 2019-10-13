# Chapter 15

Setup was pretty easy (as long as it proves to be correct)! I made my `chap15` folder, then let IntelliJ make a new
project called `ComparisonCompactor`, then had IntelliJ make a module in it called `com.cleancode.comparisonCompactor`.
I then had IntelliJ create a class in the module called `ComparisonCompactor`, and then right-clicked on the class name
and had IntelliJ create a test, which defaulted to `ComparisonCompactorTest`. So far, so good.

Instead of typing out both classes, I took the shortcut of copying and pasting the code right from the Kindle book.
This worked surprisingly well, and after replacing the curly quotes and some minor import adjustments, I right clicked
on `ComparisonCompactorTest`, hit `run`, and got a bunch of green checkboxes. Not gonna lie, I was pretty surprised!

There's one caveat that I don't think will be a problem for me, although could be if you're reading this down the road:
I let IntelliJ figure out the import for `Assert` in `ComparisonCompactor`, which got it from `junit.framework.Assert`,
which is deprecated. I looked around quickly for what the current version would be, but I didn't find it on the first
page of a google search. As I've said my goal isn't to learn Java, so since everything runs I'm going to roll with it
for now. YMMV.

### The JUnit Framework

Bob invites us to critique the tests first. IntelliJ only suggests one change: the `assertTrue` in the first test can
be simplified to `assertEquals()`. The rest of the tests utilize `assertEquals()` and the simplified test passes, so
that's pretty obvious.

That brings up another observation: all the tests rely on `compact()` to return a string, so if you wanted to change
the implementation to return say an object, you'd have to change each individual test. I think I'd abstract out the
assertion so you could pass in the result and the expected, and then if the implementation changes you'd have to make a
lot fewer changes.

This test makes no sense to me:

```java
public void testSame() {
    String failure = new ComparisonCompactor(1, "ab", "ab").compact(null);
    assertEquals("expected:<ab> but was:<ab>", failure);
}
```

In all the others, it's obvious that the `expected` doesn't match the `actual`, but these are the same, so I'm not sure
why it's not what's expected. I thought maybe `contextLength` had something to do with it, but varying it between 0 and
3 didn't fail the test. To make sure the tests are actually running, I changed some of the test strings and they failed
the tests as expected, so it looks like I'm not getting false results. I'm sure reading the code for
`ComparisonCompactor` will clear that up, but for the moment it's confusing.

The code for `ComparisonCompactor`, as Bob says, is pretty good. I was able to follow it easily and can see why that
test is: `areStringsEqual()`. I don't know how this class is used in JUint and this seems pretty counter-intuitive, but
for now I'll take it on faith that this is correct.

The same of course cannot be said for Bob's defactored version, which I'm including in `ComparisonCompactorDefactored`.
Yes, I'm very happy I don't have to clean that up!

### The Boy Scout Rule

Bob makes a bunch of small changes here, so I'll break this up into commits that make sense.

#### eliminate the f prefixes
I used IntelliJ's refactoring tool to change the names in the declarations, and low and behold, it inserted `this`
pointers where they were needed. As a web dev, I have to say that so far the lack of `this`s everywhere has been novel.

#### unencapsulated conditional
I don't think I've every used the `extract method` tool in WebStorm, so this was the perfect opportunity to give it a
whirl. It made the exact same method Bob did. Awesome!