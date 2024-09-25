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

package rassal
package syntax

import scala.annotation.targetName
import functions.{AsList, Boundable}

private[syntax] trait StringSyntax {
  extension (self: Gen[String]) {
    @targetName("stringWithBounds")
    def withBounds(min: String, max: String)(using f: Boundable[String]): Gen[String] = {
      f.withBounds(min, max)(self)
    }
  }

  extension (self: Gen[String])(using Boundable[String]) {
    @targetName("stringAscii")
    def ascii: Gen[String] = {
      self.withBounds("32", "127")
    }
  }

  extension (self: Gen[String])(using Boundable[String], Boundable[Int]) {
    @targetName("stringAlpha")
    def alpha: Gen[String] = {
      val upperChars = ('A' to 'Z').mkString
      val lowerChars = ('a' to 'z').mkString
      self.contains(upperChars + lowerChars)
    }
  }

  extension (self: Gen[String])(using Boundable[String], Boundable[Int]) {
    @targetName("stringAlphaNumeric")
    def alphanumeric: Gen[String] = {
      val upperChars = ('A' to 'Z').mkString
      val lowerChars = ('a' to 'z').mkString
      val numericChars = ('0' to '9').mkString
      self.contains(upperChars + lowerChars + numericChars)
    }
  }

  extension (self: Gen[String]) {
    @targetName("stringToUpper")
    def toUpper: Gen[String] = {
      self.map { _.toUpperCase }
    }
  }

  extension (self: Gen[String]) {
    @targetName("stringToLower")
    def toLower: Gen[String] = {
      self.map { _.toLowerCase }
    }
  }

  extension (self: Gen[String])(using f: Boundable[Int]) {
    @targetName("stringContains")
    def contains(containsList: String): Gen[String] = {
      self.flatMap { str =>
        str.foldRight(Gen.lift("")) { case (ch, acc) =>
          acc.flatMap(a => {
            f.withBounds(0, containsList.length - 1)(Gen.lift(ch))
              .map {
                containsList(_).toString + a
              }
          })
        }
      }
    }
  }

  extension (self: Gen[String]) {
    @targetName("stringExclude")
    def exclude(excludeList: String): Gen[String] = {
      self.flatMap { ch =>
        if excludeList.contains(ch) then self.exclude(excludeList)
        else Gen.lift(ch)
      }
    }
  }

  extension (self: Gen[String])(using f: AsList[String]) {
    @targetName("stringLength")
    def length(value: Int): Gen[String] = {
      f.asList(self)(value).map { _.mkString }
    }
  }

  extension (self: Gen[String]) {
    @targetName("stringAsLike")
    def asLike(template: String, map: Map[String, Gen[String]]): Gen[String] = Gen { currentSeed =>
      map.foldLeft((currentSeed, template)) { case ((runningSeed, acc), (k, gen)) =>
        val (nextSeed, generatedValue) = gen.run(runningSeed)
        val nextTemplate = acc.replace(k, generatedValue)
        (nextSeed, nextTemplate)
      }
    }
  }

  extension (self: Gen[String])(using AsList[String])(using f: Boundable[Int]) {
    @targetName("stringRangeLength")
    def rangeLength(min: Int, max: Int): Gen[String] = {
      f.withBounds(min, max)(Gen.lift(min)).flatMap(self.length)
    }
  }
}
