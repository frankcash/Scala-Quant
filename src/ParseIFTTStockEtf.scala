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


import Tree.{BST, Branch, Leaf}

import scala.collection.mutable.ArrayBuffer

object ParseIFTTStockEtf {


  /**
    *
    * @param path Path to CSV File
    * @return ArrayBuffer
    */
  def CSVParse(path: String): ArrayBuffer[Array[String]] ={
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
  def StripClosingPrice(rows: ArrayBuffer[Array[String]]): ArrayBuffer[Double] ={
    val closingCosts:ArrayBuffer[Double] = new ArrayBuffer[Double]
    rows.map(row => closingCosts.append(row(4).toDouble))
    closingCosts
  }

  def main(args: Array[String]): Unit= {
    println("hello World")
    val rows = CSVParse("C:\\Users\\Frank Cash\\Documents\\GitHub\\IFTT-Stock-Data-Manipulator\\src\\data\\sjnk.csv")
    val closingPrices = StripClosingPrice(rows)
    closingPrices.map(row => println(s"$row"))


    // TODO: Test tree
//    val tree = BST
    val tree = [1,2,3,4].foldLeft((t:BST,e:Int) => t.add(e))(Leaf)

//    tree.add(4);
//    tree.add(12)
//
    println(tree.max)
//    val f = tree.valuesByDepth
//    println(f.length)
//    f.map(f => println(f))
  }

}
