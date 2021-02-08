package io.github.scalaquest.core.model

/**
 * A base representation of a single 'move' into the match. To be significant, could be associated
 * to one or more [[Model.Item]].
 */
trait Action

/**
 * Companion object for the [[Action]] trait, including some commonly used actions.
 */
object Action {

  /**
   * Some commonly used [[Action]] s.
   */
  object Common {
    case object Take                    extends Action
    case object Open                    extends Action
    case object Close                   extends Action
    case object Enter                   extends Action
    case object Eat                     extends Action
    case object Inspect                 extends Action
    case class Go(direction: Direction) extends Action
  }
}
