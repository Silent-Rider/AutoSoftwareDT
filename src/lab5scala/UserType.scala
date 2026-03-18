package lab5scala

import java.io.InputStreamReader
import java.util.Comparator

trait UserType[T] {
  def typeName: String
  def create: T
  def clone(o: T): T
  def readValue(in: InputStreamReader): T
  def parseValue(string: String): T
  def getTypeComparator: Comparator[T]
}
