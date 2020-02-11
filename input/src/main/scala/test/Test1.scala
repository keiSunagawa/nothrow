/*
rule = Nothrow
 */
package test

object Test1 {
  throw new RuntimeException("ops.") // assert: Nothrow
}
