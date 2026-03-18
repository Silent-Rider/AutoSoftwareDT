package lab5scala.types

import lab5scala.UserType

import java.io.{BufferedReader, InputStreamReader}
import java.util.Comparator
import scala.util.Using

class IntegerType extends UserType[Int] {
  
  override def typeName: String = classOf[Int].getName
  override def create: Int = 0
  override def clone(o: Int): Int = o

  override def readValue(in: InputStreamReader): Int = {
    Using(new BufferedReader(in)) { br =>
      val line = br.readLine()
      if (line == null || line.trim.isEmpty) {
        0
      } else line.trim.toInt
    }.get
  }

  override def parseValue(string: String): Int = {
    if (string == null || string.trim.isEmpty) {
      0
    } else string.trim.toInt
  }

  override def getTypeComparator: Comparator[Int] = {
    (o1: Int, o2: Int) => Integer.compare(o1, o2)
  }
}
