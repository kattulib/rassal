object MainExample {
  @main def example1: Unit =
    def solve(a: Int, b: Int): Int = 
      a + b

    assert(solve(1, 2) == 3, "wrong!")
}
