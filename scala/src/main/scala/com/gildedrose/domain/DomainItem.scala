package com.gildedrose.domain

abstract class DomainItem(
    val name: String,
    var sellIn: SellInState,
    val initialQuality: Int
) {
  protected val minQuality: Int = 0
  protected val maxQuality: Int = 50
  protected val degradeValue: Int = 1
  var quality: Int = clampedQuality(initialQuality)

  def advanceSellInFn: Int => Int = x => x - 1

  def updateQuality(): Unit = {
    updateSellIn()
    degrade()
  }

  def updateSellIn(): Unit = {
    sellIn = sellIn.update(advanceSellInFn)
  }

  def degrade(): Unit = {
    val potentialQuality = getPotentialQuality
    quality = clampedQuality(potentialQuality)
  }

  def getPotentialQuality: Int = {
    val calculatedDegadeValue =
      sellIn match
        case Ongoing(_) => degradeValue
        case Expired(_) => degradeValue * 2

    quality - calculatedDegadeValue
  }

  protected def clampedQuality(potentialQuality: Int): Int =
    potentialQuality.max(minQuality).min(maxQuality)
}

object DomainItem {
  def fromItem(name: String, sellIn: Int, quality: Int): DomainItem = {
    val sellInState = SellInState.create(sellIn)
    name match {
      case "Aged Brie" => AgedBrie(sellInState, quality)
      case "Backstage passes to a TAFKAL80ETC concert" =>
        BackstagePasses(sellInState, quality)
      case "Conjured Mana Cake" => ConjuredManaCake(sellInState, quality)
      case "Sulfuras, Hand of Ragnaros" => Sulfuras(sellInState)
      case _                            => OtherItem(name, sellInState, quality)
    }
  }
}
