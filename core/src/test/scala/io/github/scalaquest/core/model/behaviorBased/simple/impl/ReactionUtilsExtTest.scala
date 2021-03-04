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
    SimpleModel.SimpleGenericItem(ItemDescription("item1"), ItemRef(ItemDescription("item1")))

  def item2: SimpleModel.I =
    SimpleModel.SimpleGenericItem(ItemDescription("item2"), ItemRef(ItemDescription("item2")))

  "An update" when {
    import SimpleModel.Update
    "empty" should {
      "not produce changes in a state" in {
        Update.empty(state) shouldBe state
      }
      "be created without passing anything" in {
        Update()(state) shouldBe state
      }
    }
    "created with many functions" should {
      "apply them in order" in {
        Update(
          SimpleModel.bagLens.modify(_ + item1.ref),
          SimpleModel.bagLens.modify(_ - item1.ref)
        )(state).bag should not contain item1
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
    "pure" should {
      "not produce changes in messages and change state to the given State" in {
        Reaction.pure(state)(state) shouldBe (state -> Seq.empty)
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
        Reaction(s => if (s.ended) SimpleModel.bagLens.modify(_ + item1.ref)(s) else s)
      )(state) shouldBe (state.copy(ended = true, _bag = Set(item1.ref)) -> Seq.empty)
    }
  }
  "Mapping reactions" should {
    "apply an update to a given reaction" in {
      val r            = Reaction(SimpleModel.bagLens.modify(_ + item1.ref))
      val update       = SimpleModel.bagLens.modify(_ + item2.ref)
      val updatedState = r.map(update)(state)
      updatedState._1._bag should (contain(item1.ref) and contain(item2.ref))
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
        r.flatMap(s => endGame(s._bag contains item1.ref))
          .flatMap(s =>
            if (s._bag contains item2.ref)
              Reaction.messages(bonusMessage)
            else Reaction.empty
          )

      winCond(Reaction(SimpleModel.bagLens.modify(_ ++ Set(item1.ref, item2.ref))))(state) shouldBe
        (state
          .copy(ended = true, _bag = Set(item1.ref, item2.ref)) -> Seq(winMessage, bonusMessage))

      winCond(Reaction.empty)(state) shouldBe (state.copy(ended = true) -> Seq(loseMessage))
    }
    "allow for for comprehensions" in {

      val reaction = for {
        s0 <- Reaction.empty
        s1 <-
          if (s0._bag contains item1.ref)
            Reaction(
              SimpleModel.matchEndedLens.set(true),
              winMessage
            )
          else
            Reaction.empty
        s2 <-
          if (s1._bag contains item2.ref) Reaction.messages(bonusMessage)
          else Reaction.empty
      } yield s2

      reaction(state) shouldBe (state -> Seq.empty)
      reaction(state.copy(_bag = Set(item1.ref))) shouldBe
        (state.copy(ended = true, _bag = Set(item1.ref)) -> Seq(winMessage))
      reaction(state.copy(_bag = Set(item2.ref))) shouldBe
        (state.copy(_bag = Set(item2.ref)) -> Seq(bonusMessage))
      reaction(state.copy(_bag = Set(item1.ref, item2.ref))) shouldBe
        (state.copy(_bag = Set(item1.ref, item2.ref), ended = true) -> Seq(
          winMessage,
          bonusMessage
        ))
    }
  }

}
