package io.github.scalaquest.core.model.common.items

import io.github.scalaquest.core.model.common.items.std.{Door, GenericItem, Key}

/**
 * This is a mixable part of the model, that adds some implemented common items to the model.
 */
trait StdCommonItems extends StdCommonItemsBase with Door with GenericItem with Key {}
