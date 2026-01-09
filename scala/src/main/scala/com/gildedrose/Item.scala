package com.gildedrose

case class Item(name: String, var sellIn: Int, var quality: Int) {}

object Item {
  extension (item: Item)
    def fromItem(that: Item) =
      item.sellIn = that.sellIn
      item.quality = that.quality
}
