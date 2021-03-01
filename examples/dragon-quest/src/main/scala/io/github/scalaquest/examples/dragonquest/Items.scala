package io.github.scalaquest.examples.dragonquest

import io.github.scalaquest.core.model.ItemDescription.dsl.{d, i}
import model.{GenericItem, Chest, CReactions, Takeable}

object Items {

  def allTheItems: Set[I] = Set()

  def basiliskTooth: GenericItem =
    GenericItem.withSingleBehavior(
      i(d("basilisk"), "tooth"),
      Takeable.builder()
    )

  def sortingHat: Chest =
    Chest.createUnlocked(
      i(d("old", "sorting"), "hat"),
      Set(gryffindorSword)
    )

  def stone: GenericItem =
    GenericItem.withGenBehavior(
      i(d("little"), "stone"),
      { case (Actions.Throw, None, _) =>
        Reactions.moveBasiliskToChamber
      }
    )

  def ginny: GenericItem =
    GenericItem.withGenBehavior(
      i(d("weasley"), "ginny"),
      PartialFunction.empty
    )

  def tomDiary: GenericItem =
    GenericItem.withGenBehavior(
      i(d("tom"), "diary"),
      {
        case (Actions.Attack, Some(i), _) if i == basiliskTooth =>
          CReactions.finishGame(true)
      }
    )

  def tom: GenericItem =
    GenericItem.withGenBehavior(
      i("tom"),
      { case (_, _, _) =>
        Reactions.killedByTom
      }
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
        case (Actions.Attack, Some(i), _) if i == gryffindorSword =>
          Reactions.killBasilisk
        case (_, _, _) =>
          Reactions.killedByBasilisk
      }
    )

}
