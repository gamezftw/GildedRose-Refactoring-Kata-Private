package com.gildedrose

import com.gildedrose.domain._

class GildedRose(val items: Array[Item]) {

  def updateQuality(): Unit = {
    items.foreach(i => {
      val domainItem =
        DomainItem.fromItem(i.name, i.sellIn, i.quality).updateQuality
      i.sellIn = domainItem.sellIn.get
      i.quality = domainItem.quality
    })
  }
}
