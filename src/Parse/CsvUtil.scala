package Parse

import scala.collection.mutable.ArrayBuffer

/**
  * Created by Frank Cash on 5/2/17.
  */
object CsvUtil {
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
    * @param xs List
    * @param n Step size
    * @tparam A
    * @return Returns a list of stepped lists
    */
  def split[A](xs:List[A], n:Int): List[List[A]] ={
    if(xs.isEmpty) Nil
    else (xs take n) :: split(xs drop n, n)
  }

}
