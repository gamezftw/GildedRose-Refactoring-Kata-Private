package com.gildedrose.domain

import com.gildedrose.constants._

class ConjuredManaCake(s: SellInState, q: Int)
    extends DomainItem(ItemConstants.ConjuredMakaCake, s, q) {

  protected override val degradeValue: Int = 2
}
