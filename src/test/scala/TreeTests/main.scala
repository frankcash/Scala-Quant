package TreeTests

import Tree.Sort
import org.scalatest.FunSuite

/**
  * Created by Frank Cash on 5/2/17.
  */
object main extends FunSuite{
  val nums: List[Double] = List(24.02, 24.01, 24.03, 24.05, 22.001, 29.001)

  test("Tests Merge Sort"){
    val sorted = Sort.mergeSort(nums)
    println("tst")
    assert(sorted.head == 22.001)
  }

}
