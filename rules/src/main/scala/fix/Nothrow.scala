package fix

import metaconfig.Configured
import scalafix.v1._
import scala.meta._

class Nothrow extends SemanticRule("Nothrow") {
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
      case _ => Patch.empty
    }.asPatch
  }
}
