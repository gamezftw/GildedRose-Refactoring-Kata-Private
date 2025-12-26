package com.gildedrose.domain

class Sulfuras(s: SellInState)
    extends DomainItem("Sulfuras, Hand of Ragnaros", s, 80) {

  private final val internalMax = 80

  override val maxQuality = internalMax

  override def updateSellIn(): Unit = {}

  override def degrade(): Unit = {}

  override def clampedQuality(potentialQuality: Int): Int =
    internalMax
}
