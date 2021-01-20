package io.github.scalaquest.core.pipeline.interpreter

import io.github.scalaquest.core.model.{ItemRef, ItemRetriever, Model}
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
      val itemRetriever: ItemRetriever[model.I] = ItemRetriever(model)(itemDict)

      override def interpret(
        resolverResult: ResolverResult
      ): Either[String, InterpreterResult[model.Reaction]] = {
        val eventualReaction: Either[String, model.Reaction] = resolverResult.statement match {
          case Statement.Intransitive(action) =>
            ground.use(action, state) toRight s"Could not recognize ${action.name}"

          case Statement.Transitive(action, itemRetriever(item)) =>
            item.use(action, state) toRight s"Couldn't recognize ${action.name} on ${item.itemRef}"

          case Statement.Ditransitive(
                action,
                itemRetriever(directObj),
                itemRetriever(indirectObj)
              ) =>
            directObj.use(
              action,
              state,
              Some(indirectObj)
            ) toRight s"Couldn't recognize ${action.name} on ${directObj.itemRef} and ${indirectObj.itemRef}"
        }

        eventualReaction.map(InterpreterResult(model)(_))
      }
    }
    SimpleInterpreter(state)
  }
}
