/*
 * Copyright (c) 2024 lamdalib
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

class IntGenSuite extends munit.FunSuite {
  test("random int value with Seed(1)") {
    val expected = 384748
    val (_, obtained) = Gen.nextInt.run(Seed(1))
    assertEquals(obtained, expected)
  }

  test("random int value as bounded between [1, 100] with Seed(1)") {
    val expected = 49
    val (_, obtained) = Gen.nextInt.withBounds(1, 100).run(Seed(1))
    assertEquals(obtained, expected)
  }

  test("random int value as inverted with Seed(1)") {
    val expected = -384748
    val (_, obtained) = Gen.nextInt.invert.run(Seed(1))
    assertEquals(obtained, expected)
  }

  test("random int value as inverted twice with Seed(1)") {
    val expected = 384748
    val (_, obtained) = Gen.nextInt.invert.invert.run(Seed(1))
    assertEquals(obtained, expected)
  }

  test("random int value as inverted and bounded that between [1, 100] with Seed(1)") {
    val expected = -49
    val (_, obtained) = Gen.nextInt.withBounds(1, 100).invert.run(Seed(1))
    assertEquals(obtained, expected)
  }

  test("random int values with for-comprehension, and with Seed(1)") {
    val expected = (384748, -1151252339, -549383847)
    val result = for {
      a <- Gen.nextInt
      b <- Gen.nextInt
      c <- Gen.nextInt
    } yield (a, b, c)

    val (_, obtained) = result.run(Seed(1))
    assertEquals(obtained, expected)
  }

  test("random list of int with Seed(1)") {
    val expected = List(-883454042, 1612966641, -549383847, -1151252339, 384748)
    val (_, obtained) = Gen.nextInt.asList(5).run(Seed(1))
    assertEquals(obtained, expected)
  }

  test("random list of int as bounded that between [1, 5] with Seed(1)") {
    val expected = List(4, 2, 4, 2, 4)
    val (_, obtained) = Gen.nextInt.withBounds(1, 5).asList(5).run(Seed(1))
    assertEquals(obtained, expected)
  }

  test("random list of int as inverted and bounded that between [1, 5] with Seed(1)") {
    val expected = List(-4, -2, -4, -2, -4)
    val (_, obtained) = Gen.nextInt.withBounds(1, 5).invert.asList(5).run(Seed(1))
    assertEquals(obtained, expected)
  }

  test(
    "random list of int as inverted which after the creation of the list and bounded that between [1, 5] with Seed(1)"
  ) {
    val expected = List(-4, -2, -4, -2, -4)
    val (_, obtained) = Gen.nextInt.withBounds(1, 5).asList(5).invert.run(Seed(1))
    assertEquals(obtained, expected)
  }

  test("random lazy list of int as bounded that between [1, 10] with Seed(1)") {
    val expected = List(9, 2, 4, 2, 9)
    val (_, lazyList) = Gen.nextInt.withBounds(1, 10).asLazy.run(Seed(1))
    val obtained = lazyList.take(5).toList
    assertEquals(obtained, expected)
  }
}
