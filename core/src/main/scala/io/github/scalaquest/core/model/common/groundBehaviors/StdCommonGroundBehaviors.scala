package io.github.scalaquest.core.model.common.groundBehaviors

import io.github.scalaquest.core.model.Model
import io.github.scalaquest.core.model.common.CommonBase
import io.github.scalaquest.core.model.common.groundBehaviors.std.Navigation

/**
 * When mixed into a [[Model]], it enables the implementation for the common behaviors provided by
 * ScalaQuest Core. It requires the storyteller to implement all the required [[monocle.Lens]], used
 * by the implementation to access and re-generate the concrete [[Model.State]].
 */
trait StdCommonGroundBehaviors extends CommonBase with Navigation
