//package Tree
//
//import scala.annotation.tailrec
//
///**
//  * Created by Frank Cash on 4/14/2017.
//  */
//
//object BST{
//  def empty(implicit c:Double): BST = new Branch(c)
//
//}
//
//sealed abstract  class BST{
//
//  def value: Double
//  def left: BST
//  def right: BST
//  def isEmpty: Boolean
//
//  /**
//    *
//    * @param m Fails with given message 'm'
//    * @return
//    */
//  def fail(m: String) = throw new NoSuchElementException(m)
//
//  def add(x: Double): BST = {
//    if (isEmpty) Branch(x)
//    else if (x < value) Branch(value, left.add(x), right)
//    else if (x > value) Branch(value, left, right.add(x))
//    else this
//  }
//
//  def remove(x: Double): BST ={
//    if(isEmpty) fail("Can't find " + x + " in this tree.")
//    else if(x < value) Branch(value, left.remove(x), right)
//    else if(x > value) Branch(value, left, right.remove(x))
//    else{
//      if(left.isEmpty && right.isEmpty) Leaf
//      else if (left.isEmpty) right
//      else if(right.isEmpty) left
//      else{
//        val succ = right.min
//        Branch(succ, left, right.remove(succ))
//      }
//    }
//  }
//
//  def min: Double = {
//    @tailrec def loop(t: BST, m:Double): Double =
//      if(t.isEmpty) m else loop(t.left, t.value)
//
//    if (isEmpty) fail("Empty Tree.")
//    else loop(left, value)
//  }
//
//  def max: Double = {
//    @tailrec def loop(t: BST, m: Double): Double =
//      if(t.isEmpty) m else loop(t.right, t.value)
//
//    if (isEmpty) fail("Empty Tree.")
//    else loop(right, value)
//  }
//
//  def valuesByDepth: List[Double] ={
//    def loop(s: List[BST]): List[Double]=
//      if (s.isEmpty) Nil
//      else if(s.head.isEmpty) loop(s.tail)
//      else s.head.value :: loop(s.head.right :: s.head.left :: s.tail)
//
//    loop(List(this))
//  }
//}
//
//
///**
//  * Sets up some defaults
//  */
//case object Leaf extends BST{
//  def value: Double = fail("Empty Tree.")
//  def left: BST = fail("Empty Tree.")
//  def right: BST = fail("Empty Tree.")
//  def isEmpty: Boolean = true
//}
//
//case class Branch(value: Double, left: BST = Leaf, right: BST = Leaf) extends BST{
//  def isEmpty: Boolean = false
//}
//
