package com.diogo.json

import java.io.BufferedReader
import java.io.StringReader

class JSONParser(

    private val reader: BufferedReader

) {

    constructor(text: String) : this(BufferedReader(StringReader(text)))

    fun parse() : JSONObject {

        val jsonObject = JSONObject()

        for (line in reader.lines()){

            if (line.contains("}"))
                return jsonObject

            if (line.contains("{")){

                if (line.contains(":")){
                    val key = line.split(":")[0].replace("\"", "").trim()
                    jsonObject.put(key, parse())
                }

                continue
            }

            val key = line.split(":")[0].replace("\"", "").trim()

            if (line.contains("[")){
                jsonObject.put(key, parseToJSONArray())
                continue
            }

            val value = line.replace("\"", "").replace(",", "").split(":")[1].trim()

            try {
                if (value.contains(".")) {
                    val n = value.toDouble()
                    jsonObject.put(key, n)
                }else{
                    val n = value.toLong()
                    jsonObject.put(key, n)
                }
                continue
            }catch (ignored: Exception){}

            jsonObject.put(key, value)
        }

        return jsonObject
    }

    private fun parseToJSONArray() : JSONArray {

        val jsonArray = JSONArray()

        for (line in reader.lines()){

            if (line.contains("]"))
                return jsonArray

            if (line.contains("{")){
                jsonArray.add(parse())
                continue
            }

            if (line.contains("[")){
                jsonArray.add(parseToJSONArray())
                continue
            }

            val value = line.replace("\"", "").replace(",", "").split(":")[1].trim()

            try {
                if (value.contains(".")) {
                    val n = value.toDouble()
                    jsonArray.add(n)
                }else{
                    val n = value.toLong()
                    jsonArray.add(n)
                }
                continue
            }catch (ignored: Exception){}

        }

        return jsonArray
    }

}