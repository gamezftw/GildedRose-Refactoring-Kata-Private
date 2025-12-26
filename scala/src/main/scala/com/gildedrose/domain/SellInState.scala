package com.gildedrose.domain

trait SellInState {
  def update(f: Int => Int): SellInState
  def get: Int
}

case class Ongoing(var days: Int) extends SellInState {
  def update(f: Int => Int): SellInState = {
    val newValue = f(days)
    if (newValue >= 0)
      days = newValue
      this
    else Expired(newValue)
  }
  def get: Int = days

}
case class Expired(var days: Int) extends SellInState {
  def update(f: Int => Int): SellInState = {
    days = f(days)
    this
  }

  def get: Int = days
}

object SellInState {
  def create(days: Int): SellInState =
    if (days >= 0) Ongoing(days) else Expired(days)
}
