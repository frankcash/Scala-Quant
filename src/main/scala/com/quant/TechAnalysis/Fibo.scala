package com.quant.TechAnalysis

import scala.collection.mutable.ArrayBuffer

/**
  * Created by majora on 5/6/17.
  */
object Fibo {
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
}
