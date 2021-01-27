package io.github.scalaquest.core.model

import io.github.scalaquest.core.model.MessagePusher.MessageTriggers

abstract class MessagePusher[A] {
  def triggers: MessageTriggers[A]

  def notFound: A

  def push(input: Message): A = {
    triggers.lift(input).getOrElse(notFound)
  }
}

abstract class StringPusher extends MessagePusher[String] {
  override def notFound: String = "Nothing happened!"
}

abstract class ComposableStringPusher extends StringPusher with Composable[String]

// un pusher che funziona con i message common e risponde con delle robe preconfezionate
trait Composable[A] extends MessagePusher[A] {
  def additionalTriggers: MessageTriggers[A] = PartialFunction.empty
  def commonTriggers: MessageTriggers[A]

  override def triggers: MessageTriggers[A] =
    Seq(additionalTriggers, commonTriggers).reduce(_ orElse _)
}

object MessagePusher {
  type MessageTriggers[A]    = PartialFunction[Message, A]
  type StringMessageTriggers = MessageTriggers[String]
}
