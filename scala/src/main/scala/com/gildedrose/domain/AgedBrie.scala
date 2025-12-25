package com.gildedrose.domain

class AgedBrie(s: Int, q: Int) extends DomainItem("Aged Brie", s, q) {

  override def updateQuality(): Unit = {
    super.updateQuality()

  }

  override def getPotentialQuality =
    quality + 1

}
