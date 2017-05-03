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
  * Frank Cash
  * Implementation based upon Scalacaster BST <https://raw.githubusercontent.com/vkostyukov/scalacaster/master/src/tree/Tree.scala>
  */

abstract sealed class Tree[+A <% Ordered[A]] {

  /**
    * The value of this tree.
    */
  def value: A

  /**
    * The left child of this tree.
    */
  def left: Tree[A]

  /**
    * The right child of this tree.
    */
  def right: Tree[A]

  /**
    * The size of this tree.
    */
  def size: Int

  /**
    * Checks whether this tree is empty or not.
    */
  def isEmpty: Boolean


  def isValid: Boolean =
    if (isEmpty) true
    else if (left.isEmpty && right.isEmpty) true
    else if (left.isEmpty) right.value >= value && right.isValid
    else if (right.isEmpty) left.value <= value && left.isValid
    else left.value <= value && right.value >= value && left.isValid && right.isValid


  def isBalanced: Boolean = {
    def loop(t: Tree[A]): Int =
      if (t.isEmpty) 0
      else {
        val l = loop(t.left)
        if (l == -1) -1
        else {
          val r = loop(t.right)
          if (r == -1) -1
          else if (math.abs(l - r) > 1) -1
          else 1 + math.max(l, r)
        }
      }

    !(loop(this) == -1)
  }


  def add[B >: A <% Ordered[B]](x: B): Tree[B] =
    if (isEmpty) Tree.make(x)
    else if (x < value) Tree.make(value, left.add(x), right)
    else if (x > value) Tree.make(value, left, right.add(x))
    else this





  def contains[B >: A <% Ordered[B]](x: B): Boolean = {
    def loop(t: Tree[A], c: Option[A]): Boolean =
      if (t.isEmpty) check(c)
      else if (x < t.value) loop(t.left, c)
      else loop(t.right, Some(t.value))

    def check(c: Option[A]): Boolean = c match {
      case Some(y) if y == x => true
      case _ => false
    }

    loop(this, None)
  }


  def foreach(f: (A) => Unit): Unit =
    if (!isEmpty) {
      left.foreach(f)
      f(value)
      right.foreach(f)
    }

  def map[B <% Ordered[B]](f: (A) => B): Tree[B] =
    if (isEmpty) Tree.empty
    else Tree.make(f(value), left.map(f), right.map(f))


  /**
    * Searches for the minimal element of this tree.
    */
  def min: A = {
    def loop(t: Tree[A], m: A): A =
      if (t.isEmpty) m
      else loop(t.left, t.value)

    if (isEmpty) fail("An empty tree.")
    else loop(left, value)
  }

  /**
    * Searches for the maximal element of this tree.
    */
  def max: A = {
    def loop(t: Tree[A], m: A): A =
      if (t.isEmpty) m
      else loop(t.right, t.value)

    if (isEmpty) fail("An empty tree.")
    else loop(right, value)
  }

  /**
    * Calculates the height of this tree.
    */
  def height: Int =
    if (isEmpty) 0
    else 1 + math.max(left.height, right.height)



  /**
    * Searches for the successor of given element 'x'.
    */
  def successor[B >: A <% Ordered[B]](x: B): A = {
    def forward(t: Tree[A], p: List[Tree[A]]): A =
      if (t.isEmpty) fail("Can't find " + x + " in this tree.")
      else if (x < t.value) forward(t.left, t :: p)
      else if (x > t.value) forward(t.right, t :: p)
      else if (!t.right.isEmpty) t.right.min
      else backward(t, p)

    def backward(t: Tree[A], p: List[Tree[A]]): A =
      if (p.isEmpty) fail("The " + x + " doesn't have an successor.")
      else if (t == p.head.right) backward(p.head, p.tail)
      else p.head.value

    forward(this, Nil)
  }



