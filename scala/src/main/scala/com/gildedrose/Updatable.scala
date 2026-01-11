package com.gildedrose

import scala.reflect.ClassTag

trait Updatable[A]:
  extension (a: A) def updateQuality: A

object Updatable:
  def updateQuality[A: Updatable: ClassTag](xs: Array[A]): Array[A] =
    xs.map(_.updateQuality)
