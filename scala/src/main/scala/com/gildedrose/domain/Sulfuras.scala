package com.gildedrose.domain

class Sulfuras(s: SellInState)
    extends DomainItem("Sulfuras, Hand of Ragnaros", s, 80) {

  private final val internalMax = 80

  protected override val maxQuality = internalMax

  protected override def updateSellIn(): Unit = {}

  protected override def degrade(): Unit = {}

  protected override def clampedQuality(potentialQuality: Int): Int =
    internalMax
}
