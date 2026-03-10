package lab6kotlin

import java.io.BufferedReader
import java.io.InputStreamReader

class DoubleType: UserType<Double> {

    override fun typeName(): String {
        return Double::class.java.getTypeName()
    }

    override fun create(): Double {
        return 0.0
    }

    override fun clone(o: Double?): Double? {
        return o
    }

    override fun readValue(input: InputStreamReader): Double {
        BufferedReader(input).use { br ->
            val line = br.readLine()
            if (line == null || line.trim { it <= ' ' }.isEmpty()) {
                return 0.0
            }
            return line.trim { it <= ' ' }.toDouble()
        }
    }

    override fun parseValue(string: String?): Double {
        if (string == null || string.trim { it <= ' ' }.isEmpty()) {
            return 0.0
        }
        return string.trim { it <= ' ' }.toDouble()
    }

    override fun getTypeComparator(): Comparator<Double> {
        return Comparator { o1: Any?, o2: Any? ->
            if (o1 !is Double || o2 !is Double) {
                throw ClassCastException()
            }
            o1.compareTo(o2)
        }
    }
}