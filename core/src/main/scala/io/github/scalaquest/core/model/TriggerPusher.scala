/*
 * Copyright (c) 2021 ScalaQuest Team.
 *
 * This file is part of ScalaQuest, and is distributed under the terms of the MIT License as
 * described in the file LICENSE in the ScalaQuest distribution's top directory.
 */

package io.github.scalaquest.core.model

import io.github.scalaquest.core.model.TriggerPusher.MessageTriggers

/**
 * Pusher that push in output [[Message]] s of the Game.
 * @tparam A
 *   generic for any output Type.
 */
trait MessagePusher[A] {

  /**
   * Push the [[Message]] s in output.
   * @param input
   *   the messages that have to be pushed.
   * @return
   *   the pushed messages in the selected output type.
   */
  def push(input: Seq[Message]): A
}

/**
 * A class that aims to interpret [[Message]] s and transform them in an output for the user.
 *
 * The output could be of any type in the base implementation. If [[Message]] is defined in
 * <b>MessageTriggers</b> it will trigger the specific answer, otherwise return <b>not found</b>
 * @tparam A
 *   the concrete type that will shown to the user.
 */
abstract class TriggerPusher[A] extends MessagePusher[A] {

  /**
   * A [[PartialFunction]] used to find a match for the given input.
   * @return
   *   A [[PartialFunction]] used to find a match for the given input.
   */
  def triggers: MessageTriggers[A] = PartialFunction.empty

  /**
   * An output returned if the message is not recognized.
   * @return
   *   An output returned if the message is not recognized.
   */
  def notFound: A

  /**
   * Finds a match for the given [[Message]] analyzing the [[TriggerPusher::triggers]].
   *
   * @param input
   *   the [[Message]] to control.
   * @return
   *   An output matching with the given message; [[TriggerPusher::notFound]] otherwise.
   */
  private final def eval(input: Message): Option[A] = {
    triggers.lift(input)
  }

  /**
   * Define how two [[A]] have to be combined.
   */
  def combine(x: A, y: A): A

  override final def push(input: Seq[Message]): A =
    input.flatMap(eval).reduceOption(combine).getOrElse(notFound)
}

/**
 * A [[TriggerPusher]] implementation with [[String]] as concrete type for the user's output.
 */
abstract class StringPusher extends TriggerPusher[String] {
  override def notFound: String = "Nothing happened!"

  override def combine(x: String, y: String): String = x + "\n" + y

}

/**
 * A [[StringPusher]] that exposes a [[Composable]] behavior, letting the user define base and
 * additional [[MessageTriggers]].
 */
abstract class ComposableStringPusher extends StringPusher with Composable[String]

/**
 * A mixin for [[TriggerPusher]] s, that enables the possibility to define two levels of
 * [[MessageTriggers]].
 *
 * This is commonly used when it is needed to define some base extensible triggers, that should be
 * easily extended letting the user defining the extras. When the triggers are effectively used,
 * extras will be privileged, as the extra should always overwrite the base.
 */
trait Composable[A] extends TriggerPusher[A] {

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
 * Companion object fot the [[TriggerPusher]]. It contains utility wrappers for the matchers used in
 * the various [[TriggerPusher]], that are actually [[PartialFunction]] s.
 */
object TriggerPusher {

  /**
   * The implementations of MessageTriggers is a PartialFunction with [[Message]] and a generic type
   * [[A]].
   * @tparam A
   *   the generic type to expose in output.
   */
  type MessageTriggers[A] = PartialFunction[Message, A]

  /**
   * A trigger Pusher that works with String.
   */
  type StringMessageTriggers = MessageTriggers[String]
}
