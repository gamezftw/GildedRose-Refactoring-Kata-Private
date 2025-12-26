package com.gildedrose.domain

class AgedBrie(s: SellInState, q: Int) extends DomainItem("Aged Brie", s, q) {

  protected override def getPotentialQuality =
    quality + 1

}
