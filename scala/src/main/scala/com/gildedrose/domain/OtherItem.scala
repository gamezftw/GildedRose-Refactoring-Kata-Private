package com.gildedrose.domain

case class OtherItem(n: String, s: SellInState, q: Int)
    extends DomainItem(n, s, q) {

  protected def copy(
      name: String,
      sellIn: SellInState,
      quality: Int
  ): DomainItem = OtherItem(name, sellIn, quality)
}
