/*
 * Copyright (c) 2021 ScalaQuest Team.
 *
 * This file is part of ScalaQuest, and is distributed under the terms of the MIT License as
 * described in the file LICENSE in the ScalaQuest distribution's top directory.
 */

package io.github.scalaquest.core.model.behaviorBased.commons.actioning

import io.github.scalaquest.core.model.{Action, Direction}

/**
 * A set of ready-to-use <b>Actions</b>, used into various parts of the Common package.
 */
object CActions {
  case object Take                    extends Action
  case object Open                    extends Action
  case object Close                   extends Action
  case object Enter                   extends Action
  case object Eat                     extends Action
  case object Inspect                 extends Action
  case object InspectBag              extends Action
  case class Go(direction: Direction) extends Action
}
