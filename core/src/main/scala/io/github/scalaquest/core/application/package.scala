/*
 * Copyright (c) 2021 ScalaQuest Team.
 *
 * This file is part of ScalaQuest, and is distributed under the terms of the MIT License as
 * described in the file LICENSE in the ScalaQuest distribution's top directory.
 */

package io.github.scalaquest.core

/**
 * This package provides some mixins to allow to create a `Pipeline` in a simple way. The usage of
 * these constructs makes it easy to embed a `Pipeline` into an application.
 *
 * An example of its usage can be the following:
 *
 * {{{
 *   object Main extends App
 *     with DefaultPipelineProvider[SimpleModel.type]
 *     with DictionaryProvider[SimpleModel.type] {
 *
 *     override def verbs = ???
 *
 *     override def items = ???
 *
 *     override val model = SimpleModel
 *
 *     var state: S = ???
 *
 *     // Here you can use the pipeline
 *     while(true) {
 *       val cmd = readInput()
 *       val res = makePipeline(state).run(cmd)
 *
 *       res match {
 *         case Left(error) => println(error)
 *         case Right((updatedState, messages)) =>
 *           state = updatedState
 *           println(messages)
 *       }
 *     }
 *   }
 * }}}
 */
package object application {}
