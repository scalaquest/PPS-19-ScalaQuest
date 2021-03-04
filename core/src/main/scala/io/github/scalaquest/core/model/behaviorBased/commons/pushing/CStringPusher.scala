package io.github.scalaquest.core.model.behaviorBased.commons.pushing

import io.github.scalaquest.core.model.TriggerPusher.{MessageTriggers, StringMessageTriggers}
import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import io.github.scalaquest.core.model.{ComposableStringPusher, Direction}

import scala.annotation.tailrec

/**
 * A [[ComposableStringPusher]] that have can handle the messages contained into [[CMessagesExt]].
 *
 * @param model
 *   contains the Messages to interpret.
 */
abstract class CStringPusher(model: BehaviorBasedModel with CMessagesExt)
  extends ComposableStringPusher {
  import model.CMessages._

  /**
   * Recognize every common message and output its content.
   * @return
   */
  final def base: StringMessageTriggers = {
    case Inspected(room, items, neighbors) =>
      val ordItems = items.toList.sortWith(_.toString < _.toString)
      s"The ${room.name} contains ${printItems(ordItems)}${printNeighbors(neighbors)}"

    case InspectedBag(items) =>
      val ordItems = items.toList.sortWith(_.toString < _.toString)
      s"The bag contains ${printItems(ordItems)}"

    case ReversedIntoLocation(items) =>
      val ordItems = items.toList.sortWith(_.toString < _.toString)
      s"Current room now contains also ${printItems(ordItems)}!"

    case Eaten(item)  => s"The $item has been eaten!"
    case Taken(item)  => s"The $item has been taken!"
    case Opened(item) => s"The $item has been opened!"

    case AlreadyOpened(item)         => s"The $item is already opened!"
    case FailedToOpen(item)          => s"The $item failed to open!"
    case FailedToEnter(item)         => s"The $item is locked! You cannot enter now."
    case Navigated(room)             => s"You entered $room!"
    case FailedToNavigate(direction) => s"You cannot go $direction from here!"
    case Print(msg)                  => msg
    case Welcome(msg)                => msg
    case Won                         => "You win!"
    case Lost                        => "You lose!"
  }

  @tailrec
  private def printItems(tail: List[CMessagesExt#I], acc: String = ""): String = {
    tail match {
      case ::(head, Nil)  => s"${acc}a ${head.toString}."
      case ::(head, next) => printItems(next, s"${acc}a ${head.toString}, ")
      case Nil            => "nothing."
    }
  }

  private def printNeighbors(neighbors: Map[Direction, CMessagesExt#RM]): String = {
    neighbors match {
      case ns if ns.isEmpty => "\nYou cannot go anywhere now."
      case ns =>
        ns.map(n => s"There is a ${n._2.toString} in direction ${n._1.toString}.")
          .fold("")(_ + "\n" + _)
    }
  }
}

/**
 * Object with a useful construct for create a [[CStringPusher]].
 */
object CStringPusher {

  /**
   * Create the pusher giving him only the game's model.
   * @param model
   *   the actual model game.
   * @param _additionalTriggers
   *   add some customized triggers with regard to the base ones.
   * @return
   *   an instance of CommonStringPusher.
   */
  def apply(
    model: CMessagesExt,
    _additionalTriggers: MessageTriggers[String] = PartialFunction.empty
  ): CStringPusher = {
    new CStringPusher(model) {
      override def extra: MessageTriggers[String] = _additionalTriggers
    }
  }
}
