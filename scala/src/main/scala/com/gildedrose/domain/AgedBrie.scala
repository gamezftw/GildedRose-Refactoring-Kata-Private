package com.gildedrose.domain

import com.gildedrose.constants._

case class AgedBrie(s: SellInState, q: Int)
    extends DomainItem(ItemConstants.AgedBrie, s, q) {

  protected override val degradeOperation: Int => Int => Int = x => y => x + y

  protected def clone(
      name: String,
      sellIn: SellInState,
      quality: Int
  ): DomainItem = AgedBrie(sellIn, quality)
}
