package com.gildedrose.domain

import com.gildedrose.constants._

class AgedBrie(s: SellInState, q: Int)
    extends DomainItem(ItemConstants.AgedBrie, s, q) {

  protected override def getPotentialQuality =
    quality + 1

  protected def copy(
      name: String,
      sellIn: SellInState,
      quality: Int
  ): DomainItem = AgedBrie(sellIn, quality)
}
