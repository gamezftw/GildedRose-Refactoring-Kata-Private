package com.gildedrose

class GildedRose(val items: Array[Item]) {

  def updateQuality(): Unit = {
    items.foreach(i => i.fromItem(ItemUpdater.updateItem(i)))
  }
}
