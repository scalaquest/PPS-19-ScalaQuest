package io.github.scalaquest.core.application

import io.github.scalaquest.core.model.behaviorBased.simple.SimpleModel
import io.github.scalaquest.core.parsing.engine.Engine
import io.github.scalaquest.core.parsing.engine.exceptions.InvalidTheoryException
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class PipelineProviderTest extends AnyWordSpec with Matchers {
  val theory: String         = "some rng string"
  var theories: List[String] = List()

  val pipelineProvider = new PipelineProvider[SimpleModel.type] {

    override def baseTheory: String = theory

    override def makeEngine(theory: String): Engine = {
      theories = theories :+ theory
      super.makeEngine(theory)
    }

    override val model: SimpleModel.type = SimpleModel
  }

  "Pipeline provider" should {
    "pass the given theory to the pipeline" in {
      assertThrows[InvalidTheoryException] {
        pipelineProvider.makePipeline
      }
      theories shouldBe List(theory)
    }
  }
}
