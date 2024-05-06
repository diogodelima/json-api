package com.diogo.json

class JSONObject(

    private val map: MutableMap<String, Any?> = LinkedHashMap()

) {

    fun put(key: String, value: Any?){
        this.map[key] = value
    }

    fun get(key: String) : Any? {
        return this.map[key]
    }

    override fun toString(): String {
        return toString(2)
    }

    fun toString(indent: Int): String {

        var i = 0
        val builder = StringBuilder()
        builder.append("{\n")

        this.map.forEach { entry ->

            val key = entry.key
            val value = entry.value

            for (j in 0 until indent)
                builder.append(" ")

            builder
                .append("\"${key}\": ")

            when (value) {
                is JSONObject -> builder.append(value.toString(indent + 2))
                is JSONArray -> builder.append(value.toString(indent + 2))
                is String -> builder.append("\"${value}\"")
                else -> builder.append(value)
            }

            if (i++ < this.map.size - 1)
                builder.append(",")

            builder.append("\n")
        }

        for (j in 0 until indent - 2)
            builder.append(" ")

        builder.append("}")
        return builder.toString()
    }

}