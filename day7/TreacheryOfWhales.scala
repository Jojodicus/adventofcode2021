import scala.io.Source

object TreacheryOfWhales {
  def median(s: List[Int]): Int =
    s.sortWith(_ < _).drop(s.length / 2).head

  def puzzleOne(input: List[Int]): Int =
    input.map(x => math.abs(x - median(input))).sum

  def puzzleTwo(input: List[Int]): Int =
    input.map(x => List.range(1, math.abs(x - input.sum / input.length) + 1).sum).sum

  def main(args: Array[String]): Unit = {
    val input = Source.fromFile("input")
    val data = input.getLines.next.split(",").map(Integer.parseInt).toList
    input.close()

    println(puzzleOne(data))
    println(puzzleTwo(data))
  }
}