  /**
    * Searches for the lower bound element of given 'x'.
    */
  def lowerBound[B >: A <% Ordered[B]](x: B): A =
    if (isEmpty) fail("Tree is empty.")
    else if (x < value)
      if (!left.isEmpty) left.lowerBound(x)
      else value
    else if (x > value)
      if (!right.isEmpty) { val v = right.lowerBound(x); if (v > x) value else v }
      else value
    else value

  /**
    * Calculates the number of elements that less or equal to given 'x'.
    */
  def rank[B >: A <% Ordered[B]](x: B): Int =
    if (isEmpty) 0
    else if (x < value) left.rank(x)
    else if (x > value) 1 + left.size + right.rank(x)
    else left.size

  /**
    * Searches for the upper bound element of given 'x'.
    */
  def upperBound[B >: A <% Ordered[B]](x: B): A =
    if (isEmpty) fail("Tree is empty.")
    else if (x < value)
      if (!left.isEmpty) { val v = left.upperBound(x); if (v < x) value else v }
      else value
    else if (x > value)
      if (!right.isEmpty) right.upperBound(x)
      else value
    else value

  /**
    * Calculates the path for given element 'x'.
    */
  def path[B >: A <% Ordered[B]](x: B): List[Tree[A]] =
    if (isEmpty) fail("Can't find " + x + " in this tree.")
    else if (x < value) this :: left.path(x)
    else if (x > value) this :: right.path(x)
    else List(this)


  /**
    * Performs the DFS and dumps values to the list.
    */
  def valuesByDepth: List[A] = {
    def loop(s: List[Tree[A]]): List[A] =
      if (s.isEmpty) Nil
      else if (s.head.isEmpty) loop(s.tail)
      else s.head.value :: loop(s.head.right :: s.head.left :: s.tail)

    loop(List(this))
  }

  /**
    * Fails with given message 'm'.
    */
  def fail(m: String) = throw new NoSuchElementException(m)
}

case object Leaf extends Tree[Nothing] {
  def value: Nothing = fail("An empty tree.")
  def left: Tree[Nothing] = fail("An empty tree.")
  def right: Tree[Nothing] = fail("An empty tree.")
  def size: Int = 0

  def isEmpty: Boolean = true
}

case class Branch[A <% Ordered[A]](value: A,
                                   left: Tree[A],
                                   right: Tree[A],
                                   size: Int) extends Tree[A] {
  def isEmpty: Boolean = false
}

object Tree {

  /**
    * An empty tree.
    */
  def empty[A]: Tree[A] = Leaf

  /**
    * A smart constructor for tree's branch.
    */
  def make[A <% Ordered[A]](x: A, l: Tree[A] = Leaf, r: Tree[A] = Leaf): Tree[A] =
    Branch(x, l, r, l.size + r.size + 1)

  /**
    * Creates a new tree from given sequence 'xs'.
    */
  def apply[A <% Ordered[A]](xs: A*): Tree[A] = {
    var r: Tree[A] = Tree.empty
    for (x <- xs) r = r.add(x)
    r
  }

  /**
    * Creates a new balanced tree from given sorted array 'a'.
    */
  def fromSortedArray[A <% Ordered[A]](a: Array[A]): Tree[A] = {
    def loop(l: Int, r: Int): Tree[A] =
      if (l == r) Tree.empty
      else {
        val p = (l + r) / 2
        Tree.make(a(p), loop(l, p), loop(p + 1, r))
      }

    loop(0, a.length)
  }

  /**
    * Creates a new balanced tree from given sorted list 'l'.
    *
    * http://www.geeksforgeeks.org/sorted-linked-list-to-balanced-bst/
    */
  def fromSortedList[A <% Ordered[A]](l: List[A]): Tree[A] = {
    def loop(ll: List[A], n: Int): (List[A], Tree[A]) =
      if (n == 0) (ll, Tree.empty)
      else {
        val (lt, left) = loop(ll, n / 2)
        val (rt, right) = loop(lt.tail, n - 1 - n / 2)
        (rt, Tree.make(lt.head, left, right))
      }

    loop(l, l.length)._2
  }

}
