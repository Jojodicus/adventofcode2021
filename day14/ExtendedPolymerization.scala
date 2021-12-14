import scala.io.Source

object ExtendedPolymerization {

  def puzzleOne(start: Array[Long], transitions: Map[Int, (Int, Int)], map: List[(String, Int)]): Long = {
    // step 10 times
    val res = 1.to(10).foldLeft(start)((arr, _) => step(arr, transitions))

    // count: TODO

    println("res : " + res.toList)
    res.sum
  }

  def puzzleTwo(): Int = ??? //TODO

  def step(before: Array[Long], transitions: Map[Int, (Int, Int)]): Array[Long] = {
    val newArr = new Array[Long](before.length)

    // add transitions
    for (i <- 0 until before.length) {
      newArr(transitions(i)._1) += before(i)
      newArr(transitions(i)._2) += before(i)
    }

    newArr
  }

  // "bidirectional map"
  def parseMap(value: List[String]): List[(String, Int)] =
    value.drop(2).zipWithIndex // transitions
      .foldLeft(List(): List[(String, Int)])(
        // parse and add to list
        (a, b) => a :+ ((b._1.split(" -> ")(0) -> b._2))
      )

  // find index from value
  def findIndex(map: List[(String, Int)], value: String): Int = map.filter(_._1.equals(value))(0)._2

  // find value from index
  def findValue(map: List[(String, Int)], index: Int): String = map.filter(_._2.equals(index))(0)._1

  def parseStart(value: List[String], map: List[(String, Int)]): Array[Long] = {
    val arr = new Array[Long](map.length)
    val line = value(0)

    // add combination to start
    for (v <- line.zip(line.tail))
      arr(findIndex(map, "" + v._1 + v._2)) += 1

    arr
  }

  def parseTransitions(value: List[String], map: List[(String, Int)]): Map[Int, (Int, Int)] = {
    // only transitions
    val transitions = value.drop(2)

    // fill map with transitions
    transitions.foldLeft(Map(): Map[Int, (Int, Int)])((m, s) => {
      val parts = s.split(" -> ")

      // add to list
      m + (findIndex(map, parts(0)) -> (
        findIndex(map, parts(0).head + parts(1)), findIndex(map, parts(1) + parts(0).tail)
      ))
    })
  }

  def main(args: Array[String]): Unit = {
    // open input
    val input = Source.fromFile("input")
    val data = input.getLines.toList
    input.close

    // parse
    val map = parseMap(data)
    val start = parseStart(data, map)
    val transition = parseTransitions(data, map)

    // calculate
    println(map.toList)
    println(transition.toList)

    println(puzzleOne(start, transition, map))
  }
}
