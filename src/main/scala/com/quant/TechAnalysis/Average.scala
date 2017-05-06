package com.quant.TechAnalysis

/**
  * Created by majora on 5/6/17.
  */
object Average {

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
  def movingAvg(movingAvgSize:Double, data:List[Double] ): Double ={
    var avg = 0.0
    avg = data.sum / movingAvgSize
    return avg
  }

}
