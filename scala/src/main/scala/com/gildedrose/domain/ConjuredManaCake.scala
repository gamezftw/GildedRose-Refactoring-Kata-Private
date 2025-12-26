package com.gildedrose.domain

class ConjuredManaCake(s: SellInState, q: Int)
    extends DomainItem("Conjured Mana Cake", s, q) {

  protected override val degradeValue: Int = 2
}
