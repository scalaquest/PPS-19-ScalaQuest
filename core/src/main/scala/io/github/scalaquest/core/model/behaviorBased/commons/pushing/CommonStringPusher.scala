package io.github.scalaquest.core.model.behaviorBased.commons.pushing

import io.github.scalaquest.core.model.{ComposableStringPusher, Direction}
import io.github.scalaquest.core.model.MessagePusher.{MessageTriggers, StringMessageTriggers}

import scala.annotation.tailrec
import scala.collection.immutable.{AbstractMap, SeqMap, SortedMap}

/**
 * A [[ComposableStringPusher]] that have an implementation for [[base]].
 *
 * @param model
 *   contains some implementation of [[base]].
 */
abstract class CommonStringPusher(model: CommonMessagesExt) extends ComposableStringPusher {

  override def extra: MessageTriggers[String] = PartialFunction.empty

  final def base: StringMessageTriggers = {
    case model.Inspected(room, items) =>
      @tailrec
      def go(tail: List[CommonMessagesExt#I], acc: String = ""): String = {
        tail match {
          case ::(head, Nil)  => s"${acc}a ${head.toString}."
          case ::(head, next) => go(next, s"${acc}a ${head.toString}, ")
          case Nil            => "nothing."
        }
      }

      val ordItems = items.toList.sortWith(_.toString < _.toString)
      s"The ${room.name} contains ${go(ordItems)}"

    case model.Oriented(room, neighbors) =>
      def printNeighbors(neighbors: Map[Direction, CommonMessagesExt#RM]): String =
        neighbors
          .map(n => s"There is a ${n._2.toString} in direction ${n._1.toString}.\n")
          .fold("")(_ + _)

      s"I am in the ${room.name}.\n${printNeighbors(neighbors)}"

    case model.Eaten(item)     => s"The ${item.toString} has been eaten!"
    case model.Taken(item)     => s"The ${item.toString} has been taken!"
    case model.Closed(item)    => s"The ${item.toString} has been closed!"
    case model.Opened(item)    => s"The ${item.toString} has been opened!"
    case model.Navigated(room) => s"You entered ${room.toString}!"
  }
}

/**
 * Object with some useful construct for create a [[CommonStringPusher]].
 */
object CommonStringPusher {
  def apply(model: CommonMessagesExt): CommonStringPusher = new CommonStringPusher(model) {}

  def apply(
    model: CommonMessagesExt,
    _additionalTriggers: MessageTriggers[String]
  ): CommonStringPusher = {
    new CommonStringPusher(model) {
      override def extra: MessageTriggers[String] = _additionalTriggers
    }
  }
}
