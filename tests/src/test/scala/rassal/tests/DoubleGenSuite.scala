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

  test("random double value as fixed to 4 decimal with Seed(1)") {
    val expected = 1.0e-4
    val (_, obtained) = Gen.nextDouble.toFixed(4).run(Seed(1))
    assertEquals(obtained, expected)
  }
}
