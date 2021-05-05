/*
 * Copyright (c) 2021 ScalaQuest Team.
 *
 * This file is part of ScalaQuest, and is distributed under the terms of the MIT License as
 * described in the file LICENSE in the ScalaQuest distribution's top directory.
 */

package io.github.scalaquest.core.parsing

package object engine {
  type Library = BaseLibrary with TuProlog

  type Theory = BaseTheory with TuPrologConverter
}
