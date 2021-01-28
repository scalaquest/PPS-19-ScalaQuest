package io.github.scalaquest.core.model.behaviorBased.common.pushing

import io.github.scalaquest.core.model.ComposableStringPusher
import io.github.scalaquest.core.model.MessagePusher.{MessageTriggers, StringMessageTriggers}

import scala.annotation.tailrec

/**
 * A [[ComposableStringPusher]] that have an implementation for [[commonTriggers]].
 * @param model
 *   contains some implementation of [[commonTriggers]].
 */
abstract class CommonStringPusher(model: CommonMessages) extends ComposableStringPusher {

  override def additionalTriggers: MessageTriggers[String] = PartialFunction.empty

  final def commonTriggers: StringMessageTriggers = {
    case model.Inspected(room, items) =>
      @tailrec
      def go(tail: List[CommonMessages#I], acc: String = ""): String = {
        tail match {
          case ::(head, Nil)  => s"${acc}a ${head.name}."
          case ::(head, next) => go(next, s"${acc}a ${head.name}, ")
          case Nil            => "nothing."
        }
      }

      val ordItems = items.toList.sortWith(_.name < _.name)
      s"The ${room.name} contains ${go(ordItems)}"

    case model.Eaten(item)     => s"The ${item.name} has been eaten!"
    case model.Taken(item)     => s"The ${item.name} has been taken!"
    case model.Closed(item)    => s"The ${item.name} has been closed!"
    case model.Opened(item)    => s"The ${item.name} has been opened!"
    case model.Navigated(room) => s"You entered ${room.name}!"
  }
}

/**
 * Object with some useful construct for create a [[CommonStringPusher]].
 */
object CommonStringPusher {
  def apply(model: CommonMessages): CommonStringPusher = new CommonStringPusher(model) {}

  def apply(
    model: CommonMessages,
    _additionalTriggers: MessageTriggers[String]
  ): CommonStringPusher = {
    new CommonStringPusher(model) {
      override def additionalTriggers: MessageTriggers[String] = _additionalTriggers
    }
  }
}
