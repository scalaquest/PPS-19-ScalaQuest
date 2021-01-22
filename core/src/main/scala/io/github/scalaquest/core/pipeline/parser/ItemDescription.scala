package io.github.scalaquest.core.pipeline.parser

sealed trait ItemDescription
final case class BaseItem(name: String)                                   extends ItemDescription
final case class DecoratedItem(decoration: String, item: ItemDescription) extends ItemDescription
