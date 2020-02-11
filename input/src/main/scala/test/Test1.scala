/*
rule = Nothrow
 */
package test

object Test1 {
  throw new RuntimeException("ops.") // assert: Nothrow

  Option(null).get // assert: Nothrow

  safeDiv(10, 0).get // assert: Nothrow
  Right("").right.get // assert: Nothrow

  def safeDiv(i: Int, n: Int) = if (n != 0) Some(i / n) else None
}
