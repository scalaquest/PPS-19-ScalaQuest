package io.github.scalaquest.core.model.behaviorBased.simple.impl

import io.github.scalaquest.core.model.Message
import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel

trait ReactionUtilsExt extends BehaviorBasedModel {

  implicit class EnhancedReaction(r: Reaction) {
    def map(g: S => S): Reaction = Reaction.map(g)(r)

    def flatMap(g: S => Reaction): Reaction = Reaction.flatMap(g)(r)
  }

  object Reaction {

    def appendMessage(msg: Message)(r: Reaction): Reaction = appendMessages(Seq(msg))(r)

    def appendMessages(msgs: Seq[Message])(r: Reaction): Reaction =
      s => {
        val (s1, m1) = r(s)
        (s1, m1 ++ msgs)
      }

    def apply(f: S => S, messages: Message*): Reaction = create(f, messages)

    def combine(a: Reaction, b: Reaction): Reaction = a.flatMap(_ => b)

    def create(f: S => S, messages: Seq[Message]): Reaction = s => (f(s), messages)

    def empty: Reaction = create(identity[S], Seq())

    def flatMap(g: S => Reaction)(f: Reaction): Reaction = { s =>
      val (s1, m1) = f(s)
      val (s2, m2) = g(s1)(s1)
      (s2, m1 ++ m2)
    }

    def map(g: S => S)(f: Reaction): Reaction =
      s => {
        val (s1, m1) = f(s)
        (g(s1), m1)
      }

    def messages(msgs: Message*): Reaction = appendMessages(msgs)(empty)

    def pure(s: S): Reaction = create(_ => s, Seq.empty)

  }

  object Update {
    private type U = S => S

    def apply(fs: U*): U = fs.reduceOption(_ andThen _) getOrElse identity[S]

    def empty: U = identity[S]
  }
}
