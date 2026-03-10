package lab6kotlin

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class StringType: UserType<String> {

    override fun typeName(): String {
        return String::class.java.getTypeName()
    }

    override fun create(): String {
        return ""
    }

    override fun clone(o: String?): String? {
        return o
    }

    @Throws(IOException::class)
    override fun readValue(input: InputStreamReader): String {
        BufferedReader(input).use { br ->
            val line = br.readLine()
            return line?.trim { it <= ' ' } ?: ""
        }
    }

    override fun parseValue(string: String?): String {
        return string ?: ""
    }

    override fun getTypeComparator(): Comparator<String> {
        return Comparator { o1: Any?, o2: Any? ->
            if (o1 !is String || o2 !is String) {
                throw ClassCastException()
            }
            o1.compareTo(o2)
        }
    }
}