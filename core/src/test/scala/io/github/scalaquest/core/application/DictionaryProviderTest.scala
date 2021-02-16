package io.github.scalaquest.core.application

import io.github.scalaquest.core.dictionary.verbs.{Transitive, Verb}
import io.github.scalaquest.core.model.{Action, ItemDescription}
import io.github.scalaquest.core.model.behaviorBased.simple.SimpleModel
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class DictionaryProviderTest extends AnyWordSpec with Matchers {

  case object testAction extends Action
  val item: SimpleModel.GenericItem = SimpleModel.GenericItem(ItemDescription("item"))

  val dictionaryProvider: DictionaryProvider[SimpleModel.type] =
    new DictionaryProvider[SimpleModel.type] {
      override def verbs: Set[Verb] = Set(Transitive("eat", testAction))

      override def items: Set[I] = Set(item)

      override val model: SimpleModel.type = SimpleModel
    }
  "Base theory path" should {
    "be statically defined" in {
      dictionaryProvider.baseTheoryPath shouldBe "base.pl"
    }
  }
  "Dynamic verb generation" should {
    "depend on the verb set" in {
      dictionaryProvider.verbToAction shouldBe Map(
        ("eat", None) -> testAction
      )
    }
  }
  "Dynamic item generation" should {
    "depend on the item set" in {
      dictionaryProvider.refToItem shouldBe Map(
        item.ref -> item
      )
    }
  }

}
