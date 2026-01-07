package com.gildedrose.domain

import com.gildedrose.constants.ItemConstants

abstract class DomainItem(
    val name: String,
    val sellIn: SellInState,
    val initialQuality: Int
) {
  protected val minQuality: Int = 0
  protected val maxQuality: Int = 50

  protected val baseDegradeValue: Int = 1

  val quality: Int = clampedQuality(initialQuality)

  protected val degradeOperation: Int => Int => Int = x => y => x - y

  protected def clone(
      name: String = name,
      sellIn: SellInState = sellIn,
      quality: Int = quality
  ): DomainItem

  protected val advanceSellInFn: Int => Int = x => x - 1

  def updateQuality: DomainItem = {
    updateSellIn.degrade
  }

  protected def updateSellIn: DomainItem = {
    clone(sellIn = sellIn.update(advanceSellInFn))
  }

  protected def degrade: DomainItem = {
    clone(quality = getPotentialQuality)
  }

  protected def getPotentialQuality =
    degradeOperation(quality)(getAdjustedDegradeValue)

  protected def getAdjustedDegradeValue: Int = {
    sellIn match
      case Ongoing(_) => baseDegradeValue
      case Expired(_) => baseDegradeValue * 2
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
