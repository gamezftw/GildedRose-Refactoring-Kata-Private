package com.gildedrose.domain

class BackstagePasses(s: SellInState, q: Int)
    extends DomainItem("Backstage passes", s, q) {

  override def getPotentialQuality = {
    sellIn match {
      case Expired(_)           => 0
      case Ongoing(x) if x < 6  => quality + 3
      case Ongoing(x) if x < 11 => quality + 2
      case _                    => quality + 1
    }
  }
}
