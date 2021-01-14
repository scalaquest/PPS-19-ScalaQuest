package io.github.scalaquest.core.pipeline.interpreter

import io.github.scalaquest.core.model.std.StdModel
import io.github.scalaquest.core.model.{ItemRetriever, Model}
import io.github.scalaquest.core.pipeline.resolver.{ResolverResult, Statement}

trait Interpreter[M <: Model, R] {
  def interpret(resolverResult: ResolverResult): Either[String, InterpreterResult[R]]
}

object Interpreter {

  type InterpreterBuilder[S, M <: Model, R] = S => Interpreter[M, R]

  def apply[M <: Model](implicit model: M): InterpreterBuilder[model.S, model.type, model.Reaction] = {

    type S        = model.S
    type I        = model.I
    type Reaction = model.Reaction

    case class SimpleInterpreter(state: S) extends Interpreter[model.type, model.Reaction] {
      private val useIntransitive: Option[Reaction] = ???

      // The interpreter should know the Map[ItemRef, I] in order to create a retriever
      // or should be passed the itemRetriever directly.
      val itemRetriever: ItemRetriever[I] = ???

      override def interpret(resolverResult: ResolverResult): Either[String, InterpreterResult[model.Reaction]] = {
        val eventualReaction: Either[String, Reaction] = resolverResult.statement match {
          case Statement.Intransitive(action) =>
            useIntransitive toRight
              s"Could not recognize ${action.name}"

          case Statement.Transitive(action, itemRetriever(item)) =>
            item.use(action, state) toRight s"Couldn't recognize ${action.name} on ${item.name}"

          case Statement.Ditransitive(action, itemRetriever(directObj), itemRetriever(indirectObj)) =>
            directObj.use(
              action,
              state,
              Some(indirectObj)
            ) toRight s"Couldn't recognize ${action.name} on ${directObj.name} and ${indirectObj.name}"
        }

        eventualReaction.map(InterpreterResult.builder(model).build(_))
      }
    }

    SimpleInterpreter
  }

}

object IntperpreterTest {
  implicit val model: StdModel.type     = StdModel
  implicit val reaction: model.Reaction = ???

  val res: InterpreterResult[model.Reaction] = InterpreterResult(reaction)
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
