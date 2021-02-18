package io.github.scalaquest.core.model.behaviorBased.simple.impl

import io.github.scalaquest.core.model.Message
import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel

trait ReactionUtilsExt extends BehaviorBasedModel {

  object Reaction {

    def create(f: S => S, messages: Seq[Message]): Reaction = s => (f(s), messages)

    def apply(f: S => S, messages: Message*): Reaction = create(f, messages)

    def empty: Reaction = create(identity[S], Seq())

    def messages(msgs: Message*): Reaction = appendMessages(msgs)(empty)

    def appendMessage(msg: Message)(r: Reaction): Reaction = appendMessages(Seq(msg))(r)

    def appendMessages(msgs: Seq[Message])(r: Reaction): Reaction =
      s => {
        val (s1, m1) = r(s)
        (s1, m1 ++ msgs)
      }

    def combine(a: Reaction, b: Reaction): Reaction = { s0 =>
      val (s1, m1) = a(s0)
      val (s2, m2) = b(s1)
      (s2, m1 ++ m2)
    }

    def fold(rs: Seq[Reaction]): Reaction = rs.reduceOption(combine).getOrElse(empty)

    def foldV(rs: Reaction*): Reaction = fold(rs)
  }

  object Update {
    private type U = S => S

    def apply(fs: U*): U = fs.reduceOption(_ andThen _) getOrElse identity[S]
  }
}
