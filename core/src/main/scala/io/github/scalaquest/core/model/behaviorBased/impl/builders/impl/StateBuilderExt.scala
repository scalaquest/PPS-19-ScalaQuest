package io.github.scalaquest.core.model.behaviorBased.impl.builders.impl

import io.github.scalaquest.core.dictionary.verbs.VerbPrep
import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import io.github.scalaquest.core.model.behaviorBased.impl.{SimpleGroundExt, SimpleStateExt}
import io.github.scalaquest.core.model.{Action, ItemRef, Message, RoomRef}

trait StateBuilderExt extends BehaviorBasedModel with SimpleGroundExt with SimpleStateExt {

  def stateBuilder(
    actions: Map[VerbPrep, Action],
    rooms: Map[RoomRef, RM],
    items: Map[ItemRef, I],
    ground: G = SimpleGround,
    bag: Set[ItemRef] = Set(),
    location: RoomRef,
    messages: Seq[Message] = Seq.empty,
    ended: Boolean = false
  ): SimpleState = SimpleState(actions, rooms, items, ground, bag, location, messages, ended)
}
