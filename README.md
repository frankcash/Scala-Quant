# IFTT Stock Data Manipulator

This is a project to explore market data.

This project is designed to work with a specific IFTT plugin: [Keep track of a particular stock's daily closing price in a spreadsheet](https://ifttt.com/applets/117304p-keep-track-of-a-particular-stock-s-daily-closing-price-in-a-spreadsheet)

## Utilizing

Download the `CSV` file for your desired Stock/ETF.  Then copy your path for the file into `final val path ="{Path_HERE}"`.


## Functions

`avgResistance(data:List[List[Double]]): Double`

Calculates the [average resistance](http://www.investopedia.com/articles/technical/061801.asp) based local maxes from split lists.  Takes maximum values from the split lists and then generates an average using the amount of split lists.

`avgSupport(data:List[List[Double]]): Double`

Calculates the [average support](http://www.investopedia.com/articles/technical/061801.asp) based local maxes from split lists.  Takes minimum values from the split lists and then generates an average using the amount of split lists.

`movingAvg(data:List[Double]): Double`

Calculates [moving average](http://www.investopedia.com/terms/m/movingaverage.asp).  The amount of days is pre-defined to 10.

## Configuration

It is also able to configure how many days should be grouped into the lists for calculating the average resistance and support.  Edit `final val step = 5`.

The moving average size can be changed. Edit `  final val movingAvgSize = 10`

### License

MIT