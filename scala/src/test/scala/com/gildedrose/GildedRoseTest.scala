package com.gildedrose

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class GildedRoseTest extends AnyFlatSpec with Matchers {

  val updateItemOnce = Utils.updateItem(1)
  it should "foo" in {
    val items = Array[Item](Item("foo", 0, 0))
    val app = new GildedRose(items)
    app.updateQuality()
    app.items(0).name should equal("foo")
    app.items(0).sellIn should equal(-1)
    app.items(0).quality should equal(0)
  }

  it should "decreasesSellInAndQualityByOneForNormalItem" in {
    val items = Array[Item](Item("foo", 5, 5))
    val app = new GildedRose(items)
    app.updateQuality()
    app.items(0) shouldBe Item("foo", 4, 4)
  }

  it should "givenNegativeSellInAndNoramalItemQualityDegradesTwiceAsFast" in {
    updateItemOnce(Item("foo", -1, 5))(0) shouldBe Item("foo", -2, 3)
  }

  it should "givenZeroQualityAndNoramalItemThenQualityIsNotNegative" in {
    updateItemOnce(Item("foo", 5, 0))(0) shouldBe Item("foo", 4, 0)
  }

  it should "sulfurasDoesNotDecreaseSellInAndQualityIs80" in {
    updateItemOnce(Item("Sulfuras, Hand of Ragnaros", 5, 5))(0) shouldBe Item(
      "Sulfuras, Hand of Ragnaros",
      5,
      80
    )
  }

  it should "givenAgedBrieSellInIsPositiveThenSellInDecreasesAndQualityIncreases" in {
    updateItemOnce(Item("Aged Brie", 5, 5))(0) shouldBe Item("Aged Brie", 4, 6)
  }

  it should "givenAgedBrieSellInIsNegativeThenSellInDecreasesAndQualityIncreases" in {
    updateItemOnce(Item("Aged Brie", -1, 5))(0) shouldBe Item(
      "Aged Brie",
      -2,
      6
    )
  }

  it should "givenBackstagePassesSellInAreLessThen11ThenSellInDecreasesAndQualityIncreasesBy2" in {
    updateItemOnce(Item("Backstage passes to a TAFKAL80ETC concert", 10, 5))(
      0
    ) shouldBe Item("Backstage passes to a TAFKAL80ETC concert", 9, 7)
  }

  it should "givenBackstagePassesSellInAreLessThen6ThenSellInDecreasesAndQualityIncreasesBy3" in {
    updateItemOnce(Item("Backstage passes to a TAFKAL80ETC concert", 5, 5))(
      0
    ) shouldBe Item("Backstage passes to a TAFKAL80ETC concert", 4, 8)
  }

  it should "givenBackstagePassesSellInAreLessThen1ThenSellInDecreasesAndQualityDropsTo0" in {
    updateItemOnce(Item("Backstage passes to a TAFKAL80ETC concert", 0, 5))(
      0
    ) shouldBe Item("Backstage passes to a TAFKAL80ETC concert", -1, 0)
  }

  it should "givenConjuredManaCakeAndPositiveSellInThenSellInDecreasesAndQualityDegradesTwiceAsFast" in {
    updateItemOnce(Item("Conjured Mana Cake", 5, 5))(
      0
    ) shouldBe Item("Conjured Mana Cake", 4, 3)
  }

  it should "givenConjuredManaCakeAndNegativeSellInThenSellInDecreasesAndQualityDegradesTwiceAsFast" in {
    updateItemOnce(Item("Conjured Mana Cake", -1, 5))(
      0
    ) shouldBe Item("Conjured Mana Cake", -2, 3)
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
    ("Aged Brie", 5, 50, 4, 50),
    ("Aged Brie", -5, 50, -6, 50),
    ("Backstage passes to a TAFKAL80ETC concert", 5, 50, 4, 50)
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
