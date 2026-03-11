package lab6kotlin

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class IntegerType: UserType<Int> {
    override fun typeName(): String {
        return Int::class.java.getTypeName()
    }

    override fun create(): Int {
        return 0
    }

    override fun clone(o: Int?): Int? {
        return o
    }

    override fun readValue(input: InputStreamReader): Int {
        BufferedReader(input).use { br ->
            val line = br.readLine()
            if (line == null || line.trim { it <= ' ' }.isEmpty()) {
                return 0
            }
            return line.trim { it <= ' ' }.toInt()
        }
    }

    override fun parseValue(string: String?): Int {
        if (string == null || string.trim { it <= ' ' }.isEmpty()) {
            return 0
        }
        return string.trim { it <= ' ' }.toInt()
    }

    override fun getTypeComparator(): Comparator<Int> {
        return Comparator { o1: Any?, o2: Any? ->
            if (o1 !is Int || o2 !is Int) {
                throw ClassCastException()
            }
            o1.compareTo(o2)
        }
    }
}