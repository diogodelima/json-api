package com.diogo.json

import org.junit.jupiter.api.Assertions.*
import java.time.LocalDate
import kotlin.properties.Delegates
import kotlin.test.Test

class JSONParserTest {

    data class Date(

        private val year: Int,
        private val month: Int,
        private val day: Int

    )

    data class Person(

        private val name: String,
        private val age: Int,
        private val birthday: Date

    )

    @Test
    fun saveSimpleObjectTest() {

        val expected = """
            {
              "age": 20,
              "birthday": {
                "day": 1,
                "month": 3,
                "year": 2004
              },
              "name": "Diogo"
            }
        """.trimIndent()

        val person = Person("Diogo", 20, Date(2004, 3, 1))
        val jsonObject = JSONParser.save(person)

        println(jsonObject.toString())

        assertEquals(expected, jsonObject.toString())
    }

    @Test
    fun parseObjectWithArrayTest(){

        val expected = """
            {
              "size": 3,
              "persons": [
                {
                  "name": "Diogo",
                  "surname": "Lima",
                  "age": 20
                },
                {
                  "name": "Pedro",
                  "surname": "Silva",
                  "age": 27
                },
                {
                  "name": "Ricardo",
                  "surname": "Carvalho",
                  "age": 34
                }
              ]
            }
        """.trimIndent()

        val jsonObject = JSONParser(expected).parse()
        assertEquals(expected, jsonObject.toString())
    }

    @Test
    fun parseObjectWithObjectAndArrayTest(){

        val expected = """
            {
              "size": 3,
              "persons": [
                {
                  "name": "Diogo",
                  "surname": "Lima",
                  "age": 20,
                  "grades": [
                    17,
                    18,
                    18,
                    19,
                    20
                  ]
                },
                {
                  "name": "Pedro",
                  "surname": "Silva",
                  "age": 27,
                  "grades": [
                    13,
                    12,
                    18,
                    14,
                    16
                  ]
                },
                {
                  "name": "Ricardo",
                  "surname": "Carvalho",
                  "age": 34,
                  "grades": [
                    13,
                    10,
                    12,
                    14,
                    13
                  ]
                }
              ]
            }
        """.trimIndent()

        val jsonObject = JSONParser(expected).parse()
        assertEquals(expected, jsonObject.toString())
    }

}