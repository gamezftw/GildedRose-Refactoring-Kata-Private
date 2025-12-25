package com.gildedrose.domain

abstract class DomainItem(val name: String, var sellIn: Int, var quality: Int) {

  protected val minQuality = 0
  protected val maxQuality = 50
  protected val degradeValue: Int = 1

  def updateQuality(): Unit = {
    updateSellIn()
    degrade()
  }

  def updateSellIn(): Unit = {
    sellIn = sellIn - 1
  }

  def degrade(): Unit = {
    val potentialQuality = getPotentialQuality
    quality = Math.min(maxQuality, Math.max(minQuality, potentialQuality))
  }

  def getPotentialQuality: Int = {
    val calculatedDegadeValue =
      if (sellIn >= 0) degradeValue else degradeValue * 2

    quality - calculatedDegadeValue
  }
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
