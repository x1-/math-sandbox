package com.inkenkun.x1.math.sandbox

import org.scalatest.{Matchers, WordSpec}

class ThomsonSamplingTest extends WordSpec with Matchers {
  "ThomsonSampling" should {
    "argmax" when {
      "return minimum index if duplicate maximun values like Seq(2d, 3d, 10d, 10d, 9d, 7d)" in {
        ThomsonSampling(0).argmax(Seq(2d, 3d, 10d, 10d, 9d, 7d)) shouldBe 2
      }
      "return minimum index if same values like Seq(2d, 2d, 2d, 2d, 2d, 2d)" in {
        ThomsonSampling(0).argmax(Seq(2d, 2d, 2d, 2d, 2d, 2d)) shouldBe 0
      }
      "return 0 if empty" in {
        ThomsonSampling(0).argmax(Seq.empty[Double]) shouldBe 0
      }
    }
  }
}
