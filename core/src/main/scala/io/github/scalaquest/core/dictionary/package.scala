/*
 * Copyright (c) 2021 ScalaQuest Team.
 *
 * This file is part of ScalaQuest, and is distributed under the terms of the MIT License as
 * described in the file LICENSE in the ScalaQuest distribution's top directory.
 */

package io.github.scalaquest.core

import io.github.scalaquest.core.model.Model

package object dictionary extends DictionaryImplicits {
  type Item = Model#Item
}
