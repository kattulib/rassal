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

import kuram.data.State

opaque type Gen[+A] = State[Seed, A]

object Gen {
  def apply[A](instance: Seed => (Seed, A)): Gen[A] = State.apply(instance)

  def lift[A](a: A): Gen[A] = State.lift(a)

  def seed(initialValue: Long): Seed = Seed(initialValue)

  def nextInt: Gen[Int] = Gen { _.next }

  def nextNonInt: Gen[Int] = nextInt.map { i =>
    if i < 0 then -(i + 1) else i
  }

  def nextDouble: Gen[Double] = nextNonInt.map { i =>
    i / (Int.MaxValue.toDouble + 1)
  }

  def nextBoolean: Gen[Boolean] = nextInt.map { i =>
    if i < 0 then false else true
  }

  def nextString: Gen[String] = nextInt.map { _.toChar.toString }

  extension [A](self: Gen[A]) {
    def map[B](f: A => B): Gen[B] = State.map(self)(f)
    def flatMap[B](f: A => Gen[B]): Gen[B] = State.flatMap(self)(f)
    def run(initialState: Seed): (Seed, A) = State.run(self)(initialState)
  }
}
