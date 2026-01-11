package com.gildedrose

class GildedRose(val items: Array[Item]) {

  def updateQuality(days: Int): List[(Int, Array[Item])] = {
    (0 until days - 1).foldLeft(List[(Int, Array[Item])]((0, items)))(
      (acc, curr) => {
        val (_, lastItems) = acc.last
        acc.appended(curr + 1, Updatable.updateQuality(lastItems))
      }
    )
  }
}
