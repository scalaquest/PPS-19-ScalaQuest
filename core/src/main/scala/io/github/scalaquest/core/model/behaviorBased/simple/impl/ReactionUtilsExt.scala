/*
 * Copyright (c) 2021 ScalaQuest Team.
 *
 * This file is part of ScalaQuest, and is distributed under the terms of the MIT License as
 * described in the file LICENSE in the ScalaQuest distribution's top directory.
 */

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
   *   The wrapped <b>Reaction</b>.
   */
  implicit class EnhancedReaction(r: Reaction) {

    /**
     * Use <b>Reaction.map</b> method with this and an other <b>Reaction</b>.
     * @param g
     *   The other <b>Reaction</b>.
     * @return
     *   A new <b>Reaction</b> that is the <b>Reaction::map</b> of the two reactions.
     */
    def map(g: S => S): Reaction = Reaction.map(g)(r)

    /**
     * Use <b>Reaction::flatmap</b> method with this and an other <b>Reaction</b>.
     * @param g
     *   The other <b>Reaction</b>.
     * @return
     *   A new <b>Reaction</b> that is the <b>Reaction::flatmap</b> of the two reactions.
     */
    def flatMap(g: S => Reaction): Reaction = Reaction.flatMap(g)(r)
  }

  /**
   * Object with some methods to work on <b>Reaction</b>.
   */
  object Reaction {

    /**
     * Append a specific [[Message]] to a <b>Reaction</b>.
     * @param msg
     *   The <b>message</b> to be appended.
     * @param r
     *   The <b>Reaction</b> that will have the message appended.
     * @return
     *   A new <b>Reaction</b> with the <b>message</b> appended.
     */
    def appendMessage(msg: Message)(r: Reaction): Reaction = appendMessages(Seq(msg))(r)

    /**
     * Append some specific [[Message]] s to a <b>Reaction</b>.
     * @param msgs
     *   The <b>messages</b> to be appended.
     * @param r
     *   The <b>Reaction</b> that will have the messages appended.
     * @return
     *   A new <b>Reaction</b> with the <b>messages</b> appended.
     */
    def appendMessages(msgs: Seq[Message])(r: Reaction): Reaction =
      s => {
        val (s1, m1) = r(s)
        (s1, m1 ++ msgs)
      }

    /**
     * Create a <b>Reaction</b> from a function <b>State => State</b> and some <b>messages</b>.
     * @param f
     *   A function <b>State => State</b> that giving a State provide an updated on it.
     * @param messages
     *   The <b>messages</b> of <b>Reaction</b>.
     * @return
     *   A new <b>Reaction</b> with as function <b>f</b> and for messages <b>messages</b>.
     */
    def apply(f: S => S, messages: Message*): Reaction = create(f, messages)

    /**
     * Combine two <b>Reaction</b>s using the <b>Reaction::flatmap</b>.
     * @param a
     *   A <b>Reaction</b>.
     * @param b
     *   An other <b>Reaction</b>.
     * @return
     *   The new <b>Reaction</b>, result combining of the precedent ones.
     */
    def combine(a: Reaction, b: Reaction): Reaction = a.flatMap(_ => b)

    /**
     * Create a <b>Reaction</b> from a function <b>State => State</b> and some <b>messages</b>.
     * @param f
     *   A function <b>State => State</b> that giving a State provide an updated on it.
     * @param messages
     *   The <b>messages</b> of <b>Reaction</b>.
     * @return
     *   The new <b>Reaction</b> as function <b>f</b> and for messages <b>messages</b>.
     */
    def create(f: S => S, messages: Seq[Message]): Reaction = s => (f(s), messages)

    /**
     * Create an <b>empty Reaction</b>:
     *   - no message.
     *   - function identity [[S]] => [[S]]
     * @return
     *   The empty <b>Reaction</b>.
     */
    def empty: Reaction = create(identity[S], Seq.empty)

    /**
     * The <b>flatmap</b> implemented for <b>Reactions</b>:
     *   - product a <b>State</b> applying the two <b>Reactions</b>
     *   - concatenate as message the two <b>messages</b> of the Reactions.
     * @param g
     *   A function <b>State => Reaction</b>.
     * @param r
     *   A <b>Reaction</b>.
     * @return
     *   A new <b>Reaction</b> combining the function <b>g</b> and the reaction <b>r</b>.
     */
    def flatMap(g: S => Reaction)(r: Reaction): Reaction = { s =>
      val (s1, m1) = r(s)
      val (s2, m2) = g(s1)(s1)
      (s2, m1 ++ m2)
    }

    /**
     * The <b>map</b> implemented for <b>Reactions</b>:
     *   - product a <b>State</b> applying a <b>Reaction</b> on a function <b>State => State</b>
     *   - concatenate as message the two <b>messages</b> of the <b>Reactions</b>.
     * @param g
     *   A function <b>State => Reaction</b>.
     * @param r
     *   A <b>Reaction</b>.
     * @return
     *   A new <b>Reaction</b> combining the function <b>g</b> and the reaction <b>r</b>.
     */
    def map(g: S => S)(r: Reaction): Reaction =
      s => {
        val (s1, m1) = r(s)
        (g(s1), m1)
      }

    /**
     * Add <b>messages</b> to an <b>empty Reaction</b>.
     * @param msgs
     *   <b>Messages</b> to append to <b>Reaction</b>.
     * @return
     *   The <b>empty Reaction</b> with the <b>messages</b>.
     */
    def messages(msgs: Message*): Reaction = appendMessages(msgs)(empty)

    /**
     * A Reaction created with:
     *   - a function <b>S => S</b> with any state to a specific State.
     *   - empty messages.
     * @param s
     *   The State provided from any state.
     * @return
     *   A new <b>Reaction</b> as described above.
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
