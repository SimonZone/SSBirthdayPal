package com.example.ssbirthdaypal.reposity


import com.example.ssbirthdaypal.models.Person
import retrofit2.Call
import retrofit2.http.*

interface PersonAPIService {
    @GET("Persons")
    fun getAllPersons(): Call<List<Person>>

    @GET("Persons")
    fun getPersonsByUserId(@Query("user_id") userId: String) : Call<List<Person>>

    @GET("Persons/{id}")
    fun getPersonById(@Path("id") id: Int) : Call<Person>

    @POST("Persons")
    fun addPerson(@Body person:Person): Call<Person>

    @DELETE("Persons/{id}")
    fun deletePerson(@Path("id") id: Int): Call<Person>

    @PUT("Person/{id}")
    fun updatePerson(@Path("id") id: Int, @Body person: Person): Call<Person>


}