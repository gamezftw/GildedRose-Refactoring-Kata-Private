package com.gildedrose.domain

import com.gildedrose.constants._

class Sulfuras(s: SellInState)
    extends DomainItem(ItemConstants.Sulfuras, s, 80) {

  private final val internalMax = 80

  protected override val maxQuality = internalMax

  protected override def updateSellIn(): Unit = {}

  protected override def degrade(): Unit = {}

  protected override def clampedQuality(potentialQuality: Int): Int =
    internalMax
}
