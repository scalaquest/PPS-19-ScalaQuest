package io.github.scalaquest.examples.dragonquest

import io.github.scalaquest.core.model.ItemDescription.dsl.{d, i}
import model.{GenericItem}

object Items {

  import model._

  def allItems: Set[I] = Set()

  def basiliskTooth: GenericItem =
    GenericItem.withGenBehavior(
      i(d("basilisk"), "tooth"),
      {
        ???
      }
    )

  def sortingHat: Chest = Chest.createUnlocked(
    i(d("old", "sorting"), "hat"),
    Set(gryffindorSword)
  )


  def stone: GenericItem =
    GenericItem.withGenBehavior(
      i(d("little"), "stone"), {
        ???
      }
    )

  def ginny: GenericItem =
    GenericItem.withGenBehavior(
      i(d("weasley"), "ginny"), {
        ???
      }
    )

  def tomDiary: GenericItem =
    GenericItem.withGenBehavior(
      i(d("tom"), "diary"), {
        ???
      }
    )

  def tom: GenericItem =
    GenericItem.withGenBehavior(
      i("tom"), {
        ???
      }
    )

  def gryffindorSword: GenericItem =
    GenericItem.withGenBehavior(
      i(d("gryffindor"), "sword"), {
        //case (Actions.)
      }
    )

  def basilisk: GenericItem =
    GenericItem.withGenBehavior(
      i(d("terrible"), "basilisk"),
      {
        case (Actions.Attack, _, Some(i), _) if i == gryffindorSword =>
          CustomReactions.killBasilisk
        case (_, _, _, _) =>
          CustomReactions.killedByBasilisk
      }
    )

}
