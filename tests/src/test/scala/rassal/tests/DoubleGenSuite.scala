/*
 * Copyright (c) 2024 kattulib
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package rassal.tests

import rassal.{Gen, Seed}
import rassal.syntax.all.*
import rassal.instances.all.given

class DoubleGenSuite extends munit.FunSuite {
  test("random double value with Seed(1)") {
    val expected = 1.7916224896907806e-4
    val (_, obtained) = Gen.nextDouble.run(Seed(1))
    assertEquals(obtained, expected)
  }

  test("random double value as inverted with Seed(1)") {
    val expected = -1.7916224896907806e-4
    val (_, obtained) = Gen.nextDouble.invert.run(Seed(1))
    assertEquals(obtained, expected)
  }

  test("random double value as inverted twice with Seed(1)") {
    val expected = 1.7916224896907806e-4
    val (_, obtained) = Gen.nextDouble.invert.invert.run(Seed(1))
    assertEquals(obtained, expected)
  }

  test("random double value as bounded that between [0.332, 0.355] with Seed(1)") {
    val expected = 0.3320041207317263
    val (_, obtained) = Gen.nextDouble.withBounds(0.332, 0.355).run(Seed(1))
    assertEquals(obtained, expected)
  }

  test("random double value as inverted and bounded that between [0.332, 0.355] with Seed(1)") {
    val expected = -0.3320041207317263
    val (_, obtained) = Gen.nextDouble.withBounds(0.332, 0.355).invert.run(Seed(1))
    assertEquals(obtained, expected)
  }

  test("random double values with for-comprehension, and with Seed(1)") {
    val expected = (1.7916224896907806e-4, 0.5360936457291245, 0.2558267889544368)
    val result = for {
      a <- Gen.nextDouble
      b <- Gen.nextDouble
      c <- Gen.nextDouble
    } yield (a, b, c)

    val (_, obtained) = result.run(Seed(1))
    assertEquals(obtained, expected)
  }

  test("random double values as bounded with for-comprehension, and with Seed(1)") {
    val expected = (0.10001791622489692, 0.25360936457291244, 0.3255826788954437)
    val result = for {
      a <- Gen.nextDouble.withBounds(0.1, 0.2)
      b <- Gen.nextDouble.withBounds(0.2, 0.3)
      c <- Gen.nextDouble.withBounds(0.3, 0.4)
    } yield (a, b, c)

    val (_, obtained) = result.run(Seed(1))
    assertEquals(obtained, expected)
  }

  test("random double values as fixed to 4 decimal and bounded with for-comprehension, and with Seed(1)") {
    val expected = (0.1000, 0.2536, 0.3255)
    val result = for {
      a <- Gen.nextDouble.withBounds(0.1, 0.2).toFixed(4)
      b <- Gen.nextDouble.withBounds(0.2, 0.3).toFixed(4)
      c <- Gen.nextDouble.withBounds(0.3, 0.4).toFixed(4)
    } yield (a, b, c)

    val (_, obtained) = result.run(Seed(1))
    assertEquals(obtained, expected)
  }

  test("random double value as fixed to 4 decimal with Seed(1)") {
    val expected = 1.0e-4
    val (_, obtained) = Gen.nextDouble.toFixed(4).run(Seed(1))
    assertEquals(obtained, expected)
  }

  test("random list of double with Seed(1)") {
    val expected =
      List(0.41139034600928426, 0.7510961224325001, 0.2558267889544368, 0.5360936457291245, 1.7916224896907806e-4)
    val (_, obtained) = Gen.nextDouble.asList(5).run(Seed(1))
    assertEquals(obtained, expected)
  }

  test("random list of double as bounded that between [1, 5] with Seed(1)") {
    val expected =
      List(2.645561384037137, 4.0043844897300005, 2.023307155817747, 3.144374582916498, 1.0007166489958763)
    val (_, obtained) = Gen.nextDouble.withBounds(1, 5).asList(5).run(Seed(1))
    assertEquals(obtained, expected)
  }

  test("random list of double as inverted and bounded that between [1, 5] with Seed(1)") {
    val expected =
      List(-2.645561384037137, -4.0043844897300005, -2.023307155817747, -3.144374582916498, -1.0007166489958763)
    val (_, obtained) = Gen.nextDouble.withBounds(1, 5).invert.asList(5).run(Seed(1))
    assertEquals(obtained, expected)
  }

  test("random list of double as inverted, fixed, and bounded that between [1, 5] with Seed(1)") {
    val expected =
      List(-2.64, -4.00, -2.02, -3.14, -1.00)
    val (_, obtained) = Gen.nextDouble.withBounds(1, 5).toFixed(2).invert.asList(5).run(Seed(1))
    assertEquals(obtained, expected)
  }

  test(
    "random list of double as inverted which after the creation of list, fixed, and bounded that between [1, 5] with Seed(1)"
  ) {
    val expected =
      List(-2.64, -4.00, -2.02, -3.14, -1.00)
    val (_, obtained) = Gen.nextDouble.withBounds(1, 5).toFixed(2).asList(5).invert.run(Seed(1))
    assertEquals(obtained, expected)
  }

  test("random lazy list of double as bounded that between [1, 10] with Seed(1)") {
    val expected = List(1.0016124602407217, 5.824842811562121, 3.302441100589931, 7.759865101892501, 4.702513114083558)
    val (_, lazyList) = Gen.nextDouble.withBounds(1, 10).asLazy.run(Seed(1))
    val obtained = lazyList.take(5).toList
    assertEquals(obtained, expected)
  }
}
