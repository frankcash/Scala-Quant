/**MIT License
  *Copyright (c) 2017 Frank Cash
  *Permission is hereby granted, free of charge, to any person obtaining a copy
  *of this software and associated documentation files (the "Software"), to deal
  *in the Software without restriction, including without limitation the rights
  *to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
  *copies of the Software, and to permit persons to whom the Software is
  *furnished to do so, subject to the following conditions:
  *The above copyright notice and this permission notice shall be included in all
  *copies or substantial portions of the Software.
  *THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
  *IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
  *FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
  *AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
  *LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
  *OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
  *SOFTWARE.
  */

/**
  * Created by Frank Cash on 4/12/2017.
  * Made to work CSV output from: https://ifttt.com/applets/117304p-keep-track-of-a-particular-stock-s-daily-closing-price-in-a-spreadsheet
  */

import com.quant.TechAnalysis.{Average, Fibo}
import Tree.{Sort, Tree}
import com.quant.Parse.{CsvUtil, GoogleFinanceParse, IFTTTParse}


object StockEtfData {

  /**
    * Step for calculating segmentation of price data when calculating local min and max for historical support and resistance
    */
  final val step = 5
  /**
    * Amount of days to use for Moving Average
    */
  final val movingAvgSize = 10



  def IFTTT(path:String ): Unit= {
    println("hello World")
    val rows = CsvUtil.CSVParseIFTTT(path)
    val closingPrices = IFTTTParse.StripClosingPriceIFTTT(rows)
    val sorted = Sort.mergeSort(closingPrices.toList)
    val myTree = Tree.fromSortedList(sorted)
    val steppedClosingPrices = CsvUtil.split(closingPrices.toList, step)
    val mvAvg = Average.movingAvg(movingAvgSize.toDouble, closingPrices.takeRight(movingAvgSize.toInt).toList)
    val retracementLevels = Fibo.fibRetracementValues(myTree.max, myTree.min)
    println("Moving Average is: "+ mvAvg)

    println("Average Support: " + Average.avgSupport(steppedClosingPrices))
    println("Average Resistance: " + Average.avgResistance(steppedClosingPrices))


    println("Historical Low: " + myTree.min)
    println("Historical Max: " + myTree.max)
    retracementLevels.map(l => println("Retracement: " + l))


  }

  def Goog(path:String ): Unit= {
    val rows = CsvUtil.CSVParseIFTTT(path)
    val closingPrices = GoogleFinanceParse.StripClosingPriceGoog(rows)
    val sorted = Sort.mergeSort(closingPrices.toList)
    val myTree = Tree.fromSortedList(sorted)
    val steppedClosingPrices = CsvUtil.split(closingPrices.toList, step)
    val mvAvg = Average.movingAvg(movingAvgSize.toDouble, closingPrices.takeRight(movingAvgSize.toInt).toList)
    val retracementLevels = Fibo.fibRetracementValues(myTree.max, myTree.min)
    println("Moving Average is: "+ mvAvg)

    println("Average Support: " + Average.avgSupport(steppedClosingPrices))
    println("Average Resistance: " + Average.avgResistance(steppedClosingPrices))


    println("Historical Low: " + myTree.min)
    println("Historical Max: " + myTree.max)
    retracementLevels.map(l => println("Retracement: " + l))
  }

  def main(args: Array[String]): Unit= {
    IFTTT("/Users/majora/Code/Scala-Quant/src/data/sjnk.csv")
    Goog("/Users/majora/Code/Scala-Quant/src/data/txn-no_headers.csv")
  }


  }
