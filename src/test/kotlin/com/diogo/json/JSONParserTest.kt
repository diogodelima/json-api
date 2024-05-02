package com.diogo.json

import org.junit.jupiter.api.Assertions.*
import kotlin.test.Test

class JSONParserTest {

    @Test
    fun parseSimpleObjectTest(){

        val expected = """
            {
              "name": "Diogo",
              "surname": "Lima",
              "age": 20,
              "birthday": {
                "year": 2004,
                "month": 3,
                "day": 1
              },
              "profession": "computer engineer",
              "wallet": {
                "total": 12.5,
                "5€": 2,
                "2€": 1,
                "0,5€": 1
              }
            }
        """.trimIndent()

        val jsonObject = JSONParser(expected).parse()
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