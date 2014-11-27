package course

/**
 * Description : some words
 * Author: chris 
 * Date: 2014/9/13
 */
class ApplyOps {

}

class AppT(val name: String) {

  def apply()   = {
    println("class apply")
  }

  def show {
    println("appt do show. " + name)
  }
}

object AppT {
  def apply(name: String) = {
    new AppT("nick " + name)
  }
}

object ApplyOps extends App {

  val apclass = new AppT("asia")
  apclass.show
//  println(apclass)

  // use apply
  val app = AppT
//  ap.show

  val ap = AppT("chris")
  app.apply("cici").show

}
