package com.gildedrose

import org.approvaltests.Approvals
import org.approvaltests.reporters.DiffReporter
import org.approvaltests.reporters.UseReporter
import org.junit.jupiter.api.Test

import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.PrintStream

@UseReporter(Array(classOf[DiffReporter]))
class GildedRoseApprovalTest {

  @Test
  def foo(): Unit = {

    val items: Array[Item] = Array(Item("foo", 0, 0))
    val app: GildedRose = new GildedRose(items)
    val updatedItems = app.updateQuality(2)

    Approvals.verifyAll("Items", updatedItems.last._2)
  }

  @Test
  def thirtyDays(): Unit = {

    val fakeoutput: ByteArrayOutputStream = new ByteArrayOutputStream()
    System.setOut(new PrintStream(fakeoutput))
    System.setIn(new ByteArrayInputStream("a\n".getBytes()))

    val args: Array[String] = Array("30")
    TexttestFixture.main(args)
    val output: String = fakeoutput.toString()

    Approvals.verify(output)
  }
}
