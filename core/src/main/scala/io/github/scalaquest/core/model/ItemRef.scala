/*
 * Copyright (c) 2021 ScalaQuest Team.
 *
 * This file is part of ScalaQuest, and is distributed under the terms of the MIT License as
 * described in the file LICENSE in the ScalaQuest distribution's top directory.
 */

package io.github.scalaquest.core.model

/**
 * A unique identifier for a [[Model.Item]].
 */
trait ItemRef

/**
 * Companion object for ItemRef. Exposes a factory to build the ItemRef based on an
 * [[ItemDescription]].
 */
object ItemRef {

  /**
   * An implementation ItemRef with String.
   * @param id
   *   the Unifier Identifier for ItemRef.
   */
  private final case class StringItemRef(id: String) extends ItemRef

  /**
   * Factory to build the [[ItemRef]] based on an the mkString of [[ItemDescription]].
   * @param itemDescription
   *   the ItemDescription.
   * @return
   *   the referent to the ItemDescription.
   */
  def apply(itemDescription: ItemDescription): ItemRef = StringItemRef(itemDescription.mkString)
}
