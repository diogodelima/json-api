package com.diogo.json

class JSONArray(

    private val list: MutableList<Any> = ArrayList()

) {

    fun add(value: Any){
        this.list.add(value)
    }

    fun add(vararg values: Any){
        this.list.addAll(values)
    }

    override fun toString(): String {
        return toString(2)
    }

    fun toString(indent: Int): String {

        val builder = StringBuilder()

        builder.append("[\n")

        for ((i, value) in list.withIndex()){

            for (j in 0 until indent)
                builder.append(" ")

            when (value) {
                is JSONObject -> builder.append(value.toString(indent + 2))
                is JSONArray -> builder.append(value.toString(indent + 2))
                is String -> builder.append("\"${value}\"")
                else -> builder.append(value)
            }

            if (i < this.list.size - 1)
                builder.append(",")

            builder.append("\n")
        }

        for (j in 0 until indent - 2)
            builder.append(" ")

        builder.append("]")
        return builder.toString()
    }

}