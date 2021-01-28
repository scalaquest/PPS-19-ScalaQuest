package io.github.scalaquest.core.model

import io.github.scalaquest.core.model.MessagePusher.MessageTriggers

/**
 * A class that take a [[Message]] and return a generic output for the user.
 *
 * If [[Message]] is defined in <b>MessageTriggers</b> it will trigger the specific answer,
 * otherwise return <b>not found</b>
 * @tparam A
 *   the concrete type that will shown to the user.
 */
abstract class MessagePusher[A] {

  /**
   * Defines what MessagePusher have to do when encounter some specific messages.
   * @return
   *   the chosen answer.
   */
  def triggers: MessageTriggers[A]

  /**
   * The standard output returned if the message isn't recognized.
   * @return
   */
  def notFound: A

  /**
   * This method control that a given message is present in <b>triggers</b>, otherwise return
   * <b>notFound</b>.
   * @param input
   *   the [[Message]] to control.
   * @return
   *   the specific result of <b>triggers</b>, or if message don't match any combination return
   *   <b>notFound</b>.
   */
  def push(input: Message): A = {
    triggers.lift(input).getOrElse(notFound)
  }
}

/**
 * A [[MessagePusher]] implementation with [[String]] as concrete type of return.
 */
abstract class StringPusher extends MessagePusher[String] {
  override def notFound: String = "Nothing happened!"
}

/**
 * A pusher that have the characteristic of [[StringPusher]] and [[Composable]].
 */
abstract class ComposableStringPusher extends StringPusher with Composable[String]

/**
 * A pusher that contain some <b>additional triggers</b> and some <b>common triggers</b>. <b>Common
 * triggers</b> could have been already implemented by creator of the game. <b>Additional
 * triggers</b> could have been added for the creator of the specific story. If a <b>additional
 * trigger</b> answer to the same message of a <b>common triggers</b> the <b>additional</b> will be
 * privileged.
 */
trait Composable[A] extends MessagePusher[A] {

  /**
   * The custom triggers added to the story.
   * @return
   *   The possible triggers
   */
  def additionalTriggers: MessageTriggers[A] = PartialFunction.empty

  def commonTriggers: MessageTriggers[A]

  /**
   * All the triggers with the right order.
   * @return
   *   the chosen answer.
   */
  override def triggers: MessageTriggers[A] =
    Seq(additionalTriggers, commonTriggers).reduce(_ orElse _)
}

/**
 * Object with types involved in for [[MessagePusher]]
 */
object MessagePusher {
  type MessageTriggers[A]    = PartialFunction[Message, A]
  type StringMessageTriggers = MessageTriggers[String]
}
