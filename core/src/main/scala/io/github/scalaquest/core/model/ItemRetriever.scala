package io.github.scalaquest.core.model

trait ItemRef

trait ItemRetriever {
  def unapply(ref: ItemRef)(implicit model: Model): Option[model.I]
}

object ItemRetriever {

  def apply(implicit model: Model): ItemRetriever = {
    new ItemRetriever {
      override def unapply(ref: ItemRef)(implicit model: Model): Option[model.I] = None
    }
  }
}
