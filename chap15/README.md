# [Chapter 15](https://github.com/andybp85/clean-code-workspace/commit/bd66e8254228947561dd43a99cf043576f7ceb82)

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

### [The JUnit Framework](https://github.com/andybp85/clean-code-workspace/commit/e95436acbd1fd60d28e80d64ea7e854bf6518f7d)

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

#### [eliminate the f prefixes](https://github.com/andybp85/clean-code-workspace/commit/1ef69a905760fc1f9c995f19573c3cd21724402f)
I used IntelliJ's refactoring tool to change the names in the declarations, and low and behold, it inserted `this`
pointers where they were needed. As a web dev, I have to say that so far the lack of `this`s everywhere has been novel.

#### [unencapsulated conditional](https://github.com/andybp85/clean-code-workspace/commit/26556c4ec4211755416ff66456d37ee832ab1035)
I don't think I've every used the `extract method` tool in WebStorm, so this was the perfect opportunity to give it a
whirl. It made the exact same method Bob did. Awesome!

#### [refactor compact()](https://github.com/andybp85/clean-code-workspace/commit/1116e32f340ae3134f69887c254f0eb5af827973)
I should have had the tests running on changes, but I'll forgot and this was the first time I felt it necessary - good
thing, because I forgot to change the `||`s to `&&`s the first time.

I really appreciate this refactor. The result of inverting the conditional is something I strive to do in my own code
day-to-day.

#### [change compact() to formatCompactedComparison()](https://github.com/andybp85/clean-code-workspace/commit/c62330d023d850038b9f0a0f15d7f8f8834e1d35)

#### [change findCommonPrefix() and findCommonSuffix() to use consistent conventions](https://github.com/andybp85/clean-code-workspace/commit/090c5d50b1eacb87a2aea4996fe1f2beec20c6d4)
This is another one I really like, because it gives the reader a better idea of what the function is doing without
having to read the definition.

#### [fix hidden temporal coupling in findCommonSuffix()](https://github.com/andybp85/clean-code-workspace/commit/765da69a15c63f02caf95c473d5752d4fdd52999)
We're about to undo this, but I'm including it for completeness.

#### [better temporal coupling fix with findCommonPrefixAndSuffix()](https://github.com/andybp85/clean-code-workspace/commit/814b58f626389b2397aa05eff3dcf4be7517985f)
And there go the return values I liked. Of course it makes sense though.

#### [refactor findCommonPrefixAndSuffix()](https://github.com/andybp85/clean-code-workspace/commit/be7337bc59b6e3dce0340f596a7f4abe722662b7)
I did this without copy and pasting, and it took me a few tries to get the tests passing.

#### [change suffixIndex to suffixLength](https://github.com/andybp85/clean-code-workspace/commit/36bfaa48190bde45026eb84d7854653c98f8b1f3)
Fairly straightforward, although I left a `+ 1` in the first time I ran my tests. IntelliJ also helped me out, as I had
left in `this.suffixLength = suffixLength;` in `findCommonPrefixAndSuffix()`, and it helpfully informed me that the
assignment is now redundant. I always read what the IDE is suggesting, and I've discovered many cool refactorings by
reading WebStorm's helpful messages.

#### [refactor compactString()](https://github.com/andybp85/clean-code-workspace/commit/ece5637568848c51ef3c65237524c3c914b2e75c)
At first I commented out the `if` statements and their bodies, which failed the test - whoops. Bob means just comment
the `if`s themselves. The refactor itself is slick!

### [Final Refactor](https://github.com/andybp85/clean-code-workspace/commit/1f8a9c04d9ebcbedff28c1d0aae28ea7a71d8d44)
This is obviously pretty huge, and rather than try it myself at first I used one of my favorite tools,
[quickdiff.com](https://www.quickdiff.com/), to figure it out. I then decided to go back and try from the couple things
Bob points out at the end, so I copied what I had into Atom and checked out `ComparisonCompactor.java`.

The first one was really cool because after I inlined the methods into `formatCompactedComparison`, IntelliJ offered to
make `compactExpected` and `compactActual` local variables in it. I went for it, and then it was only a small refactor 
to make it have only one return statement.

I don't think I would have thought of inverting the sense of `shouldNotBeCompacted()` like that, but it is way clearer
and only has the one negation. I'll have to keep that in mind. After these two, my tests passed.

I then used another great IntelliJ tool, `Compare with Clipboard`, to compare my current file with what I had in Atom.
First thing I noticed was the heavy refactor and move of `compactString()` to `compact()` with a few helper methods.
I used IntelliJ's refactor to change the name, moved it, and then just copy and pasted the body and new methods (first
time I've ever seen `StringBuilder()`). This resulted in `computeCommonPrefix()` and `computeCommonSuffix()` not being
used. I see how he took the logic in those two functions and broke it out into the new functions. And now, my tests
are failing...

I continued using `Compare with Clipboard` and saw that Bob had inlined the `expected.equals(actual)` from
`areStringsEqual()` into `shouldNotBeCompacted()`, and suddenly the IntelliJ lit the line up. Apparently this condition
will always result in `false` - oh, I forgot to switch the `&&`s to `||`s! That got my tests passing. There's
definitely a lesson here in that simpler code is easier for the IDE to check, and letting your tools help you as much
as possible is one of the best pieces of programming advice I can think of.

After that, I removed the redundant argument in `suffixOverlapsPrefix()`, and with only a couple formatting differences,
my code matched Uncle Bob's.