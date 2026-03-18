package lab5scala.types

import lab5scala.UserType

import java.io.{BufferedReader, InputStreamReader}
import java.util.Comparator
import scala.util.Using

class DoubleType extends UserType[Double] {

  override def typeName: String = classOf[Double].getName
  override def create: Double = 0.0
  override def clone(o: Double): Double = o

  override def readValue(in: InputStreamReader): Double = {
    Using(new BufferedReader(in)) { br =>
      val line = br.readLine()
      if (line == null || line.trim.isEmpty) {
        0.0
      } else {
        line.trim.toDouble
      }
    }.get
  }

  override def parseValue(string: String): Double = {
    if (string == null || string.trim.isEmpty) {
      0.0
    } else {
      string.trim.toDouble
    }
  }

  override def getTypeComparator: Comparator[Double] = {
    (o1: Double, o2: Double) => java.lang.Double.compare(o1, o2)
  }
}
