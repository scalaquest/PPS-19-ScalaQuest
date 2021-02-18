package io.github.scalaquest.core.model.behaviorBased.simple.impl

import io.github.scalaquest.core.model.{ItemDescription, ItemRef, Message, RoomRef}
import io.github.scalaquest.core.model.behaviorBased.simple.SimpleModel
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class ReactionUtilsExtTest extends AnyWordSpec with Matchers {

  case object testMessage  extends Message
  case object winMessage   extends Message
  case object loseMessage  extends Message
  case object bonusMessage extends Message

  def state: SimpleModel.S =
    SimpleModel.State(
      actions = Map.empty,
      rooms = Map.empty,
      items = Map.empty,
      location = RoomRef("1")
    )

  def item1: SimpleModel.I =
    SimpleModel.SimpleGenericItem(ItemDescription("item"), ItemRef(ItemDescription("item")))

  def item2: SimpleModel.I =
    SimpleModel.SimpleGenericItem(ItemDescription("item2"), ItemRef(ItemDescription("item2")))

  "An update" when {
    import SimpleModel.Update
    "empty" should {
      "not produce changes in a state" in {
        Update.empty(state) shouldBe state
      }
    }
    "created with many functions" should {
      "apply them in order" in {
        Update(
          SimpleModel.bagLens.modify(x => x + item1),
          SimpleModel.bagLens.modify(x => x - item1)
        )(state).bag should contain(item1)
      }
    }
  }

  import SimpleModel.Reaction
  "A reaction" when {
    "empty" should {
      "not produce changes both in state and messages" in {
        Reaction.empty(state) shouldBe (state -> Seq.empty)
      }
    }
    "has messages" should {
      "produce messages in output" in {
        Reaction.messages(testMessage, testMessage)(state) shouldBe (state -> Seq(
          testMessage,
          testMessage
        ))
      }
    }
    "is not empty" should {
      "modify the state and add the messages" in {
        Reaction(
          SimpleModel.matchEndedLens.set(true),
          testMessage
        )(state) shouldBe (state.copy(ended = true) -> Seq(testMessage))
      }
    }
  }
  "Combining reactions" should {
    "apply them sequentially" in {
      Reaction.combine(
        Reaction(
          SimpleModel.matchEndedLens.set(true)
        ),
        Reaction(s => if (s.ended) SimpleModel.bagLens.modify(x => x + item1)(s) else s)
      )(state) shouldBe state.copy(ended = true, _bag = Set(item1))
    }
  }
  "Mapping reactions" should {
    "apply an update to a given reaction" in {
      val r      = Reaction(SimpleModel.bagLens.modify(x => x + item1))
      val update = SimpleModel.bagLens.modify(x => x + item1)
      Reaction.map(update)(r)(state)._1.bag should (contain(item1) and contain(item2))
    }
  }
  "Reactions flatMap" should {
    "allow for sequential reaction combination" in {
      def endGame(win: Boolean): Reaction =
        Reaction(
          SimpleModel.matchEndedLens.set(true),
          if (win) winMessage else loseMessage
        )

      def winCond(r: Reaction): Reaction =
        r.flatMap(s => endGame(s.bag contains item1))
          .flatMap(s =>
            if (s.bag contains item2)
              Reaction.messages(bonusMessage)
            else Reaction.empty
          )

      winCond(Reaction(SimpleModel.bagLens.modify(x => x + Set(item1, item2))))(state) shouldBe
        (state.copy(ended = true, _bag = Set(item1)) -> Seq(winMessage, bonusMessage))

      winCond(Reaction.empty)(state) shouldBe (state.copy(ended = true) -> Seq(loseMessage))
    }
    "allow for for comprehensions" in {

      val reaction = for {
        s0 <- Reaction.empty
        s1 <-
          if (s0.bag contains item1)
            Reaction(
              SimpleModel.matchEndedLens.set(true),
              winMessage
            )
          else
            Reaction.empty
        s2 <-
          if (s1.bag contains item2) Reaction.messages(bonusMessage)
          else Reaction.empty
      } yield s2

      reaction(state) shouldBe (state                         -> Seq.empty)
      reaction(state.copy(_bag = Set(item1))) shouldBe (state -> Seq(winMessage))
      reaction(state.copy(_bag = Set(item2))) shouldBe (state -> Seq(bonusMessage))
      reaction(state.copy(_bag = Set(item1, item2))) shouldBe (state -> Seq(
        winMessage,
        bonusMessage
      ))
    }
  }

}
