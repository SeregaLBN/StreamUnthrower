# StreamUnthrower

Lets you use checked exception in lambda expressions.
One import and use!
```java
import utils.stream.Unthrow;
...
    static class BlaBla {
        void method() throws IOException { ... }
        boolean isSomething() throws IOException { ... }
    }

    public void blablaDemo() {
        // :)
        getBlaBlaList().stream()
                .filter( blaBla -> Unthrow.wrap(() -> blaBla.isSomething()) )
                .forEach(blaBla -> Unthrow.wrapProc(() -> blaBla.method()) );
        // :(
        getBlaBlaList().stream()
                .filter( blaBla -> { try { return blaBla.isSomething(); } catch (Exception ex) { throw new RuntimeException(ex); } } )
                .forEach(blaBla -> { try { blaBla.method(); } catch (Exception ex) { throw new RuntimeException(ex); } } );
    }
...
```
Full [Demo][untrowMinDemo] example of use.

Comparison examples of usage:
  - [for lambda expressions][untrowGoodWay]
  - [for local functions][untrowGoodWay2]

Features:
-
 - it does not create new exceptions - re use existing
 - a minimum code
 - no substitution Stream, only the wrapper over the lambda

License
----

MIT


**Free Software, Hell Yeah!**

[//]: #
   [untrowMinDemo]: <https://github.com/SeregaLBN/StreamUnthrower/src/test/java/demo/Demo.java>
   [untrowGoodWay]: <https://github.com/SeregaLBN/StreamUnthrower/src/test/java/demo/GoodWay.java>
   [untrowGoodWay2]: <https://github.com/SeregaLBN/StreamUnthrower/src/test/java/demo/GoodWay2.java>
