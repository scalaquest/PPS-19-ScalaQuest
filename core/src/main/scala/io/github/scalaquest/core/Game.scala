/*
 * Copyright (c) 2021 ScalaQuest Team.
 *
 * This file is part of ScalaQuest, and is distributed under the terms of the MIT License as
 * described in the file LICENSE in the ScalaQuest distribution's top directory.
 */

package io.github.scalaquest.core

import io.github.scalaquest.core.model.{Message, Model}
import io.github.scalaquest.core.pipeline.Pipeline.PartialBuilder

/**
 * A wrapper for the main game iteration, that given an input textual command and a State, it tries
 * to interpret it and modify the state accordingly.
 * @param model
 *   The concrete type of the [[Model]] in use.
 */
abstract class Game[M <: Model](val model: M) {

  /**
   * The main game iteration. Given an input textual command and a State, it tries to interpret it
   * and modify the state accordingly.
   * @param input
   *   A textual command from the user.
   * @param state
   *   The current [[Model.State]] of the game.
   * @return
   *   [[Right]] with the updated state and the resulting message, if no errors have been occurred;
   *   [[Left]] with a string message error, instead.
   */
  def send(input: String)(state: model.S): Either[String, (model.S, Seq[Message])]
}

/**
 * Contains a default builder for [[Game]], using an external instance of Pipeline.
 */
object Game {

  class GameBuilder[M <: Model](val model: M) {

    /**
     * A partial implementation of [[Game]], using an external instance of Pipeline.
     */
    abstract class GameWithPipeline extends Game[model.type](model) {

      def partialBuilder: PartialBuilder[model.S, model.type]

      override def send(input: String)(state: model.S): Either[String, (model.S, Seq[Message])] =
        partialBuilder(state) run input
    }

    /**
     * Builds a default instance of [[Game]], using an external instance of Pipeline.
     */
    def build(
      _partialBuilder: PartialBuilder[model.S, model.type]
    ): Game[model.type] =
      new GameWithPipeline {
        override def partialBuilder: PartialBuilder[model.S, model.type] = _partialBuilder
      }
  }

  /**
   * Generates the default builder.
   */
  def builderFrom[M <: Model](model: M): GameBuilder[M] = new GameBuilder(model)
}
