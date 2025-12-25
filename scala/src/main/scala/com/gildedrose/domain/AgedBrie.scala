package com.gildedrose.domain

class AgedBrie(s: Int, q: Int) extends DomainItem("Aged Brie", s, q) {

  override def getPotentialQuality =
    quality + 1

}
