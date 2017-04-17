package Tree

/**
  * Created by Frank Cash on 4/16/17.
  */

  object Sort{
    def merge(left: List[Double], right: List[Double]):List[Double]= (left, right)match{
      case(left, Nil) => left
      case(Nil, right) => right
      case(leftHead :: leftTail, rightHead :: rightTail) =>
        if(leftHead < rightHead) leftHead::merge(leftTail, right)
        else rightHead :: merge(left, rightTail)
    }

    def mergeSort(list: List[Double]): List[Double] ={
      val n = list.length / 2
      if (n == 0) list
      else{
        val(left, right) = list.splitAt(n)
        merge(mergeSort(left), mergeSort(right))
      }
    }


  }

