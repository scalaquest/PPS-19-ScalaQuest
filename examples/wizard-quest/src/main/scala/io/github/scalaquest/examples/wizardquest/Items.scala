/*
 * Copyright (c) 2021 ScalaQuest Team.
 *
 * This file is part of ScalaQuest, and is distributed under the terms of the MIT License as
 * described in the file LICENSE in the ScalaQuest distribution's top directory.
 */

package io.github.scalaquest.examples.wizardquest

import io.github.scalaquest.core.model.ItemDescription.dsl.{d, i}
import model.{CReactions, GenericItem, Takeable}

/**
 * The items required by the example.
 */
object Items {

  def allTheItems: Set[I] =
    Set(
      basilisk,
      basiliskTooth,
      sortingHat,
      tom,
      tomDiary,
      ginny,
      gryffindorSword,
      stone
    )

  def basiliskTooth: GenericItem =
    GenericItem.withSingleBehavior(
      i(d("basilisk"), "tooth"),
      Takeable.builder()
    )

  def sortingHat: GenericItem =
    GenericItem.withGenBehavior(
      i(d("old", "sorting"), "hat"),
      {
        case (Actions.LookInside, _, _) if !Reactions.isInitState && !Reactions.swordShown =>
          Reactions.showTheSword
      }
    )

  def stone: GenericItem =
    GenericItem.withGenBehavior(
      i(d("little"), "stone"),
      { case (Actions.Throw, None, _) => Reactions.basiliskMovesBack }
    )

  def ginny: GenericItem = GenericItem.empty(i(d("weasley"), "ginny"))

  def tomDiary: GenericItem =
    GenericItem.withGenBehavior(
      i(d("tom"), "diary"),
      {
        case (Actions.Attack, Some(i), _) if i == basiliskTooth => CReactions.finishGame(true)
      }
    )

  def tom: GenericItem =
    GenericItem.withGenBehavior(
      i("tom"),
      { case (_, _, _) => Reactions.getKilledByTom }
    )

  def gryffindorSword: GenericItem =
    GenericItem.withSingleBehavior(
      i(d("gryffindor"), "sword"),
      Takeable.builder()
    )

  def basilisk: GenericItem =
    GenericItem.withGenBehavior(
      i(d("terrible"), "basilisk"),
      {
        case (Actions.Attack, Some(i), _) if i == gryffindorSword => Reactions.killTheBasilisk
        case (_, _, _)                                            => Reactions.getKilledByBasilisk
      }
    )
}
