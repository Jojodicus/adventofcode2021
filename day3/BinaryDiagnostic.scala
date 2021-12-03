import scala.io.Source

object BinaryDiagnostic {
  def mostCommon(in: List[String]): IndexedSeq[Int] =
    (for (i <- 0 until in(0).length) // iterate row
      yield (for (str <- in) // iterate col
        yield str.charAt(i)
        ).count(_ == '1') // count '1's
      ).map(a => if (a << 1 >= in.length) 1 else 0) // map digit according to frequency


  def puzzleOne(in: List[String]): Int = {
    val width = in(0).length

    // convert encoded binary list to int
    val gamma = (for t <- mostCommon(in).zipWithIndex yield t._1 << (width - t._2 - 1)).sum

    // multiply with bitwise negation
    gamma * (~gamma & (-1 >>> (32 - width)))
  }

  def puzzleTwo(in: List[String]): Int = {
    val width = in(0).length

    // oxygen
    var results = in
    var i = 0
    while (results.length != 1) do { // continuously filter
      results = results.filter(s => s.charAt(i) == mostCommon(results)(i) + '0')
      i = i + 1
    }
    // decode remaining element
    val resO2 = (for t <- results(0).zipWithIndex yield t._1 - '0' << (width - t._2 - 1)).sum

    // carbon dioxide
    results = in
    i = 0
    while (results.length != 1) do {
      results = results.filter(s => s.charAt(i) != mostCommon(results)(i) + '0')
      i = i + 1
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
