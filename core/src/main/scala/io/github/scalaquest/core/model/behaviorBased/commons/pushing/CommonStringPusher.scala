package io.github.scalaquest.core.model.behaviorBased.commons.pushing

import io.github.scalaquest.core.model.{ComposableStringPusher, Direction}
import io.github.scalaquest.core.model.MessagePusher.{MessageTriggers, StringMessageTriggers}
import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel

import scala.annotation.tailrec

/**
 * A [[ComposableStringPusher]] that have can handle the messages contained into
 * [[CommonMessagesExt]].
 *
 * @param model
 *   contains the Messages to interpret.
 */
abstract class CommonStringPusher(model: BehaviorBasedModel with CommonMessagesExt)
  extends ComposableStringPusher {

  override def extra: MessageTriggers[String] = PartialFunction.empty

  final def base: StringMessageTriggers = {
    case model.Inspected(room, items, neighbors) =>
      @tailrec
      def printItems(tail: List[CommonMessagesExt#I], acc: String = ""): String = {
        tail match {
          case ::(head, Nil)  => s"${acc}a ${head.toString}."
          case ::(head, next) => printItems(next, s"${acc}a ${head.toString}, ")
          case Nil            => "nothing."
        }
      }

      def printNeighbors(neighbors: Map[Direction, CommonMessagesExt#RM]): String = {
        neighbors match {
          case ns if ns.isEmpty => "I cannot go anywhere now."
          case ns =>
            ns.map(n => s"There is a ${n._2.toString} in direction ${n._1.toString}.\n")
              .fold("")(_ + _)
        }
      }

      val ordItems = items.toList.sortWith(_.toString < _.toString)

      s"The ${room.name} contains ${printItems(ordItems)}\n${printNeighbors(neighbors)}"

    case model.Eaten(item)     => s"The ${item.toString} has been eaten!"
    case model.Taken(item)     => s"The ${item.toString} has been taken!"
    case model.Opened(item)    => s"The ${item.toString} has been opened!"
    case model.Navigated(room) => s"You entered ${room.toString}!"
    case model.Print(msg)      => msg
    case model.Win             => "You win!"
    case model.Lose            => "You lose!"
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
