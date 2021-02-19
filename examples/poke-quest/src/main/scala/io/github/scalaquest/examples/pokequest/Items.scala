package io.github.scalaquest.examples.pokequest

import io.github.scalaquest.core.model.ItemDescription.dsl.{d, i}
import io.github.scalaquest.core.model.Direction
import io.github.scalaquest.examples.pokequest.Actions.{Play, Wake}
import model.{
  GenericItem,
  ItemBehavior,
  ItemTriggers,
  Update,
  locationRoomLens,
  roomDirectionsLens,
  roomItemsLens,
  Reactions,
  Reaction => ReactionFactory
}

object Items {

  def snorlax: GenericItem =
    GenericItem(
      i(d("sleeping"), "snorlax"),
      Seq(_ =>
        new ItemBehavior {

          override def triggers: model.ItemTriggers = {
            case (Wake, _, Some(i), _) if i == flute => wakeSnorlaxReaction
            case _                                   => Reactions.finishGame(false)
          }
        }
      )
    )

  def flute: GenericItem =
    GenericItem(
      i("pokeflute"),
      Seq(_ =>
        new ItemBehavior {

          override def triggers: ItemTriggers = { case (Play, _, _, s) =>
            if (s.location.items(s).contains(snorlax)) wakeSnorlaxReaction
            else ReactionFactory.messages(Pusher.FreePlayFlute)
          }
        }
      )
    )

  def wakeSnorlaxReaction: Reaction =
    ReactionFactory(
      Update(
        (locationRoomLens composeLens roomItemsLens).modify(_ - snorlax.ref),
        (locationRoomLens composeLens roomDirectionsLens).modify(
          _ + (Direction.North -> Geography.forest.ref)
        )
      ),
      Pusher.SnorlaxWoke
    )

  def allTheItems: Set[I] =
    Set(
      snorlax,
      flute
    )
}
