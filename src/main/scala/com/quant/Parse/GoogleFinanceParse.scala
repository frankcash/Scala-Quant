package com.quant.Parse

import scala.collection.mutable.ArrayBuffer

/**
  * Created by Frank Cash on 5/2/17.
  */
object GoogleFinanceParse {

  /**
    *
    * @param rows Rows from CSVParse Output
    * @return ArrayBuffer with Closing Costs of the Stock/ETF
    */
  def StripClosingPriceGoog(rows: ArrayBuffer[Array[String]]): ArrayBuffer[Double] ={
    val closingCosts:ArrayBuffer[Double] = new ArrayBuffer[Double]
    rows.map(row => closingCosts.append(row(4).toDouble))
    closingCosts
  }
}
