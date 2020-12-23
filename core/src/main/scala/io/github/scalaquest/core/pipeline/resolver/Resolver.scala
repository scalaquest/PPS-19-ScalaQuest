package io.github.scalaquest.core.pipeline.resolver

import io.github.scalaquest.core.model.{
  Action,
  DitransitiveAction,
  IntransitiveAction,
  Model,
  TransitiveAction
}
import io.github.scalaquest.core.pipeline.parser.{AST, ParserResult}

sealed trait Statement

object Statement {
  final case class Intransitive(action: IntransitiveAction) extends Statement
  final case class Transitive(action: TransitiveAction, target: Model#Item)
      extends Statement
  final case class Ditransitive(
      action: DitransitiveAction,
      target1: Model#Item,
      target2: Model#Item
  ) extends Statement
}

trait ResolverResult {
  def statement: Statement
}

case class SimpleResolverResult(statement: Statement) extends ResolverResult

trait Resolver {
  def resolve(parserResult: ParserResult): Either[String, ResolverResult]
}

trait ResolverFactory {
  def create(
      actions: Map[String, Action],
      items: Map[String, Model#Item]
  ): Resolver
}

//trait ResolverTemplate extends Resolver {
//
//  def actions: Map[String, Action]
//
//  def items: Map[String, Item]
//
//  def matchAction[A](verb: String)(actionType: Class[A]): PartialFunction[(String, Action), A] =
//    { case (v, a: A) if v == verb => a }
//
//  def retrieveAction[A](verb: String)(actionType: Class[A]): Either[String, A] =
//    actions collectFirst matchAction(verb)(actionType) toRight s"Couldn't understand ${verb}."
//
//  def retrieveItem(item: String): Either[String, Item] =
//    items get item toRight s"Could't understand ${item}"
//
//  override def resolve(parserResult: ParserResult): Either[String, ResolverResult] = {
//    val statement = parserResult.tree match {
//      case AST.Ditransitive(verb, _, target1, target2) =>
//        for {
//          a  <- retrieveAction(verb)(Class[DitransitiveAction])
//          t1 <- retrieveItem(target1)
//          t2 <- retrieveItem(target2)
//        } yield Statement.Ditransitive(a, t1, t2)
//      case AST.Intransitive(verb, _) =>
//        for {
//          a  <- retrieveAction(verb)(Class[IntransitiveAction])
//        } yield Statement.Intransitive(a)
//      case AST.Transitive(verb, _, target1) =>
//        for {
//          a  <- retrieveAction(verb)(Class[TransitiveAction])
//          t1 <- retrieveItem(target1)
//        } yield Statement.Transitive(a, t1)
//      case _ => Left("")
//    }
//    statement.map(SimpleResolverResult(_))
//  }
//}
