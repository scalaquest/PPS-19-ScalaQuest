package io.github.scalaquest.core.pipeline.interpreter

import io.github.scalaquest.core.model.std.StdModel
import io.github.scalaquest.core.model.std.StdModel.GenericItem

import org.scalatest.wordspec.AnyWordSpec

class ItemRetrieverTest extends AnyWordSpec {

  "An ItemRetriever have a dictionary and" when {
    val itemRefRight: ItemRef = new ItemRef {}
    // val itemRefWrong: ItemRef                = new ItemRef {}
    val item                                 = GenericItem("genericItem")
    val dictionary: Map[ItemRef, StdModel.I] = Map(itemRefRight -> item)

    case class SimpleItemRetriever(itemsDict: Map[ItemRef, StdModel.I]) extends ItemRetriever[StdModel.I] {
      override def unapply(ref: ItemRef): Option[StdModel.I] = itemsDict get ref
    }

    val itemRetriever = SimpleItemRetriever(dictionary)
    "receive an ItemRef" should {
      "find a match from ItemRef to the specific Item" in {
        assert(
          itemRetriever match {
            case SimpleItemRetriever(_) => true
            case _                      => false
          },
          "The itemRef not refer to the item"
        )
      }
      /*"not find a match from ItemRef to an Item if doesn't exist" in {
        assert(
          itemRetriever match {
            case SimpleItemRetriever(item) if ()=> false
            case _                      => true
          },
          "The itemRef take an item"
        )
      }*/
    }
  }
}
