package com.gildedrose.domain

abstract class DomainItem(
    val name: String,
    var sellIn: Int,
    val initialQuality: Int
) {
  protected val minQuality: Int = 0
  protected val maxQuality: Int = 50
  protected val degradeValue: Int = 1
  var quality: Int = clampedQuality(initialQuality)

  def updateQuality(): Unit = {
    updateSellIn()
    degrade()
  }

  def updateSellIn(): Unit = {
    sellIn -= 1
  }

  def degrade(): Unit = {
    val potentialQuality = getPotentialQuality
    quality = clampedQuality(potentialQuality)
  }

  def getPotentialQuality: Int = {
    val calculatedDegadeValue =
      if (sellIn >= 0) degradeValue else degradeValue * 2

    quality - calculatedDegadeValue
  }

  protected def clampedQuality(potentialQuality: Int): Int =
    potentialQuality.max(minQuality).min(maxQuality)
}

object DomainItem {
  def fromItem(name: String, sellIn: Int, quality: Int): DomainItem = {
    name match {
      case "Aged Brie" => AgedBrie(sellIn, quality)
      case "Backstage passes to a TAFKAL80ETC concert" =>
        BackstagePasses(sellIn, quality)
      case "Conjured Mana Cake"         => ConjuredManaCake(sellIn, quality)
      case "Sulfuras, Hand of Ragnaros" => Sulfuras(sellIn)
      case _                            => OtherItem(name, sellIn, quality)
    }
  }
}
