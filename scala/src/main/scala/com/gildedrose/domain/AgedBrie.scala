package com.gildedrose.domain

import com.gildedrose.constants._

class AgedBrie(s: SellInState, q: Int)
    extends DomainItem(ItemConstants.AgedBrie, s, q) {

  protected override def getPotentialQuality =
    quality + 1

}
