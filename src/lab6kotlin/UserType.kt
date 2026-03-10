package lab6kotlin

import java.io.InputStreamReader

interface UserType<T> {
    fun typeName(): String?
    fun create(): T
    fun clone(o: T?): T?
    fun readValue(input: InputStreamReader): T?
    fun parseValue(string: String?): T?
    fun getTypeComparator(): Comparator<T>
}