package com.inkenkun.x1.math.sandbox

import org.apache.commons.math3.distribution.BetaDistribution
import org.apache.commons.math3.random.RandomDataGenerator

import scala.collection.mutable

case class ThomsonSampling (arms: Int) {
  type S           = Double
  type F           = Double
  type Challenge   = (S, F)
  type Arm         = Int
  type Probability = Double

  val challenges: mutable.Buffer[Challenge] = (1 to arms).map(_ => (0d, 0d)).toBuffer

  def selectArm: (Arm, Probability) = {
    val p = challenges.map {
      case (s, f) => beta(s, f)
    }
    val arm = argmax(p)
    (arm, p(arm))
  }

  def addResult(arm: Arm, isSuccess: Boolean): Unit = {
    val s = if (isSuccess) 1d else 0d
    val f = if (isSuccess) 0d else 1d
    challenges(arm) = (challenges(arm)._1 + s, challenges(arm)._2 + f)
  }

  def getTrials: Seq[Double] =
    challenges.map(sf => sf._1 + sf._2)

  def getHitRate: Seq[Double] =
    challenges.map { case (s, f) =>
      val denominator = s + f
      if (denominator == 0) 0
      else s / denominator
    }

  private[sandbox]
  def beta(a: Double, b: Double): Double = {
    new BetaDistribution(a + 1.0, b + 1.0).sample()
  }
  private[sandbox]
  def argmax(xs: Seq[Double]): Int =
    if (xs.isEmpty) 0
    else xs.indices.maxBy(xs)

  private[sandbox]
  def betaSamplingN(a: Double, b: Double, n: Int): Seq[Double] = {
    val beta = new BetaDistribution(a, b)
    for (i <- 1 to n)
      yield beta.sample()
  }
}

object ThomsonSampling {
  val random = new RandomDataGenerator()

  def main (args: Array[String]): Unit = {
    val machines         = Seq(0.1, 0.3, 0.5, 0.8, 0.9)
    val thompsonSampling = ThomsonSampling(machines.size)

    val N = 1000    // 1,000回試行
    val y = new mutable.ArrayBuffer[Seq[Double]](N)
    val z = new mutable.ArrayBuffer[Seq[Double]](N)

    for (i <- 1 to N) {
      // アームを選択
      val (arm, prob) = thompsonSampling.selectArm
      // スロットを引く
      val isSuccess = hitOrDeviate(machines(arm))
      // 結果を加算
      thompsonSampling.addResult(arm, isSuccess)

      y += thompsonSampling.getHitRate
      z += thompsonSampling.getTrials
    }

    println(y)
    println(z)
  }

  def hitOrDeviate(prob: Double): Boolean =
    if (random.nextBinomial(1, prob) == 0) false
    else true
}