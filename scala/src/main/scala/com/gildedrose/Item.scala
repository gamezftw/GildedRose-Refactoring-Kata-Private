package com.gildedrose

case class Item(name: String, var sellIn: Int, var quality: Int) {}

object Item {
  given Updatable[Item] with
    extension (item: Item)
      def updateQuality: Item =
        ItemUpdater.updateItem(item)
}
