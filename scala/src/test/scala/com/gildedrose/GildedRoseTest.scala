package com.gildedrose

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import com.gildedrose.constants._

class GildedRoseTest extends AnyFlatSpec with Matchers {

  val updateItemOnce = Utils.updateItem(1)
  val normalItem = "foo"

  behavior of "Normal item"
  it should "decrease sellIn and quality by one" in {
    updateItemOnce(Item(normalItem, 5, 5))(0) shouldBe Item(normalItem, 4, 4)
  }

  it should "decrease quality twice as fast when given negative sellIn" in {
    updateItemOnce(Item(normalItem, -1, 5))(0) shouldBe Item(normalItem, -2, 3)
  }

  it should "decrease quality twice as fast when given zero sellIn" in {
    updateItemOnce(Item(normalItem, 0, 5))(0) shouldBe Item(normalItem, -1, 3)
  }

  it should "decrease quality once when given one sellIn" in {
    updateItemOnce(Item(normalItem, 1, 5))(0) shouldBe Item(normalItem, 0, 4)
  }

  it should "clamp quality and decrease once when given quality is above max" in {
    updateItemOnce(Item(normalItem, 5, 52))(0) shouldBe Item(normalItem, 4, 49)
  }

  it should "clamp quality and decrease twice when given zero sellIn and quality is above max" in {
    updateItemOnce(Item(normalItem, 0, 52))(0) shouldBe Item(normalItem, -1, 48)
  }

  it should "clamp quality when quality is negative" in {
    updateItemOnce(Item(normalItem, 5, 0))(0) shouldBe Item(normalItem, 4, 0)
  }

  behavior of "Conjured mana Cake"
  it should "clamp quality and decrease once when given quality is above max" in {
    updateItemOnce(Item(ItemConstants.ConjuredMakaCake, 5, 52))(
      0
    ) shouldBe Item(
      ItemConstants.ConjuredMakaCake,
      4,
      48
    )
  }
  it should "clamp quality and decrease twice when given quality is above max and zero sellIn" in {
    updateItemOnce(Item(ItemConstants.ConjuredMakaCake, 0, 52))(
      0
    ) shouldBe Item(
      ItemConstants.ConjuredMakaCake,
      -1,
      46
    )
  }

  it should "decrease quality by two when sellIn is positive" in {
    updateItemOnce(Item(ItemConstants.ConjuredMakaCake, 5, 5))(
      0
    ) shouldBe Item(ItemConstants.ConjuredMakaCake, 4, 3)
  }

  it should "decrease quality by four when sellIn is negative" in {
    updateItemOnce(Item(ItemConstants.ConjuredMakaCake, -1, 5))(
      0
    ) shouldBe Item(ItemConstants.ConjuredMakaCake, -2, 1)
  }

  behavior of "Sulfuras"
  it should "quality is clamped when quality is above max" in {
    updateItemOnce(Item(ItemConstants.Sulfuras, 5, 81))(0) shouldBe Item(
      ItemConstants.Sulfuras,
      5,
      80
    )
  }

  it should "sellIn doesn't change" in {
    updateItemOnce(Item(ItemConstants.Sulfuras, 5, 5))(0) shouldBe Item(
      ItemConstants.Sulfuras,
      5,
      80
    )
  }

  behavior of "Aged Brie"
  it should "increases quality when sellIn is positive" in {
    updateItemOnce(Item(ItemConstants.AgedBrie, 5, 5))(0) shouldBe Item(
      ItemConstants.AgedBrie,
      4,
      6
    )
  }

  it should "increases quality twice when sellIn is negative" in {
    updateItemOnce(Item(ItemConstants.AgedBrie, -1, 5))(0) shouldBe Item(
      ItemConstants.AgedBrie,
      -2,
      7
    )
  }

  behavior of "Backstage passes"
  it should "increases quality by two when sellIn is ten" in {
    updateItemOnce(Item(ItemConstants.BackstagePasses, 10, 5))(
      0
    ) shouldBe Item(ItemConstants.BackstagePasses, 9, 7)
  }

  it should "increases quality by two when sellIn is 5" in {
    updateItemOnce(Item(ItemConstants.BackstagePasses, 5, 5))(
      0
    ) shouldBe Item(ItemConstants.BackstagePasses, 4, 8)
  }

  it should "quality is set to zero when sellIn is negative" in {
    updateItemOnce(Item(ItemConstants.BackstagePasses, 0, 5))(
      0
    ) shouldBe Item(ItemConstants.BackstagePasses, -1, 0)
  }
}

import org.scalatest._
import prop._
import scala.collection.immutable._

class SetSpec
    extends propspec.AnyPropSpec
    with TableDrivenPropertyChecks
    with Matchers {

  val updateItemOnce = Utils.updateItem(1)
  val qualityNotBiggerThen50Cases = Table(
    ("item", "givenSellIn", "giveQuality", "expectedSellIn", "expectedQuality"),
    (ItemConstants.AgedBrie, 5, 50, 4, 50),
    (ItemConstants.AgedBrie, -5, 50, -6, 50),
    (ItemConstants.BackstagePasses, 5, 50, 4, 50)
  )

  property("quality is clamped at 50") {
    forAll(qualityNotBiggerThen50Cases) {
      (
          item: String,
          givenSellIn: Int,
          giveQuality: Int,
          expectedSellIn: Int,
          expectedQuality: Int
      ) =>
        updateItemOnce(Item(item, givenSellIn, giveQuality))(0) shouldBe Item(
          item,
          expectedSellIn,
          expectedQuality
        )
    }
  }
}

object Utils {
  def updateItem(numberOfUpdates: Int)(
      givenItem: Item
  ): Array[Item] = {
    val items = Array[Item](givenItem)
    val app = new GildedRose(items)
    (0 until numberOfUpdates).foreach(_ => app.updateQuality())
    app.items
  }

}
