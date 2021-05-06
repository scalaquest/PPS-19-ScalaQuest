/*
 * Copyright (c) 2021 ScalaQuest Team.
 *
 * This file is part of ScalaQuest, and is distributed under the terms of the MIT License as
 * described in the file LICENSE in the ScalaQuest distribution's top directory.
 */

package io.github.scalaquest.core.pipeline.interpreter

import io.github.scalaquest.core.model.{ItemRef, Model}

/**
 * An utility to extract an [[Model.Item]], given its [[ItemRef]]. The [[RefToItem::unapply()]]
 * functionality is exploited, as this utility is used in match cases.
 * @tparam I
 *   The concrete type of the [[Model.Item]] in use. It should be a subtype ofderived from
 *   [[Model.Item]].
 */
trait RefToItem[I] {
  def unapply(ref: ItemRef): Option[I]
}

/**
 * Companion object for the [[RefToItem]] trait. It exposes an [[RefToItem::apply()]] to build the
 * [[RefToItem]] instance from a dictionary of [[Model.Item]] s.
 */
object RefToItem {

  /**
   * Used to build the [[RefToItem]] instance from a dictionary of [[Model.Item]], making
   * transparent the presence of it during its use.
   * @param model
   *   The concrete instance of the [[Model]] in use.
   * @param itemsDict
   *   A [[Map]] that indicates the [[ItemRef]] of each ot the [[Model.Item]] in use. The
   *   [[Model.Item]] type must be derived from the `model` passed as parameter.
   * @tparam M
   *   The concrete type of the [[Model]] in use.
   * @return
   *   An instance of [[RefToItem]], using transparently the given item dictionary to match
   *   [[ItemRef]] - [[Model.Item]] links.
   */
  def apply[M <: Model](model: M)(itemsDict: Map[ItemRef, model.I]): RefToItem[model.I] = {
    case class SimpleRefToItem(itemsDict: Map[ItemRef, model.I]) extends RefToItem[model.I] {
      override def unapply(ref: ItemRef): Option[model.I] = itemsDict get ref
    }
    SimpleRefToItem(itemsDict)
  }
}
