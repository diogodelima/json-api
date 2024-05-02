package com.diogo.json

import org.junit.jupiter.api.Assertions.*
import kotlin.test.Test

class JSONObjectTest {

    @Test
    fun jsonObjectWithPrimitiveFields(){

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
    fun jsonObjectWithAnotherJsonObject(){

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

}