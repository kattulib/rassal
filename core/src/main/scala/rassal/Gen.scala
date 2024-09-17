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

import kuram.data.State

opaque type Gen[+A, P <: BoundP] = State[Seed, A]

object Gen {
  def apply[A, P <: BoundP](instance: Seed => (Seed, A)): Gen[A, P] = State.apply(instance)

  def lift[A, P <: BoundP](a: A): Gen[A, P] = State.lift(a)

  def seed(initialValue: Long): Seed = Seed(initialValue)

  def nextInt: Gen[Int, Unbounded] = Gen { _.next }

  def nextNonInt: Gen[Int, Unbounded] = nextInt.map { i =>
    if i < 0 then -(i + 1) else i
  }

  def nextDouble: Gen[Double, Unbounded] = nextNonInt.map { i =>
    i / (Int.MaxValue.toDouble + 1)
  }

  extension [A, P <: BoundP](self: Gen[A, P]) {
    def map[B, P2 <: BoundP](f: A => B): Gen[B, P2] = State.map(self)(f)
    def flatMap[B, P2 <: BoundP](f: A => Gen[B, P2]): Gen[B, P2] = State.flatMap(self)(f)
    def run(initialState: Seed) = State.run(self)(initialState)
  }
}
