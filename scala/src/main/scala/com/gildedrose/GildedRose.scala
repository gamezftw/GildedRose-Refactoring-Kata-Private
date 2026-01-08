package com.gildedrose

class GildedRose(val items: Array[Item]) {

  def updateQuality(): Unit = {
    items.foreach(i => {
      val domainItem = ItemUpdater.updateItem(i)
      i.sellIn = domainItem.sellIn
      i.quality = domainItem.quality
    })
  }
}
