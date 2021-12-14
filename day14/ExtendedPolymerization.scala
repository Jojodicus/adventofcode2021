import scala.io.Source

object ExtendedPolymerization {

  def puzzleOne(): Int = ???

  def puzzleTwo(): Int = ???

  def step(): Int = ???

  def parseStart(value: List[String], map: List[(String, Int)]): Array[Int] = {
    var arr = new Array[Int](map.length)
    val line = value(0)

    for (v <- line.zip(line.tail)) {
      val index = findIndex(map, "" + v._1 + v._2)
      arr.update(index, arr(index) + 1)
    }

    arr
  }

  def parseMap(value: List[String]): List[(String, Int)] = {
    val parts = value.drop(2).zipWithIndex
    parts.foldLeft[List[(String, Int)]](List())((a, b) => a.appended((b._1.split(" -> ")(0) -> b._2)))
  }

  def findIndex(map: List[(String, Int)], value: String): Int = map.filter(_._1.equals(value))(0)._2
  def findValue(map: List[(String, Int)], index: Int): String = map.filter(_._2.equals(index))(0)._1

  def parseTransitions(value: List[String]): List[(String, String, String)] = {
    val transitions = value.drop(2)
    transitions.map(p => {
      val parts = p.split(" -> ")
      (parts(0), parts(0).head + parts(1), parts(1) + parts(0).tail)
    }).toList
  }

  def main(args: Array[String]): Unit = {
    // open input
    val input = Source.fromFile("input")
    val data = input.getLines.toList
    input.close

    // parse
    val map = parseMap(data)
    val start = parseStart(data, map)
    val transition = parseTransitions(data)

    // calculate
    println("start: " + start.toList)
    println(map.toList)
    println(transition)
  }
}
