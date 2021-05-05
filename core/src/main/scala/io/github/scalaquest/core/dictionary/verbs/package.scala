/*
 * Copyright (c) 2021 ScalaQuest Team.
 *
 * This file is part of ScalaQuest, and is distributed under the terms of the MIT License as
 * described in the file LICENSE in the ScalaQuest distribution's top directory.
 */

package io.github.scalaquest.core.dictionary

import io.github.scalaquest.core.parsing.scalog.dsl.{CompoundBuilder, stringToAtom}

package object verbs {

  private[verbs] val verb = CompoundBuilder("verb").constructor

  type Verb = BaseVerb with ClauseOps with Meaning

  type VerbC = BaseVerb with ClauseOps

  type VerbPrep = (String, Option[String])
}
