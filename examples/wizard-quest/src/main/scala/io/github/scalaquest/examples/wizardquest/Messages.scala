package io.github.scalaquest.examples.wizardquest

import io.github.scalaquest.core.model.Message

/**
 * Custom messages required by the example.
 */
object Messages {
  case object BasiliskKilled         extends Message
  case object ToothSpawned           extends Message
  case object BasiliskMovedToChamber extends Message
  case object EscapingFromBasilisk   extends Message
  case object KilledByBasilisk       extends Message
  case object KilledByTom            extends Message
  case object SwordShown             extends Message
}
