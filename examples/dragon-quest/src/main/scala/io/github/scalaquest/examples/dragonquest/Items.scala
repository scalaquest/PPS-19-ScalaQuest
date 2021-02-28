package io.github.scalaquest.examples.dragonquest

import io.github.scalaquest.core.model.ItemDescription.dsl.{d, i}
import model.{GenericItem}

object Items {
  def allItems: Set[I] = Set()

  def sortingHat: GenericItem =
    GenericItem.withGenBehavior(
      i(d("old", "sorting"), "hat"), {
        ???
      }
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
        ???
      }
    )

  def basilisk: GenericItem =
    GenericItem.withGenBehavior(
      i(d("terrible"), "basilisk"),
      {
        case (Actions.Attack, _, None, _) =>
          CustomReactions.attackBasilisk
        case (Actions.Attack, _, Some(i), _) if i == gryffindorSword =>
          CustomReactions.killBasilisk
      }
    )

}
