import scala.io.Source

object BinaryDiagnostic {
  def mostCommon(in: List[String]): List[Int] =
    (for (e <- in.transpose) yield e.count(_ == '1')).map(a => if (a << 1 >= in.length) 1 else 0)

  def puzzleOne(in: List[String]): Int = {
    val mc = mostCommon(in)

    // convert encoded binary to decimal
    val gamma = (for t <- mc.zipWithIndex yield t._1 << (mc.length - t._2 - 1)).sum

    // multiply with bitwise negation
    gamma * (~gamma & (-1 >>> (32 - mc.length)))
  }

  def puzzleTwo(in: List[String]): Int = {
    val width = in(0).length

    // oxygen
    var results = in
    var i = 0
    while (results.length != 1) do { // continuously filter
      results = results.filter(s => s.charAt(i) == mostCommon(results)(i) + '0')
      i += 1
    }
    // decode remaining element
    val resO2 = (for t <- results(0).zipWithIndex yield t._1 - '0' << (width - t._2 - 1)).sum

    // carbon dioxide
    results = in
    i = 0
    while (results.length != 1) do {
      results = results.filter(s => s.charAt(i) != mostCommon(results)(i) + '0')
      i += 1
    }
    val resCO2 = (for t <- results(0).zipWithIndex yield t._1 - '0' << (width - t._2 - 1)).sum

    resO2 * resCO2
  }

  def main(args: Array[String]): Unit = {
    val input = Source.fromFile("input")

    val raw = input.getLines.toList
    println(puzzleOne(raw))
    println(puzzleTwo(raw))

    input.close()
  }
}
