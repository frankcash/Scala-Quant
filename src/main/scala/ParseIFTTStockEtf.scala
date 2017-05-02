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


import Tree.{Tree, Sort}

import scala.collection.mutable.ArrayBuffer

object ParseIFTTStockEtf {

  /**
    * Path for data CSV
    */
  final val path = "/home/foobar/Code/IFTT-Stock-Data-Manipulator/src/main/java/data/sjnk.csv"
  /**
    * Step for calculating segmentation of price data when calculating local min and max for historical support and resistance
    */
  final val step = 5
  /**
    * Amount of days to use for Moving Average
    */
  final val movingAvgSize = 10


  /**
    *
    * @param path Path to CSV File
    * @return ArrayBuffer
    */
  def CSVParseIFTTT(path: String): ArrayBuffer[Array[String]] ={
    val rows = ArrayBuffer[Array[String]]()
    val bufferedSource = io.Source.fromFile(path)
    for(line <- bufferedSource.getLines()){
      rows += line.split(",").map(_.trim)
    }
    bufferedSource.close()
    rows
  }

  /**
    *
    * @param rows Rows from CSVParse Output
    * @return ArrayBuffer with Closing Costs of the Stock/ETF
    */
  def StripClosingPriceIFTTT(rows: ArrayBuffer[Array[String]]): ArrayBuffer[Double] ={
    val closingCosts:ArrayBuffer[Double] = new ArrayBuffer[Double]
    rows.map(row => closingCosts.append(row(3).toDouble))
    closingCosts
  }

  /**
    *
    * @param xs List
    * @param n Step size
    * @tparam A
    * @return Returns a list of stepped lists
    */
  def split[A](xs:List[A], n:Int): List[List[A]] ={
    if(xs.isEmpty) Nil
    else (xs take n) :: split(xs drop n, n)
  }

  /**
    * Reference on Resistance <http://www.investopedia.com/articles/technical/061801.asp>
    * @param data List of List Double.  Takes return from split(xs, n).
    * @return Returns the avg resistance
    */
  def avgResistance(data:List[List[Double]]): Double = {
    var runTotal = 0.0;
    for(n <- data){
      runTotal += (n.max)
    }
    return runTotal./(data.length)
  }


  /**
    * Reference on Support <http://www.investopedia.com/articles/technical/061801.asp>
    * @param data List of List Double. Takes return from split(xs, n)
    * @return Returns the avg support
    */
  def avgSupport(data:List[List[Double]]): Double ={
    var runTotal = 0.0
    for(n <- data){
      runTotal += n.min
    }
    return runTotal./(data.length)
  }


  /**
    * Reference on Moving Average <http://www.investopedia.com/terms/m/movingaverage.asp>
    * @param data List[Double] of prices to include in moving average
    * @return Moving Average
    */
  def movingAvg(data:List[Double]): Double ={
    var avg = 0.0
    avg = data.sum / movingAvgSize.toDouble
    return avg
  }

  /**
    * Fibonacci Retracement Math
    * High minus Low multiplied by the given ratio
    * Reference on Fibonacci Retracement <http://www.investopedia.com/ask/answers/05/fibonacciretracement.asp>
    * @param h The high value
    * @param l The low value
    * @param ratio The ratio to use, as a decimal
    * @return the amount for the fibonacci retracement at the given ratio
    */
  def fibonacciRetracement(h:Double, l:Double, ratio:Double): Double={
    val fib = (h - l) * ratio
    val res = h - fib
    res
  }

  /**
    * Reference on Fibonacci Retracement <http://www.investopedia.com/ask/answers/05/fibonacciretracement.asp>
    * @param high
    * @param low
    * @return List of fibonacci retracement values for [23.6%, 38.2%, 50.0%, 0.618%, 100%]
    */
  def fibRetracementValues(high:Double, low:Double): List[Double]={
    val fib:ArrayBuffer[Double] = new ArrayBuffer[Double]
    fib.append(fibonacciRetracement(high, low, 0.236))
    fib.append(fibonacciRetracement(high, low, 0.382))
    fib.append(fibonacciRetracement(high, low, 0.500))
    fib.append(fibonacciRetracement(high, low, 0.618))
    fib.append(fibonacciRetracement(high, low, 1.000))
    fib.toList

  }

  def main(args: Array[String]): Unit= {
    println("hello World")
    val rows = CSVParseIFTTT(path)
    val closingPrices = StripClosingPriceIFTTT(rows)
//    closingPrices.map(row => println(s"$row"))
    val sorted = Sort.mergeSort(closingPrices.toList)
    val myTree = Tree.fromSortedList(sorted)
    val steppedClosingPrices = split(closingPrices.toList, step)
    val mvAvg = movingAvg(closingPrices.takeRight(movingAvgSize.toInt).toList)
    val retracementLevels = fibRetracementValues(myTree.max, myTree.min)
    println("Moving Average is: "+ mvAvg)

    println("Average Support: " + avgSupport(steppedClosingPrices))
    println("Average Resistance: " + avgResistance(steppedClosingPrices))


    println("Historical Low: " + myTree.min)
    println("Historical Max: " + myTree.max)
    retracementLevels.map(l => println("Retracement: " + l))


  }

}
