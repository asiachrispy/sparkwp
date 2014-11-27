//package course
//
//import collection.mutable.Stack
//import org.scalatest._
//
//class ScalaTest extends FlatSpec with Matchers {
//
//  "A Stack" should "pop values in last-in-first-out order" in {
//    val stack = new Stack[Int]
//    stack.push(1)
//    stack.push(2)
//    stack.pop() should be (2)
//    stack.pop() should be (1)
//  }
//
//  it should "throw NoSuchElementException if an empty stack is popped" in {
//    val emptyStack = new Stack[Int]
//    a [NoSuchElementException] should be thrownBy {
//      emptyStack.pop()
//    }
//  }
//
//  describe("A Set") {
//    describe("when empty") {
//      it("should have size 0") {
//        assert(Set.empty.size == 0)
//      }
//
//      it("should produce NoSuchElementException when head is invoked") {
//        intercept[NoSuchElementException] {
//          Set.empty.head
//        }
//      }
//    }
//  }
//}
//
//class SetSuite extends FunSuite {
//
//  test("An empty Set should have size 0") {
//    assert(Set.empty.size == 0)
//  }
//
//  test("Invoking head on an empty Set should produce NoSuchElementException") {
//    intercept[NoSuchElementException] {
//      Set.empty.head
//    }
//  }
//}
//
//
//import org.scalatest.FlatSpec
//
//class SetFlatSpec extends FlatSpec {
//
//  "An empty Set" should "have size 0" in {
//    assert(Set.empty.size == 0)
//  }
//
//  it should "produce NoSuchElementException when head is invoked" in {
//    intercept[NoSuchElementException] {
//      Set.empty.head
//    }
//  }
//}
//
//import org.scalatest.FunSpec
//
//class SetSpec extends FunSpec {
//
//  describe("A Set") {
//    describe("when empty") {
//      it("should have size 0") {
//        assert(Set.empty.size == 0)
//      }
//
//      it("should produce NoSuchElementException when head is invoked") {
//        intercept[NoSuchElementException] {
//          Set.empty.head
//        }
//      }
//    }
//  }
//}