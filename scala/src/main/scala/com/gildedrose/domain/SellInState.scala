package com.gildedrose.domain

trait SellInState {
  def update(f: Int => Int): SellInState
  def get: Int
}

case class Ongoing(var days: Int) extends SellInState {
  def update(f: Int => Int): SellInState = {
    SellInState.create(f(days))
  }
  def get: Int = days

}
case class Expired(var days: Int) extends SellInState {
  def update(f: Int => Int): SellInState = {
    SellInState.create(f(days))
  }

  def get: Int = days
}

object SellInState {
  def create(days: Int): SellInState =
    if (days >= 0) Ongoing(days) else Expired(days)
}
