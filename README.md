# StreamUnthrower

Main thing - see class [Unthrow.java][unthrow]

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
Full [Demo][unthrowMinDemo] example of use.

Comparison examples of usage:
  - [for lambda expressions][unthrowGoodWay]
  - [for local functions][unthrowGoodWay2]

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
   [unthrow]: <https://github.com/SeregaLBN/StreamUnthrower/blob/master/src/main/java/utils/stream/Unthrow.java>
   [unthrowMinDemo]: <https://github.com/SeregaLBN/StreamUnthrower/blob/master/src/test/java/demo/Demo.java>
   [unthrowGoodWay]: <https://github.com/SeregaLBN/StreamUnthrower/blob/master/src/test/java/demo/GoodWay.java>
   [unthrowGoodWay2]: <https://github.com/SeregaLBN/StreamUnthrower/blob/master/src/test/java/demo/GoodWay2.java>
