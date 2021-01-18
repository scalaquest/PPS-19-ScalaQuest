package io.github.scalaquest.core.model.common.items

import io.github.scalaquest.core.model.Model
import io.github.scalaquest.core.model.common.items.std.{Door, Food, GenericItem, Key}

/**
 * When mixed into a [[Model]], it enables the implementation for the common items provided by
 * ScalaQuest Core.
 */
trait StdCommonItems extends StdCommonItemsBase with Door with GenericItem with Key with Food
