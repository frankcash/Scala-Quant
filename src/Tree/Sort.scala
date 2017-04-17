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

package Tree


/**
  * Created by Frank Cash on 4/16/17.
  */

object Sort{

  /**
    *
    * @param left List[Double]
    * @param right List[Double]
    * @return List[Double]
    */
    def merge(left: List[Double], right: List[Double]):List[Double]= (left, right)match{
      case(left, Nil) => left
      case(Nil, right) => right
      case(leftHead :: leftTail, rightHead :: rightTail) =>
        if(leftHead < rightHead) leftHead::merge(leftTail, right)
        else rightHead :: merge(left, rightTail)
    }

  /**
    *  Merge Sort
    * @param list[Double] Input
    * @return Returns a sorted List[Double]
    */
  def mergeSort(list: List[Double]): List[Double] ={
      val n = list.length / 2
      if (n == 0) list
      else{
        val(left, right) = list.splitAt(n)
        merge(mergeSort(left), mergeSort(right))
      }
    }


  }

