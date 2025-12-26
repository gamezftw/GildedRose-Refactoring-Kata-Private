package com.gildedrose

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import com.gildedrose.constants._

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
    updateItemOnce(Item(ItemConstants.ConjuredMakaCake, 5, 52))(
      0
    ) shouldBe Item(
      ItemConstants.ConjuredMakaCake,
      4,
      48
    )
  }

  it should "givenZeroQualityAndNoramalItemThenQualityIsNotNegative" in {
    updateItemOnce(Item(normalItem, 5, 0))(0) shouldBe Item(normalItem, 4, 0)
  }

  it should "givenZSulfurasAndQualityAboveMaxThenQualityIsClamped" in {
    updateItemOnce(Item(ItemConstants.Sulfuras, 5, 81))(0) shouldBe Item(
      ItemConstants.Sulfuras,
      5,
      80
    )
  }

  it should "sulfurasDoesNotDecreaseSellInAndQualityIs80" in {
    updateItemOnce(Item(ItemConstants.Sulfuras, 5, 5))(0) shouldBe Item(
      ItemConstants.Sulfuras,
      5,
      80
    )
  }

  it should "givenAgedBrieSellInIsPositiveThenSellInDecreasesAndQualityIncreases" in {
    updateItemOnce(Item(ItemConstants.AgedBrie, 5, 5))(0) shouldBe Item(
      ItemConstants.AgedBrie,
      4,
      6
    )
  }

  it should "givenAgedBrieSellInIsNegativeThenSellInDecreasesAndQualityIncreases" in {
    updateItemOnce(Item(ItemConstants.AgedBrie, -1, 5))(0) shouldBe Item(
      ItemConstants.AgedBrie,
      -2,
      6
    )
  }

  it should "givenBackstagePassesSellInAreLessThen11ThenSellInDecreasesAndQualityIncreasesBy2" in {
    updateItemOnce(Item(ItemConstants.BackstagePasses, 10, 5))(
      0
    ) shouldBe Item(ItemConstants.BackstagePasses, 9, 7)
  }

  it should "givenBackstagePassesSellInAreLessThen6ThenSellInDecreasesAndQualityIncreasesBy3" in {
    updateItemOnce(Item(ItemConstants.BackstagePasses, 5, 5))(
      0
    ) shouldBe Item(ItemConstants.BackstagePasses, 4, 8)
  }

  it should "givenBackstagePassesSellInAreLessThen1ThenSellInDecreasesAndQualityDropsTo0" in {
    updateItemOnce(Item(ItemConstants.BackstagePasses, 0, 5))(
      0
    ) shouldBe Item(ItemConstants.BackstagePasses, -1, 0)
  }

  it should "givenConjuredManaCakeAndPositiveSellInThenSellInDecreasesAndQualityDegradesTwiceAsFast" in {
    updateItemOnce(Item(ItemConstants.ConjuredMakaCake, 5, 5))(
      0
    ) shouldBe Item(ItemConstants.ConjuredMakaCake, 4, 3)
  }

  it should "givenConjuredManaCakeAndNegativeSellInThenSellInDecreasesAndQualityDegradesTwiceAsFast" in {
    updateItemOnce(Item(ItemConstants.ConjuredMakaCake, -1, 5))(
      0
    ) shouldBe Item(ItemConstants.ConjuredMakaCake, -2, 1)
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
