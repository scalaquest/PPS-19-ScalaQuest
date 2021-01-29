package io.github.scalaquest.core.model

import io.github.scalaquest.core.model.MessagePusher.MessageTriggers

/**
 * A class that aims to interpret [[Message]] s and transform them in an output for the user.
 *
 * The output could be of any type in the base implementation. If [[Message]] is defined in
 * <b>MessageTriggers</b> it will trigger the specific answer, otherwise return <b>not found</b>
 * @tparam A
 *   the concrete type that will shown to the user.
 */
abstract class MessagePusher[A] {

  /**
   * A [[PartialFunction]] used to find a match for the given input.
   * @return
   *   A [[PartialFunction]] used to find a match for the given input.
   */
  def triggers: MessageTriggers[A]

  /**
   * An output returned if the message is not recognized.
   * @return
   *   An output returned if the message is not recognized.
   */
  def notFound: A

  /**
   * Finds a match for the given [[Message]] analyzing the [[MessagePusher::triggers]].
   * @param input
   *   the [[Message]] to control.
   * @return
   *   An output matching with the given message; [[MessagePusher::notFound]] otherwise.
   */
  def push(input: Message): A = {
    triggers.lift(input).getOrElse(notFound)
  }

  def push(input: Seq[Message]): A
}

/**
 * A [[MessagePusher]] implementation with [[String]] as concrete type for the user's output.
 */
abstract class StringPusher extends MessagePusher[String] {
  override def notFound: String = "Nothing happened!"

  override def push(input: Seq[Message]): String =
    input.map(push).reduceOption(_ + "\n" + _) getOrElse ""
}

/**
 * A [[StringPusher]] that exposes a [[Composable]] behavior, letting the user define base and
 * additional [[MessageTriggers]].
 */
abstract class ComposableStringPusher extends StringPusher with Composable[String]

/**
 * A mixin for [[MessagePusher]] s, that enables the possibility to define two levels of
 * [[MessageTriggers]].
 *
 * This is commonly used when it is needed to define some base extensible triggers, that should be
 * easily extended letting the user defining the extras. When the triggers are effectively used,
 * extras will be privileged, as the extra should always overwrite the base.
 */
trait Composable[A] extends MessagePusher[A] {

  /**
   * The extra [[MessageTriggers]] of the pusher.
   * @return
   *   The extra [[MessageTriggers]].
   */
  def extra: MessageTriggers[A] = PartialFunction.empty

  /**
   * The base [[MessageTriggers]] of the pusher.
   * @return
   */
  def base: MessageTriggers[A]

  /**
   * All the triggers with the right order.
   * @return
   *   the chosen answer.
   */
  override def triggers: MessageTriggers[A] = Seq(extra, base).reduce(_ orElse _)
}

/**
 * Companion object fot the [[MessagePusher]]. It contains utility wrappers for the matchers used in
 * the various [[MessagePusher]], that are actually [[PartialFunction]] s.
 */
object MessagePusher {
  type MessageTriggers[A]    = PartialFunction[Message, A]
  type StringMessageTriggers = MessageTriggers[String]
}
