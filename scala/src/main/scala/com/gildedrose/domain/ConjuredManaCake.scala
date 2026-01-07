package com.gildedrose.domain

import com.gildedrose.constants._

case class ConjuredManaCake(s: SellInState, q: Int)
    extends DomainItem(ItemConstants.ConjuredMakaCake, s, q) {

  override val baseDegradeValue: Int = 2

  protected def clone(
      name: String,
      sellIn: SellInState,
      quality: Int
  ): DomainItem = ConjuredManaCake(sellIn, quality)
}
