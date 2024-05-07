package com.diogo.json

import java.io.BufferedReader
import java.io.StringReader
import kotlin.reflect.KProperty1
import kotlin.reflect.KType
import kotlin.reflect.full.createType
import kotlin.reflect.full.isSubtypeOf
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible
import kotlin.reflect.typeOf

class JSONParser(

    private val reader: BufferedReader

) {

    constructor(text: String) : this(BufferedReader(StringReader(text)))

    private lateinit var jsonObject : JSONObject

    companion object {

        fun save(o: Any) : JSONObject {

            val jsonObject = JSONObject()
            val clazz = o::class

            clazz.memberProperties.forEach { member ->
                member.isAccessible = true
                saveField(o, member, jsonObject)
            }

            return jsonObject
        }

        private fun saveField(o: Any, member: KProperty1<out Any, *>, jsonObject: JSONObject){

            val key = member.name
            val value = member.getter.call(o) ?: return

            if (isPrimitive(member.returnType))
                jsonObject.put(key, value)
            else if (isArray(member.returnType))
                jsonObject.put(key, saveArray(value))
            else
                jsonObject.put(key, save(value))

        }

        private fun saveArray(o: Any) : JSONObject {

            val jsonObject = JSONObject()
            val jsonArray = JSONArray()
            val array = o as Array<*>

            jsonObject.put("length", array.size)
            jsonObject.put("elements", jsonArray)

            for (element in array){

                if (element == null)
                    continue

                if (element is Array<*>) {
                    jsonArray.add(saveArray(element))
                    continue
                }

                val type = element::class.createType()

                if (isPrimitive(type))
                    jsonArray.add(element)
                else
                    jsonArray.add(save(element))

            }

            return jsonObject
        }

        private fun isPrimitive(type: KType) : Boolean {

            return type.classifier?.let {
                when (it) {
                    Int::class, Long::class, Double::class, Float::class, Short::class,
                    Byte::class, Char::class, Boolean::class, String::class, -> true
                    else -> false
                }
            }!!

        }

        private fun isArray(type: KType): Boolean {
            return type.isSubtypeOf(typeOf<Array<*>>())
        }

    }

    fun parse() : JSONObject{

        if (::jsonObject.isInitialized)
            return this.jsonObject

        return parse(true)
    }

    private fun parse(first: Boolean) : JSONObject {

        val jsonObject = JSONObject()

        if (first)
            this.jsonObject = jsonObject

        for (line in reader.lines()){

            if (line.contains("}"))
                return jsonObject

            if (line.contains("{")){

                if (line.contains(":")){
                    val key = line.split(":")[0].replace("\"", "").trim()
                    jsonObject.put(key, parse(false))
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

        reader.close()
        throw RuntimeException("Invalid JSON")
    }

    private fun parseToJSONArray() : JSONArray {

        val jsonArray = JSONArray()

        for (line in reader.lines()){

            if (line.contains("]"))
                return jsonArray

            if (line.contains("{")){
                jsonArray.add(parse(false))
                continue
            }

            if (line.contains("[")){
                jsonArray.add(parseToJSONArray())
                continue
            }

            val value = line.replace("\"", "").replace(",", "").trim()

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

        reader.close()
        throw RuntimeException("Invalid JSON")
    }

}