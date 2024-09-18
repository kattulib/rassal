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

private[instances] class IntInstances {
  given Boundable[Int] with {
    def withBounds(min: Int, max: Int)(self: Gen[Int, Unbounded]): Gen[Int, Bounded] = {
      self.map[Int, Bounded] { Math.floorMod(_, max - min + 1) + min }
    }
  }

  given Invertible[Int] with {
    def invert[P <: BoundP](self: Gen[Int, P]): Gen[Int, P] = {
      self.map { -_ }
    }
  }

  given intListInvertible: Invertible[List[Int]] with {
    def invert[P <: BoundP](self: Gen[List[Int], P]): Gen[List[Int], P] = {
      self.map { as =>
        as.foldLeft(List.empty[Int])(_ :+ -_)
      }
    }
  }

  given AsList[Int] with {
    def asList[P <: BoundP](self: Gen[Int, P])(length: Int): Gen[List[Int], P] = Gen { currentSeed =>
      (0 until length).foldLeft((currentSeed, List.empty[Int])) { case ((runningSeed, acc), _) =>
        val (nextSeed, v) = self.run(runningSeed)
        (nextSeed, v +: acc)
      }
    }
  }
}
