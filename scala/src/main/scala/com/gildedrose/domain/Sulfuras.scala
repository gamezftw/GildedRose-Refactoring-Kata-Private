package com.gildedrose.domain

import com.gildedrose.constants._

case class Sulfuras(s: SellInState)
    extends DomainItem(ItemConstants.Sulfuras, s, 80) {

  private final val internalMax = 80

  protected override val maxQuality = internalMax

  protected override def updateSellIn: DomainItem = this

  protected override def degrade: DomainItem = this

  protected override def clampedQuality(potentialQuality: Int): Int =
    internalMax

  protected def copy(
      name: String,
      sellIn: SellInState,
      quality: Int
  ): DomainItem = Sulfuras(sellIn)
}
