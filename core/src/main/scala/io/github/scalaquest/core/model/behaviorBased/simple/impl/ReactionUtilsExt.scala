package io.github.scalaquest.core.model.behaviorBased.simple.impl

import io.github.scalaquest.core.model.Message
import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel

trait ReactionUtilsExt extends BehaviorBasedModel {

  implicit class EnhancedReaction(r: Reaction) {
    def map(g: S => S): Reaction = Reaction.map(g)(r)

    def flatMap(g: S => Reaction): Reaction = Reaction.flatMap(g)(r)
  }

  object Reaction {

    def map(g: S => S)(f: Reaction): Reaction =
      s => {
        val (s1, m1) = f(s)
        (g(s1), m1)
      }

    def flatMap(g: S => Reaction)(f: Reaction): Reaction = { s =>
      val (s1, m1) = f(s)
      val (s2, m2) = g(s1)(s1)
      (s2, m1 ++ m2)
    }

    case object MyMessage extends Message

    val aaaaa: Reaction = for {
      s <- Reaction.empty
      l = s.location
      r <- if (!s.ended) Reaction.messages(MyMessage) else Reaction.empty

    } yield r

    def combine(a: Reaction, b: Reaction): Reaction = a.flatMap(_ => b)

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

    // def combine(a: Reaction, b: Reaction): Reaction = { s0 =>
    //   val (s1, m1) = a(s0)
    //   val (s2, m2) = b(s1)
    //   (s2, m1 ++ m2)
    // }

    def fold(rs: Seq[Reaction]): Reaction = rs.reduceOption(combine).getOrElse(empty)

    def foldV(rs: Reaction*): Reaction = fold(rs)
  }

  object Update {
    private type U = S => S

    def apply(fs: U*): U = fs.reduceOption(_ andThen _) getOrElse identity[S]

    def empty: U = identity[S]
  }
}
