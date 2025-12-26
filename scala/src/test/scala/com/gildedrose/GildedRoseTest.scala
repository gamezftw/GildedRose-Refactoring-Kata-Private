package com.gildedrose

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import com.gildedrose.ItemConstants.conjuredMakaCake

class GildedRoseTest extends AnyFlatSpec with Matchers {

  val updateItemOnce = Utils.updateItem(1)
  val normalItem = "foo"

  it should "GivenNormalItemThenSellInIsDecreasedAndQualityIsDecreasedByOne" in {
    updateItemOnce(Item(normalItem, 5, 5))(0) shouldBe Item(normalItem, 4, 4)
  }

  it should "givenNegativeSellInAndNoramalItemQualityDegradesTwiceAsFast" in {
    updateItemOnce(Item(normalItem, -1, 5))(0) shouldBe Item(normalItem, -2, 3)
  }

  it should "givenZeroSellInAndNoramalItemQualityDegradesTwiceAsFast" in {
    updateItemOnce(Item(normalItem, 0, 5))(0) shouldBe Item(normalItem, -1, 3)
  }

  it should "givenOneSellInAndNoramalItemQualityDegradesOnce" in {
    updateItemOnce(Item(normalItem, 1, 5))(0) shouldBe Item(normalItem, 0, 4)
  }

  it should "givenNoramalItemAndQualityAboveMaxThenQualityIsClampedAndDecreased" in {
    updateItemOnce(Item(normalItem, 5, 52))(0) shouldBe Item(normalItem, 4, 49)
  }

  it should "givenConjuredManaCakeAndQualityAboveMaxThenQualityIsClampedAndDecreased" in {
    updateItemOnce(Item(conjuredMakaCake, 5, 52))(0) shouldBe Item(
      conjuredMakaCake,
      4,
      48
    )
  }

  it should "givenZeroQualityAndNoramalItemThenQualityIsNotNegative" in {
    updateItemOnce(Item(normalItem, 5, 0))(0) shouldBe Item(normalItem, 4, 0)
  }

  it should "givenZSulfurasAndQualityAboveMaxThenQualityIsClamped" in {
    updateItemOnce(Item(ItemConstants.sulfuras, 5, 81))(0) shouldBe Item(
      ItemConstants.sulfuras,
      5,
      80
    )
  }

  it should "sulfurasDoesNotDecreaseSellInAndQualityIs80" in {
    updateItemOnce(Item(ItemConstants.sulfuras, 5, 5))(0) shouldBe Item(
      ItemConstants.sulfuras,
      5,
      80
    )
  }

  it should "givenAgedBrieSellInIsPositiveThenSellInDecreasesAndQualityIncreases" in {
    updateItemOnce(Item(ItemConstants.agedBrie, 5, 5))(0) shouldBe Item(
      ItemConstants.agedBrie,
      4,
      6
    )
  }

  it should "givenAgedBrieSellInIsNegativeThenSellInDecreasesAndQualityIncreases" in {
    updateItemOnce(Item(ItemConstants.agedBrie, -1, 5))(0) shouldBe Item(
      ItemConstants.agedBrie,
      -2,
      6
    )
  }

  it should "givenBackstagePassesSellInAreLessThen11ThenSellInDecreasesAndQualityIncreasesBy2" in {
    updateItemOnce(Item(ItemConstants.backstagePasses, 10, 5))(
      0
    ) shouldBe Item(ItemConstants.backstagePasses, 9, 7)
  }

  it should "givenBackstagePassesSellInAreLessThen6ThenSellInDecreasesAndQualityIncreasesBy3" in {
    updateItemOnce(Item(ItemConstants.backstagePasses, 5, 5))(
      0
    ) shouldBe Item(ItemConstants.backstagePasses, 4, 8)
  }

  it should "givenBackstagePassesSellInAreLessThen1ThenSellInDecreasesAndQualityDropsTo0" in {
    updateItemOnce(Item(ItemConstants.backstagePasses, 0, 5))(
      0
    ) shouldBe Item(ItemConstants.backstagePasses, -1, 0)
  }

  it should "givenConjuredManaCakeAndPositiveSellInThenSellInDecreasesAndQualityDegradesTwiceAsFast" in {
    updateItemOnce(Item(ItemConstants.conjuredMakaCake, 5, 5))(
      0
    ) shouldBe Item(ItemConstants.conjuredMakaCake, 4, 3)
  }

  it should "givenConjuredManaCakeAndNegativeSellInThenSellInDecreasesAndQualityDegradesTwiceAsFast" in {
    updateItemOnce(Item(ItemConstants.conjuredMakaCake, -1, 5))(
      0
    ) shouldBe Item(ItemConstants.conjuredMakaCake, -2, 1)
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
    (ItemConstants.agedBrie, 5, 50, 4, 50),
    (ItemConstants.agedBrie, -5, 50, -6, 50),
    (ItemConstants.backstagePasses, 5, 50, 4, 50)
  )

  property("qualityCanNotBeBiggerThen50") {
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
