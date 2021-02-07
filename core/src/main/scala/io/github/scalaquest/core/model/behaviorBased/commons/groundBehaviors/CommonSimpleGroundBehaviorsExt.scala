package io.github.scalaquest.core.model.behaviorBased.commons.groundBehaviors

import io.github.scalaquest.core.model.Model
import io.github.scalaquest.core.model.behaviorBased.commons.CommonBase
import io.github.scalaquest.core.model.behaviorBased.commons.groundBehaviors.impl.{
  SimpleInspectExt,
  SimpleNavigationExt,
  SimpleOrientateExt
}

/**
 * When mixed into a [[Model]], it enables the implementation for the common behaviors provided by
 * ScalaQuest Core. It requires the storyteller to implement all the required [[monocle.Lens]], used
 * by the implementation to access and re-generate the concrete [[Model.State]].
 */
trait CommonSimpleGroundBehaviorsExt
  extends CommonBase
  with SimpleNavigationExt
  with SimpleInspectExt
  with SimpleOrientateExt {

  def allSimpleCommonGroundBehaviors: Seq[GroundBehavior] =
    Seq(SimpleNavigation(), SimpleInspect(), SimpleOrientate())
}
