package com.gildedrose.domain

import com.gildedrose.constants._

case class BackstagePasses(s: SellInState, q: Int)
    extends DomainItem(ItemConstants.BackstagePasses, s, q) {

  protected override def getPotentialQuality = {
    sellIn match {
      case Expired(_)           => 0
      case Ongoing(x) if x < 6  => quality + 3
      case Ongoing(x) if x < 11 => quality + 2
      case _                    => quality + 1
    }
  }

  protected def clone(
      name: String,
      sellIn: SellInState,
      quality: Int
  ): DomainItem = BackstagePasses(sellIn, quality)
}
