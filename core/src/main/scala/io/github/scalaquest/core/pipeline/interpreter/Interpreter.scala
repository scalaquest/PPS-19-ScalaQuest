package io.github.scalaquest.core.pipeline.interpreter

import io.github.scalaquest.core.model.{ItemRef, Model}
import io.github.scalaquest.core.pipeline.resolver.{ResolverResult, Statement}

trait Interpreter[M <: Model, R] {
  def interpret(resolverResult: ResolverResult): Either[String, InterpreterResult[R]]
}

object Interpreter {

  type Builder[M <: Model, S, R] = S => Interpreter[M, R]

  def builder[M <: Model](model: M)(
    itemDict: Map[ItemRef, model.I],
    ground: model.G
  ): Builder[model.type, model.S, model.Reaction] = apply(model)(_, itemDict, ground)

  def apply[M <: Model](model: M)(
    state: model.S,
    itemDict: Map[ItemRef, model.I],
    ground: model.G
  ): Interpreter[model.type, model.Reaction] = {

    case class SimpleInterpreter(state: model.S) extends Interpreter[model.type, model.Reaction] {

      // The interpreter should know the Map[ItemRef, I] in order to create a retriever
      // or should be passed the itemRetriever directly.
      val refToItem: RefToItem[model.I] = RefToItem(model)(itemDict)

      override def interpret(
        resolverResult: ResolverResult
      ): Either[String, InterpreterResult[model.Reaction]] =
        for {
          maybeReaction <- resolverResult.statement match {
            case Statement.Intransitive(action) =>
              ground
                .use(action, state)
                .toRight(s"Could not recognize action")

            case Statement.Transitive(action, refToItem(item)) =>
              item
                .use(action, state)
                .toRight(s"Couldn't recognize action on the given item")

            case Statement.Ditransitive(action, refToItem(directObj), refToItem(indirectObj)) =>
              directObj
                .use(action, state, Some(indirectObj))
                .toRight(s"Couldn't recognize action on the given item with the other item")
          }
        } yield InterpreterResult(model)(maybeReaction)
    }

    SimpleInterpreter(state)
  }
}
