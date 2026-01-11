package com.gildedrose

trait Updatable[A]:
  extension(a: A) def update: A
