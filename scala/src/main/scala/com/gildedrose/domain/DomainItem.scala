package com.gildedrose.domain

import com.gildedrose.constants.ItemConstants

abstract class DomainItem(
    val name: String,
    val sellIn: SellInState,
    val initialQuality: Int
) {
  protected val minQuality: Int = 0
  protected val maxQuality: Int = 50
  protected val degradeValue: Int = 1
  val quality: Int = clampedQuality(initialQuality)

  protected def copy(
      name: String,
      sellIn: SellInState,
      quality: Int
  ): DomainItem

  protected def advanceSellInFn: Int => Int = x => x - 1

  def updateQuality: DomainItem = {
    updateSellIn.degrade
  }

  protected def updateSellIn: DomainItem = {
    copy(name, sellIn.update(advanceSellInFn), quality)
  }

  protected def degrade: DomainItem = {
    copy(name, sellIn, getPotentialQuality)
  }

  protected def getPotentialQuality: Int = {
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
      case ItemConstants.AgedBrie        => AgedBrie(sellInState, quality)
      case ItemConstants.BackstagePasses =>
        BackstagePasses(sellInState, quality)
      case ItemConstants.ConjuredMakaCake =>
        ConjuredManaCake(sellInState, quality)
      case ItemConstants.Sulfuras => Sulfuras(sellInState)
      case _                      => OtherItem(name, sellInState, quality)
    }
  }
}
