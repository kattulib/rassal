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
package instances

import functions.{AsList, Boundable, Invertible}

private[instances] trait DoubleInstances {
  given Boundable[Double] with {
    def withBounds(min: Double, max: Double)(self: Gen[Double, Unbounded]): Gen[Double, Bounded] = {
      self.map[Double, Bounded] { min + (max - min) * _ }
    }
  }

  given Invertible[Double] with {
    def invert[P <: BoundP](self: Gen[Double, P]): Gen[Double, P] = {
      self.map { -_ }
    }
  }

  given doubleListInvertible: Invertible[List[Double]] with {
    def invert[P <: BoundP](self: Gen[List[Double], P]): Gen[List[Double], P] = {
      self.map { as =>
        as.foldLeft(List.empty[Double])(_ :+ -_)
      }
    }
  }

  given AsList[Double] with {
    def asList[P <: BoundP](self: Gen[Double, P])(length: Int): Gen[List[Double], P] = Gen { currentSeed =>
      (0 until length).foldLeft((currentSeed, List.empty[Double])) { case ((runningSeed, acc), _) =>
        val (nextSeed, v) = self.run(runningSeed)
        (nextSeed, v +: acc)
      }
    }
  }
}
