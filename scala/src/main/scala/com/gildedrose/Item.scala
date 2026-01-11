package com.gildedrose

case class Item(name: String, var sellIn: Int, var quality: Int) {}

object Item {
  given Updatable[Item] with
    extension (item: Item)
      def update: Item = 
        ItemUpdater.updateItem(item)
      def updateInPlace: Item = 
        val updatedItem = ItemUpdater.updateItem(item)
        item.sellIn = updatedItem.sellIn
        item.quality = updatedItem.quality
        item
}
