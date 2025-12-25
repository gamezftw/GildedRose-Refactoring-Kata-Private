package com.gildedrose.domain

class BackstagePasses(s: Int, q: Int)
    extends DomainItem("Backstage passes", s, q) {

  override def getPotentialQuality = {
    sellIn match {
      case x if x < 0  => 0
      case x if x < 6  => quality + 3
      case x if x < 11 => quality + 2
      case _           => quality + 1
    }
  }
}
