package com.gildedrose

type Updater = Item => Item

object ItemUpdater {

  private def advanceSellIn(sellIn: Int) = sellIn - 1
  private def isBeforeSellIn(sellIn: Int) = sellIn >= 0
  private def clampQuality(
      quality: Int,
      minQuality: Int = 0,
      maxQuality: Int = 50
  ) =
    quality.max(minQuality).min(maxQuality)

  val normal: Updater = item =>
    val sellIn = advanceSellIn(item.sellIn)
    val initialQuality = clampQuality(item.quality)
    val quality =
      if isBeforeSellIn(sellIn) then initialQuality - 1
      else initialQuality - 2
    item.copy(sellIn = sellIn, quality = clampQuality(quality))

  val agedBrie: Updater = item =>
    val sellIn = advanceSellIn(item.sellIn)
    val initialQuality = clampQuality(item.quality)
    val quality =
      if isBeforeSellIn(sellIn) then initialQuality + 1
      else initialQuality + 2
    item.copy(
      sellIn = advanceSellIn(item.sellIn),
      quality = clampQuality(quality)
    )

  val backstagePasses: Updater = item =>
    val sellIn = advanceSellIn(item.sellIn)
    val initialQuality = clampQuality(item.quality)
    val quality =
      if sellIn > 10 then initialQuality + 1
      else if sellIn > 5 then initialQuality + 2
      else if sellIn >= 0 then initialQuality + 3
      else 0
    item.copy(sellIn = sellIn, quality = clampQuality(quality))

  val sulfuras: Updater = item => item.copy(quality = 80)

  val conjured: Updater = item =>
    val sellIn = advanceSellIn(item.sellIn)
    val initialQuality = clampQuality(item.quality)
    val quality =
      if isBeforeSellIn(sellIn) then initialQuality - 2
      else initialQuality - 4
    item.copy(sellIn = sellIn, quality = clampQuality(quality))

  val registry: Map[String, Updater] =
    Map(
      "Aged Brie" -> agedBrie,
      "Backstage passes to a TAFKAL80ETC concert" -> backstagePasses,
      "Sulfuras, Hand of Ragnaros" -> sulfuras,
      "Conjured Mana Cake" -> conjured
    )

  def updateItem(item: Item): Item =
    registry.getOrElse(item.name, normal)(item)
}
