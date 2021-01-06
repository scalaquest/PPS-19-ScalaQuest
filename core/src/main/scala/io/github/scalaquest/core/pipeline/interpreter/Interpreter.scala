package io.github.scalaquest.core.pipeline.interpreter

import io.github.scalaquest.core.model.impl.SimpleModel
import io.github.scalaquest.core.model.{ItemRetriever, Model}
import io.github.scalaquest.core.pipeline.resolver.{ResolverResult, Statement}

trait Interpreter[M <: Model, R] {
  def interpret(resolverResult: ResolverResult): Either[String, InterpreterResult[R]]
}

trait InterpreterResult[R] {
  def reaction: R
}

object InterpreterResult {

  trait InterpreterResultBuilder[R] {
    def build(a: R): InterpreterResult[R]
  }

  def apply[M <: Model](implicit model: M): InterpreterResultBuilder[model.Reaction] = {
    case class SimpleInterpreterResult(reaction: model.Reaction) extends InterpreterResult[model.Reaction]
    SimpleInterpreterResult(_)
  }
}

object aaa {
  implicit val model: SimpleModel.type       = SimpleModel
  implicit val reaction: model.Reaction      = ???
  val res: InterpreterResult[model.Reaction] = InterpreterResult[SimpleModel.type] build reaction
}

object Interpreter {

  type InterpreterBuilder[S, M <: Model, R] = S => Interpreter[M, R]

  def apply[M <: Model](implicit model: M): InterpreterBuilder[model.S, model.type, model.Reaction] = {

    type S        = model.S
    type I        = model.I
    type Reaction = model.Reaction

    case class SimpleInterpreter(state: S) extends Interpreter[model.type, model.Reaction] {
      private val useIntransitive: Option[Reaction] = ???

      val itemRetriever: ItemRetriever = ItemRetriever(model)

      override def interpret(resolverResult: ResolverResult): Either[String, InterpreterResult[model.Reaction]] = {
        val eventualReaction: Either[String, Reaction] = resolverResult.statement match {
          case Statement.Intransitive(action) =>
            useIntransitive toRight
              s"Could not recognize ${action.name}"
          //// fixme experiments

          case Statement.Transitive(action, itemRetriever(item)) =>
            val s0: model.S = state
            val i: model.I  = item

            val transitiveResult = i.useTransitive(action, state)
            //          val i2: model.I = model.getItem(new ItemRef {})
            //          item.useTransitive(action, state)
            //          val res: Option[model.Reaction] = item.useTransitive5(action, state)
            Left("no")

          //// fixme end of experiments
        }

        eventualReaction.map(InterpreterResult(model).build(_))
      }
    }

    (s: model.S) => SimpleInterpreter(s)

  }

}
//
//object useInterpreter {
//  implicit val model: SimpleModel.type                       = SimpleModel
//  val interpreterBuilder: model.S => Interpreter[model.type] = TypedInterpreter2[model.type]
//  val state: model.S                                         = ???
//  val rr: ResolverResult                                     = ???
//  val ir: Either[String, InterpreterResult[model.Reaction]]  = interpreterBuilder(state).interpret(rr)
//  ir map (_.reaction(model)(state))
//}
