package com.diogo.json

import org.junit.jupiter.api.Assertions.*
import kotlin.test.Test

class JSONParserTest {

    data class Person1(

        var name: String?,
        var age: Long?,
        var birthday: Date?,
        var grades: Array<Long>?,
        var exams: Array<Date>?,

    ){

        constructor() : this(null, null, null, null, null)

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Person1

            if (name != other.name) return false
            if (age != other.age) return false
            if (birthday != other.birthday) return false
            if (grades != null) {
                if (other.grades == null) return false
                if (!grades.contentEquals(other.grades)) return false
            } else if (other.grades != null) return false

            return true
        }

        override fun hashCode(): Int {
            var result = name?.hashCode() ?: 0
            result = 31 * result + (age?.hashCode() ?: 0)
            result = 31 * result + (birthday?.hashCode() ?: 0)
            result = 31 * result + (grades?.contentHashCode() ?: 0)
            return result
        }

    }

    data class Date(

        var year: Long?,
        var month: Long?,
        var day: Long?

    ){

        constructor() : this(null, null, null)

    }

    data class Person(

        private val name: String,
        private val age: Int,
        private val birthday: Date,
        private val grades: List<Any>,
        private val exams: Array<Date>,
        private val array: Array<Array<Int>>

    ) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Person

            if (name != other.name) return false
            if (age != other.age) return false
            if (birthday != other.birthday) return false

            return true
        }

        override fun hashCode(): Int {
            var result = name.hashCode()
            result = 31 * result + age
            result = 31 * result + birthday.hashCode()
            return result
        }
    }

    @Test
    fun saveObjectTest() {

        val expected = """
            {
              "age": 20,
              "array": {
                "length": 3,
                "elements": [
                  {
                    "length": 3,
                    "elements": [
                      3,
                      5,
                      8
                    ]
                  },
                  {
                    "length": 3,
                    "elements": [
                      10,
                      12,
                      14
                    ]
                  },
                  {
                    "length": 3,
                    "elements": [
                      16,
                      18,
                      20
                    ]
                  }
                ]
              },
              "birthday": {
                "day": 1,
                "month": 3,
                "year": 2004
              },
              "exams": {
                "length": 3,
                "elements": [
                  {
                    "day": 27,
                    "month": 5,
                    "year": 2024
                  },
                  {
                    "day": 5,
                    "month": 6,
                    "year": 2024
                  },
                  {
                    "day": 6,
                    "month": 6,
                    "year": 2024
                  }
                ]
              },
              "grades": {
                "length": 5,
                "elements": [
                  16,
                  17,
                  19,
                  20,
                  16
                ]
              },
              "name": "Diogo"
            }
        """.trimIndent()

        val person = Person("Diogo", 20, Date(2004, 3, 1), listOf(16, 17, 19, 20, 16),
            arrayOf(Date(2024, 5, 27), Date(2024, 6, 5), Date(2024, 6, 6)),
            arrayOf(arrayOf(3, 5, 8), arrayOf(10, 12, 14), arrayOf(16, 18, 20))
        )
        val jsonObject = JSONParser.save(person)

        assertEquals(expected, jsonObject.toString())
    }

    @Test
    fun loadObjectTest(){

        val json = """
            {
              "name": "Diogo",
              "age": 20,
              "birthday": {
                "day": 1,
                "month": 3,
                "year": 2004
              },
              "grades": {
                "length": 5,
                "elements": [
                  16,
                  17,
                  19,
                  20,
                  16
                ]
              },
              "exams": {
                "length": 3,
                "elements": [
                  {
                    "day": 27,
                    "month": 5,
                    "year": 2024
                  },
                  {
                    "day": 5,
                    "month": 6,
                    "year": 2024
                  },
                  {
                    "day": 6,
                    "month": 6,
                    "year": 2024
                  }
                ]
              }
            }
        """.trimIndent()

        val person = JSONParser(json).load(Person1::class)
        val expected = "Person1(name=Diogo, age=20, birthday=Date(year=2004, month=3, day=1), grades=[16, 17, 19, 20, 16], exams=[Date(year=2024, month=5, day=27), Date(year=2024, month=6, day=5), Date(year=2024, month=6, day=6)])"

        println(person.toString())
        assertEquals(expected, person.toString())
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