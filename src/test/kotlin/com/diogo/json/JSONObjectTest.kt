package com.diogo.json

import org.junit.jupiter.api.Assertions.*
import kotlin.test.Test

class JSONObjectTest {

    @Test
    fun jsonObjectWithPrimitiveFieldsTest(){

        val actual = JSONObject()

        actual.put("name", "Diogo")
        actual.put("surname", "Lima")
        actual.put("age", 20)

        val expected = """
            {
              "name": "Diogo",
              "surname": "Lima",
              "age": 20
            }
        """.trimIndent()

        assertEquals(expected, actual.toString())
    }

    @Test
    fun jsonObjectWithAnotherJsonObjectTest(){

        val actual = JSONObject()

        actual.put("name", "Diogo")
        actual.put("surname", "Lima")
        actual.put("age", 20)

        val birthday = JSONObject()
        birthday.put("year", 2004)
        birthday.put("month", 3)
        birthday.put("day", 1)

        actual.put("birthday", birthday)
        actual.put("profession", "computer engineer")

        val wallet = JSONObject()
        wallet.put("total", 12.5)
        wallet.put("5€", 2)
        wallet.put("2€", 1)
        wallet.put("0,5€", 1)

        actual.put("wallet", wallet)

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

        assertEquals(expected, actual.toString())
    }

    @Test
    fun jsonObjectWithSimpleArrayTest(){

        val actual = JSONObject()

        actual.put("size", 5)

        val array = JSONArray()
        array.add(1, 2, 3, 4, 5)
        actual.put("array", array)

        val expected = """
            {
              "size": 5,
              "array": [
                1,
                2,
                3,
                4,
                5
              ]
            }
        """.trimIndent()

        assertEquals(expected, actual.toString())
    }

    @Test
    fun jsonObjectWithArrayThatContainsOtherObjectsTest(){

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

        val actual = JSONObject()
        actual.put("size", 3)
        actual.put("persons", persons)

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

        assertEquals(expected, actual.toString())
    }

    @Test
    fun jsonObjectWithArrayThatContainsOtherObjectsWithArrayTest(){

        val person = JSONObject()
        var grades = JSONArray()
        grades.add(17, 18, 18, 19, 20)

        person.put("name", "Diogo")
        person.put("surname", "Lima")
        person.put("age", 20)
        person.put("grades", grades)

        val person1 = JSONObject()
        grades = JSONArray()
        grades.add(13, 12, 18, 14, 16)

        person1.put("name", "Pedro")
        person1.put("surname", "Silva")
        person1.put("age", 27)
        person1.put("grades", grades)

        val person2 = JSONObject()
        grades = JSONArray()
        grades.add(13, 10, 12, 14, 13)

        person2.put("name", "Ricardo")
        person2.put("surname", "Carvalho")
        person2.put("age", 34)
        person2.put("grades", grades)

        val persons = JSONArray()
        persons.add(person, person1, person2)

        val actual = JSONObject()
        actual.put("size", 3)
        actual.put("persons", persons)

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

        assertEquals(expected, actual.toString())
    }

}