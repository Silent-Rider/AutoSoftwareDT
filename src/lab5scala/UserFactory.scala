package lab5scala

import scala.collection.mutable

class UserFactory {
  
  private val registry: mutable.HashMap[String, UserType[?]] = mutable.HashMap.empty
  
  register(new IntegerType())
  register(new StringType())
  register(new DoubleType())

  private def register(userType: UserType[?]): Unit = registry.put(userType.typeName, userType)
  
  def getTypeNameList: List[String] = registry.keys.toList
  
  def getBuilderByName(name: String): UserType[?] = {
    registry.get(name) match {
      case Some(userType) => userType
      case None => throw new IllegalArgumentException(s"Unknown type: $name")
    }
  }
}
