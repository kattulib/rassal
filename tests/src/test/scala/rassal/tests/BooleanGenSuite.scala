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

class BooleanGenSuite extends munit.FunSuite {
  test("random boolean value with Seed(1)") {
    val expected = true
    val (_, obtained) = Gen.nextBoolean.run(Seed(1))
    assertEquals(obtained, expected)
  }

  test("random boolean value as inverted with Seed(1)") {
    val expected = false
    val (_, obtained) = Gen.nextBoolean.invert.run(Seed(1))
    assertEquals(obtained, expected)
  }

  test("random boolean value as inverted twice with Seed(1)") {
    val expected = true
    val (_, obtained) = Gen.nextBoolean.invert.invert.run(Seed(1))
    assertEquals(obtained, expected)
  }

  test("random boolean values with for-comprehension, and with Seed(1)") {
    val expected = (true, false, false)
    val result = for {
      a <- Gen.nextBoolean
      b <- Gen.nextBoolean
      c <- Gen.nextBoolean
    } yield (a, b, c)

    val (_, obtained) = result.run(Seed(1))
    assertEquals(obtained, expected)
  }

  test("random list of boolean with Seed(1)") {
    val expected = List(false, true, false, false, true)
    val (_, obtained) = Gen.nextBoolean.asList(5).run(Seed(1))
    assertEquals(obtained, expected)
  }

  test("random lazy list of boolean with Seed(1)") {
    val expected = List(true, false, false, true, false)
    val (_, lazyList) = Gen.nextBoolean.asLazy.run(Seed(1))
    val obtained = lazyList.take(5).toList
    assertEquals(obtained, expected)
  }
}
