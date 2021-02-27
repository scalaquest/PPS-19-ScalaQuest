package io.github.scalaquest.examples.dragonquest

import io.github.scalaquest.core.model.ItemDescription.dsl.{d, i}
import model.{GenericItem}

object Items {
  def allItems: Set[I] = Set()

  def tomDiary: GenericItem =
    GenericItem.withGenBehavior(
      i(d("tom's"), "diary"),
      {
        ???
      }
    )

  def tom: GenericItem =
    GenericItem.withGenBehavior(
      i("tom"),
      {
        ???
      }
    )

  def gryffindorSword: GenericItem =
    GenericItem.withGenBehavior(
      i(d("gryffindor"), "sword"),
      {
        ???
      }
    )

  def basilisk: GenericItem =
    GenericItem.withGenBehavior(
      i(d("terrible"), "basilisk"),
      {
        ???
      }
    )

}
