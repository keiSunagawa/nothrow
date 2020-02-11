package fix

import scalafix.v1._
import scala.meta._

class Nothrow extends SemanticRule("Nothrow") {
  private val optionGet = SymbolMatcher.normalized("scala.Option.get")
  private val eitherGet =
    SymbolMatcher.normalized("scala.util.Either.RightProjection.get")

  override def fix(implicit doc: SemanticDocument): Patch = {
    doc.tree.collect {
      case t: Term.Throw =>
        Patch.lint(
          new Diagnostic {
            override def position: _root_.scala.meta.Position = t.pos

            override def message: String =
              "throw keyword not allowed in this project."
          }
        )
      case t @ Term.Select(_, n @ Term.Name("get")) =>
        if (optionGet.matches(n.symbol)) {
          Patch.lint(
            new Diagnostic {
              override def position: _root_.scala.meta.Position = t.pos

              override def message: String =
                "Option#get not allowed in this project."
            }
          )
        } else if (eitherGet.matches(n.symbol)) {
          Patch.lint(
            new Diagnostic {
              override def position: _root_.scala.meta.Position = t.pos

              override def message: String =
                "Either#right.get not allowed in this project."
            }
          )
        } else {
          Patch.empty
        }
      case _ => Patch.empty
    }.asPatch
  }
}
