package com.gildedrose

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import com.gildedrose.constants._
import com.gildedrose.domain._

class DomainItemTest extends AnyFlatSpec with Matchers {

  val normalItem = "foo"
  val sellIn = 0

  behavior of "Normal item"
  it should "Clamp quality to 50 when initial quality is above 50" in {
    val item = DomainItem.fromItem(normalItem, sellIn, 55)
    item.quality should equal(50)
  }
  it should "Clamp quality to 0 when initial quality is negative" in {
    val item = DomainItem.fromItem(normalItem, sellIn, -5)
    item.quality should equal(0)
  }

  behavior of "Aged Brie"
  it should "Clamp quality to 50 when initial quality is above 50" in {
    val item = DomainItem.fromItem(ItemConstants.AgedBrie, sellIn, 55)
    item.quality should equal(50)
  }
  it should "Clamp quality to 0 when initial quality is negative" in {
    val item = DomainItem.fromItem(ItemConstants.AgedBrie, sellIn, -5)
    item.quality should equal(0)
  }

  behavior of "Backstage passes"
  it should "Clamp quality to 50 when initial quality is above 50" in {
    val item = DomainItem.fromItem(ItemConstants.BackstagePasses, sellIn, 55)
    item.quality should equal(50)
  }
  it should "Clamp quality to 0 when initial quality is negative" in {
    val item = DomainItem.fromItem(ItemConstants.BackstagePasses, sellIn, -5)
    item.quality should equal(0)
  }

  behavior of "Conjured Mana Cake"
  it should "Clamp quality to 50 when initial quality is above 50" in {
    val item = DomainItem.fromItem(ItemConstants.ConjuredMakaCake, sellIn, 55)
    item.quality should equal(50)
  }
  it should "Clamp quality to 0 when initial quality is negative" in {
    val item = DomainItem.fromItem(ItemConstants.ConjuredMakaCake, sellIn, -5)
    item.quality should equal(0)
  }

  behavior of "Sulfuras"
  it should "Set quality to 80 when initial quality is positive" in {
    val item = DomainItem.fromItem(ItemConstants.Sulfuras, sellIn, 85)
    item.quality should equal(80)
  }
  it should "Set quality to 80 when initial quality is negative" in {
    val item = DomainItem.fromItem(ItemConstants.Sulfuras, sellIn, -1)
    item.quality should equal(80)
  }
}
