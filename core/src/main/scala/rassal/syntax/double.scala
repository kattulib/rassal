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

package rassal
package syntax

import scala.annotation.targetName
import rassal.functions.{AsList, Boundable, Invertible}

private[syntax] trait DoubleSyntax {
  extension [P <: BoundP](self: Gen[Double, P]) {
    @targetName("doubleInvert")
    def invert(using f: Invertible[Double]): Gen[Double, P] = {
      f.invert(self)
    }
  }

  extension [P <: BoundP](self: Gen[List[Double], P]) {
    @targetName("doubleListInvert")
    def invert(using f: Invertible[List[Double]]): Gen[List[Double], P] = {
      f.invert(self)
    }
  }

  extension (self: Gen[Double, Unbounded]) {
    @targetName("doubleWithBounds")
    def withBounds(min: Double, max: Double)(using f: Boundable[Double]): Gen[Double, Bounded] = {
      f.withBounds(min, max)(self)
    }
  }

  extension [P <: BoundP](self: Gen[Double, P]) {
    @targetName("doubleToFixed")
    def toFixed(decimalPlaces: Int): Gen[Double, P] = self.map[Double, P] {
      BigDecimal(_)
        .setScale(
          decimalPlaces,
          BigDecimal.RoundingMode.FLOOR
        )
        .toDouble
    }
  }

  extension [P <: BoundP](self: Gen[Double, P]) {
    @targetName("doubleAsList")
    def asList(length: Int)(using f: AsList[Double]): Gen[List[Double], P] = {
      f.asList(self)(length)
    }
  }
}
