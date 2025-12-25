package com.gildedrose

class GildedRose(val items: Array[Item]) {

  def updateQuality(): Unit = {
    for (i <- 0 until items.length) {
      // Sulfuras and others
      if (
        !items(i).name.equals("Aged Brie")
        && !items(i).name.equals("Backstage passes to a TAFKAL80ETC concert")
      ) {
        // Others
        if (items(i).quality > 0) {
          if (!items(i).name.equals("Sulfuras, Hand of Ragnaros")) {
            items(i).quality = items(i).quality - 1
            // Conjured
            if (items(i).name.equals("Conjured Mana Cake")) {
              if (items(i).quality > 0) {
                items(i).quality = items(i).quality - 1
              }
            }
          }
          // Only Sulfuras???
          else {
            items(i).quality = 80
          }
        }
      }
      // Aged and Backstage
      // and Conjured
      else {
        if (items(i).quality < 50) {
          items(i).quality = items(i).quality + 1
          // Backstage
          if (
            items(i).name.equals("Backstage passes to a TAFKAL80ETC concert")
          ) {
            // double value
            if (items(i).sellIn < 11) {
              if (items(i).quality < 50) {
                items(i).quality = items(i).quality + 1
              }
            }

            // triple value
            if (items(i).sellIn < 6) {
              if (items(i).quality < 50) {
                items(i).quality = items(i).quality + 1
              }
            }
          }
        }
      }

      // Handle sellIn decrease
      if (!items(i).name.equals("Sulfuras, Hand of Ragnaros")) {
        items(i).sellIn = items(i).sellIn - 1
      }

      // sellIn bellow 0
      if (items(i).sellIn < 0) {
        if (!items(i).name.equals("Aged Brie")) {
          if (
            !items(i).name.equals("Backstage passes to a TAFKAL80ETC concert")
          ) {
            if (items(i).quality > 0) {
              // Other
              if (!items(i).name.equals("Sulfuras, Hand of Ragnaros")) {
                items(i).quality = items(i).quality - 1
                // Conjured degrades twice as fast
                if (items(i).name.equals("Conjured Mana Cake")) {
                  items(i).quality = items(i).quality - 1
                }
              }
            }
          }
          // "Backstage passes to a TAFKAL80ETC concert"
          else {
            items(i).quality = items(i).quality - items(i).quality // 0?
          }
        }
        // "Aged Brie"
        // Why does it increase quality again?
        // else {
        //   if (items(i).quality < 50) {
        //     items(i).quality = items(i).quality + 1
        //   }
        // }
      }
    }
  }
}
