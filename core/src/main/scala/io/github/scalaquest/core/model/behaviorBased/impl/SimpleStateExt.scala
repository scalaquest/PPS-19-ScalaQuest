package io.github.scalaquest.core.model.behaviorBased.impl

import io.github.scalaquest.core.dictionary.verbs.VerbPrep
import io.github.scalaquest.core.model._
import monocle.Lens
import monocle.macros.GenLens

/**
 * Extension for the model. Adds a base implementation of the [[Model.State]].
 */
trait SimpleStateExt extends Model {

  override type S = SimpleState

  case class SimpleState(
    actions: Map[VerbPrep, Action],
    rooms: Map[RoomRef, RM],
    items: Map[ItemRef, I],
    ground: G,
    _bag: Set[ItemRef],
    _location: RoomRef,
    messages: Seq[Message],
    ended: Boolean = false
  ) extends State {
    override def bag: Set[I]  = _bag.flatMap(items.get)
    override def location: RM = rooms(_location)
  }

  override def roomsLens: Lens[S, Map[RoomRef, RM]] = GenLens[S](_.rooms)
  override def itemsLens: Lens[S, Map[ItemRef, I]]  = GenLens[S](_.items)
  override def messageLens: Lens[S, Seq[Message]]   = GenLens[S](_.messages)
  override def matchEndedLens: Lens[S, Boolean]     = GenLens[S](_.ended)
  override def bagLens: Lens[S, Set[ItemRef]]       = GenLens[S](_._bag)
  override def locationLens: Lens[S, RoomRef]       = GenLens[S](_._location)
}
