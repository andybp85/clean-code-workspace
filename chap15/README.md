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