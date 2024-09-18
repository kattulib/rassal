# Rassal
<p>
    <img src="/docs/icon.jpeg" width="256" height="256" />
</p>

Rassal is a random value generator.

## Getting Rassal
Right now, Rassal is not a published project. If you want to use the library,
you should clone the project first and publish it locally as on the following
command:

```bash
# on interactive sbt shell
sbt:rassal> publishLocal
...
[info]  published ivy to /home/USER/.ivy2/local/io.github.kattulib/rassal_3/0.1.0-SNAPSHOT
[info]  published ivy to /home/USER/.ivy2/local/io.github.kattulib/rassal-core_3/0.1.0-SNAPSHOT

# on bash
$ sbt publishLocal
...
[info]  published ivy to /home/USER/.ivy2/local/io.github.kattulib/rassal_3/0.1.0-SNAPSHOT
[info]  published ivy to /home/USER/.ivy2/local/io.github.kattulib/rasasl-core_3/0.1.0-SNAPSHOT
```

Then you can add the library in the library dependencies as others.
```scala
libraryDependencies += "io.github.kattulib" %% "rassal" % "0.1.0-SNAPSHOT"
```

## Run Tests
You can run all tests as on the following command:
```bash
# on interactive sbt shell
sbt:rassal> tests/test

# on bash
$ sbt tests/test
```

## Quick Start
```scala
import rassal.Gen
import rassal.syntax.all.*
import rassal.instances.all.given

scala> Gen.nextInt.run(Gen.seed(1))
val res0: (rassal.Seed, Int) = (Seed(25214903928),384748)

scala> Gen.nextInt.withBounds(5, 25).run(Gen.seed(1))
val res1: (rassal.Seed, Int) = (Seed(25214903928),12)

scala> Gen.nextInt.withBounds(5, 25).asList(3).run(Gen.seed(1))
val res2: (rassal.Seed, List[Int]) = (Seed(245470556921330),List(14, 6, 12))

scala> Gen.nextDouble.withBounds(1.5, 4.2).toFixed(2).run(Gen.seed(1))
val res3: (rassal.Seed, Double) = (Seed(25214903928),1.5)

scala> Gen.nextDouble.withBounds(1.5, 4.2).toFixed(2).asList(3).run(Gen.seed(1))
val res4: (rassal.Seed, List[Double]) = (Seed(245470556921330),List(2.19, 2.94, 1.5))
```
