package course

/**
 * Description : some words
 * Author: chris 
 * Date: 2014/9/13
 */
class FileD {

}

object FileD {
  def main(args: Array[String]) {
    val fs = (new java.io.File(".")).listFiles()
    for (f <- fs) {
      println(f)
    }
  }
}
