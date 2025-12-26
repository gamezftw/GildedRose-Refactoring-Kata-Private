package com.gildedrose.domain

import com.gildedrose.constants._

case class ConjuredManaCake(s: SellInState, q: Int)
    extends DomainItem(ItemConstants.ConjuredMakaCake, s, q) {

  protected override val degradeValue: Int = 2

  protected def copy(
      name: String,
      sellIn: SellInState,
      quality: Int
  ): DomainItem = ConjuredManaCake(sellIn, quality)
}
