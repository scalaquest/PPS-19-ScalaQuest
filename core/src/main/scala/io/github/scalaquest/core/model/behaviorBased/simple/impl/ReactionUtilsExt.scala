package io.github.scalaquest.core.model.behaviorBased.simple.impl

import io.github.scalaquest.core.model.Message
import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel

/**
 * Extension for the model. Adds some common implementation of the [[BehaviorBasedModel]].
 */
trait ReactionUtilsExt extends BehaviorBasedModel {

  /**
   * A class that wrap a reaction and it map and flatmap methods.
   * @param r
   *   the wrapped Reaction.
   */
  implicit class EnhancedReaction(r: Reaction) {

    /**
     * Use Reaction.map method with this and an other reaction.
     * @param g
     *   the other reaction
     * @return
     *   a new Reaction that is the Reaction.map of the two reactions.
     */
    def map(g: S => S): Reaction = Reaction.map(g)(r)

    /**
     * Use Reaction.flatmap method with this and an other reaction.
     * @param g
     *   the other reaction.
     * @return
     *   a new Reaction that is the Reaction.flatmap of the two reactions.
     */
    def flatMap(g: S => Reaction): Reaction = Reaction.flatMap(g)(r)
  }

  /**
   * Object with some methods to work on Reaction.
   */
  object Reaction {

    /**
     * Append a specific Message to a Reaction.
     * @param msg
     *   the message to be appended.
     * @param r
     *   the Reaction that will have the message appended.
     * @return
     *   a new Reaction.
     */
    def appendMessage(msg: Message)(r: Reaction): Reaction = appendMessages(Seq(msg))(r)

    /**
     * Append some specific Messages to a Reaction.
     * @param msgs
     *   the messages to be appended.
     * @param r
     *   the Reaction that will have the messages appended.
     * @return
     *   a new Reaction.
     */
    def appendMessages(msgs: Seq[Message])(r: Reaction): Reaction =
      s => {
        val (s1, m1) = r(s)
        (s1, m1 ++ msgs)
      }

    /**
     * Create a Reaction from a function [[S]] => [[S]] and some messages.
     * @param f
     *   the function.
     * @param messages
     *   the messages.
     * @return
     *   A new Reaction.
     */
    def apply(f: S => S, messages: Message*): Reaction = create(f, messages)

    /**
     * Combine two Reactions.
     * @param a
     *   a Reaction.
     * @param b
     *   other Reaction.
     * @return
     *   The new Reaction.
     */
    def combine(a: Reaction, b: Reaction): Reaction = a.flatMap(_ => b)

    /**
     * Create a Reaction from a function [[S]] => [[S]] and some messages.
     * @param f
     *   the function.
     * @param messages
     *   the messages of the Reactions.
     * @return
     *   a new Reaction with the settings chosen before.
     */
    def create(f: S => S, messages: Seq[Message]): Reaction = s => (f(s), messages)

    /**
     * Create a Reaction empty:
     *   - no message.
     *   - function identity [[S]] => [[S]]
     * @return
     *   the Reaction.
     */
    def empty: Reaction = create(identity[S], Seq.empty)

    /**
     * The flatmap implemented for Reactions.
     * @param g
     *   a function [[S]] to Reaction.
     * @param f
     *   a Reaction.
     * @return
     *   a new Reaction.
     */
    def flatMap(g: S => Reaction)(f: Reaction): Reaction = { s =>
      val (s1, m1) = f(s)
      val (s2, m2) = g(s1)(s1)
      (s2, m1 ++ m2)
    }

    /**
     * The map implemented for Reactions.
     * @param g
     *   a function [[S]] to Reaction.
     * @param f
     *   a Reaction.
     * @return
     *   a new Reaction.
     */
    def map(g: S => S)(f: Reaction): Reaction =
      s => {
        val (s1, m1) = f(s)
        (g(s1), m1)
      }

    /**
     * Add messages to an empty Reaction.
     * @param msgs
     *   messages to append to Reaction.
     * @return
     *   The Reaction.
     */
    def messages(msgs: Message*): Reaction = appendMessages(msgs)(empty)

    /**
     * A Reaction created with:
     *   - a function [[S]] => [[S]] with any state to a specific State.
     *   - empty messages.
     * @param s
     *   the State provided from any state.
     * @return
     *   a new Reaction.
     */
    def pure(s: S): Reaction = create(_ => s, Seq.empty)

  }

  /**
   * An object used to create the Reaction part composed solely by the function [[S]] => [[S]].
   */
  object Update {

    /**
     * An Update [[U]] is the function [[S]] => [[S]].
     */
    private type U = S => S

    /**
     * Concatenate more [[U]].
     * @param fs
     *   all the [[U]] to concatenate.
     * @return
     *   a [[U]] that is the reduction of [[U]] cited above, function identity of [[S]] if [[U]] are
     *   not presents.
     */
    def apply(fs: U*): U = fs.reduceOption(_ andThen _) getOrElse identity[S]

    /**
     * The [[U]] empty value.
     * @return
     *   the identity of [[S]].
     */
    def empty: U = identity[S]
  }
}
