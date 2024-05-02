package com.diogo.json

import org.junit.jupiter.api.Assertions.*
import kotlin.test.Test

class JSONArrayTest {

    @Test
    fun simpleJsonArrayTest(){

        val actual = JSONArray()
        actual.add("Diogo", "Pedro", "Afonso", "Carlos", "João")

        val expected = """
            [
              "Diogo",
              "Pedro",
              "Afonso",
              "Carlos",
              "João"
            ]
        """.trimIndent()

        assertEquals(expected, actual.toString())
    }

    @Test
    fun jsonArrayWithObjectsTest(){

        val person = JSONObject()

        person.put("name", "Diogo")
        person.put("surname", "Lima")
        person.put("age", 20)

        val person1 = JSONObject()

        person1.put("name", "Pedro")
        person1.put("surname", "Silva")
        person1.put("age", 27)

        val person2 = JSONObject()

        person2.put("name", "Ricardo")
        person2.put("surname", "Carvalho")
        person2.put("age", 34)

        val actual = JSONArray()
        actual.add(person, person1, person2)

        val expected = """
            [
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
        """.trimIndent()

        assertEquals(expected, actual.toString())
    }

    @Test
    fun jsonArrayWithOtherArrayTest(){

        val person = JSONObject()

        person.put("name", "Diogo")
        person.put("surname", "Lima")
        person.put("age", 20)

        val person1 = JSONObject()

        person1.put("name", "Pedro")
        person1.put("surname", "Silva")
        person1.put("age", 27)

        val person2 = JSONObject()

        person2.put("name", "Ricardo")
        person2.put("surname", "Carvalho")
        person2.put("age", 34)

        val persons = JSONArray()
        persons.add(person, person1, person2)

        val actual = JSONArray()
        actual.add(persons, persons, persons)

        val expected = """
            [
              [
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
              ],
              [
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
              ],
              [
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
            ]
        """.trimIndent()

        assertEquals(expected, actual.toString())
    }

}