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

class StringGenSuite extends munit.FunSuite {
  test("random string as ascii value with Seed(1)") {
    val expected = "d"
    val (_, obtained) = Gen.nextString.ascii.run(Seed(1))
    assertEquals(obtained, expected)
  }

  test("random string as ascii value with length with Seed(1)") {
    val expected = "*gPd"
    val (_, obtained) = Gen.nextString.ascii.length(4).run(Seed(1))
    assertEquals(obtained, expected)
  }

  test("random string as alphabetic value with length with Seed(1)") {
    val expected = "hVJY"
    val (_, obtained) = Gen.nextString.alpha.length(4).run(Seed(1))
    assertEquals(obtained, expected)
  }

  test("random string as alphanumeric and uppered value with length with Seed(1)") {
    val expected = "FNJC"
    val (_, obtained) = Gen.nextString.alphanumeric.toUpper.length(4).run(Seed(1))
    assertEquals(obtained, expected)
  }

  test("random string contains [XYZ] as lowered with length with Seed(1)") {
    val expected = "yyxz"
    val (_, obtained) = Gen.nextString.contains("XYZ").toLower.length(4).run(Seed(1))
    assertEquals(obtained, expected)
  }

  test("random string contains [ABCDE] and excludes [AB] with length with Seed(1)") {
    val expected = "EEDD"
    val (_, obtained) = Gen.nextString.contains("ABCDE").exclude("AB").length(4).run(Seed(1))
    assertEquals(obtained, expected)
  }

  test("random string with asLike, and Seed(1)") {
    val expected = "hifnjc@spmo.kfn"
    val (_, obtained) = Gen.nextString
      .asLike(
        "$userName@$domain.$tld",
        Map(
          "$userName" -> Gen.nextString.alphanumeric.toLower.length(6),
          "$domain" -> Gen.nextString.alpha.toLower.length(4),
          "$tld" -> Gen.nextString.alpha.toLower.length(3)
        )
      )
      .run(Gen.seed(1))

    assertEquals(obtained, expected)
  }
}
