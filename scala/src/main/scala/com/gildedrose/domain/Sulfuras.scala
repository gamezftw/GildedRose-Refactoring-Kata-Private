package com.gildedrose.domain

class Sulfuras(s: Int)
    extends DomainItem("Sulfuras, Hand of Ragnaros", s, 80) {

  override val maxQuality = 80

  override def updateSellIn(): Unit = {}

  override def degrade(): Unit = {}
}
