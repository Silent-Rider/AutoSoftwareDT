package lab5scala

import java.io.{BufferedReader, InputStreamReader}
import java.util.Comparator
import scala.util.Using

class StringType extends UserType[String] {

  override def typeName: String = classOf[String].getName

  override def create: String = ""

  override def clone(o: String): String = o

  override def readValue(in: InputStreamReader): String = {
    Using(new BufferedReader(in)) { br =>
      val line = br.readLine()
      if (line == null) {
        ""
      } else {
        val trimmed = line.trim
        if (trimmed.isEmpty) "" else trimmed
      }
    }.get
  }

  override def parseValue(string: String): String = {
    if (string == null) {
      ""
    } else {
      val trimmed = string.trim
      if (trimmed.isEmpty) "" else trimmed
    }
  }

  override def getTypeComparator: Comparator[String] = {
    (o1: String, o2: String) => o1.compareTo(o2)
  }
}
