package com.example.ssbirthdaypal.reposity

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.ssbirthdaypal.models.Person
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PersonsRepository {
    private val baseUrl = "https://birthdaysrest.azurewebsites.net/api/"

    private val personAPIService: PersonAPIService
    val personsLiveData: MutableLiveData<List<Person>> = MutableLiveData<List<Person>>()
    val errorMessageLiveData: MutableLiveData<String> = MutableLiveData()
    val updateMessageLiveData: MutableLiveData<String> = MutableLiveData()

    init {
        val build: Retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        personAPIService = build.create(PersonAPIService::class.java)

    }

    fun getPersons() {
        personAPIService.getAllPersons().enqueue(object : Callback<List<Person>> {
            override fun onResponse(call: Call<List<Person>>, response: Response<List<Person>>) {
                if (response.isSuccessful){
                    val p: List<Person>? = response.body()
                    personsLiveData.postValue(p!!)
                    errorMessageLiveData.postValue("")
                } else {
                    val message = response.code().toString() + " " + response.message()
                    errorMessageLiveData.postValue(message)
                    Log.d("getPersons response", message)
                }
            }

            override fun onFailure(call: Call<List<Person>>, t: Throwable) {
                errorMessageLiveData.postValue(t.message)
                Log.d("getPersons failure", t.message!!)
            }
        })
    }

    fun getPersonsByUserId(userId: String) {
        personAPIService.getPersonsByUserId(userId).enqueue(object : Callback<List<Person>> {
            override fun onResponse(call: Call<List<Person>>, response: Response<List<Person>>) {
                if (response.isSuccessful){
                    val p: List<Person>? = response.body()
                    personsLiveData.postValue(p!!)
                    errorMessageLiveData.postValue("")
                    Log.d("getPersonsById http request", call.request().toString())
                } else {
                    val message = response.code().toString() + " " + response.message()
                    errorMessageLiveData.postValue(message)
                    Log.d("getPersonsById response", message)
                }
            }

            override fun onFailure(call: Call<List<Person>>, t: Throwable) {
                errorMessageLiveData.postValue(t.message)
                Log.d("getPersonsById failure", t.message!!)
            }
        })
    }

    fun add(person: Person) {
        personAPIService.addPerson(person).enqueue(object : Callback<Person> {
            override fun onResponse(call: Call<Person>, response: Response<Person>) {
                if (response.isSuccessful) {
                    Log.d("add response", "Added: " + response.body())
                    updateMessageLiveData.postValue("Added: " + response.body())
                } else {
                    val message = response.code().toString() + " " + response.message()
                    errorMessageLiveData.postValue(message)
                    Log.d("add response", message)
                }
            }

            override fun onFailure(call: Call<Person>, t: Throwable) {
                errorMessageLiveData.postValue(t.message)
                Log.d("add failure", t.message!!)
            }
        })
    }

    fun delete(id: Int) {
        personAPIService.deletePerson(id).enqueue(object : Callback<Person> {
            override fun onResponse(call: Call<Person>, response: Response<Person>) {
                if (response.isSuccessful) {
                    Log.d("delete response", "Deleted: " + response.body())
                    updateMessageLiveData.postValue("Deleted: " + response.body())
                } else {
                    val message = response.code().toString() + " " + response.message()
                    errorMessageLiveData.postValue(message)
                    Log.d("delete response", message)
                }
            }

            override fun onFailure(call: Call<Person>, t: Throwable) {
                errorMessageLiveData.postValue(t.message)
                Log.d("delete failure", t.message!!)
            }
        })
    }

    fun getById(id: Int) {
        personAPIService.getPersonById(id).enqueue(object : Callback<Person> {
            override fun onResponse(call: Call<Person>, response: Response<Person>) {
                if (response.isSuccessful) {
                    Log.d("getById response", "Got: " + response.body())
                    updateMessageLiveData.postValue("Got: " + response.body())
                } else {
                    val message = response.code().toString() + " " + response.message()
                    errorMessageLiveData.postValue(message)
                    Log.d("getById response", message)
                }
            }

            override fun onFailure(call: Call<Person>, t: Throwable) {
                errorMessageLiveData.postValue(t.message)
                Log.d("getById failure", t.message!!)
            }
        })
    }

    fun update(person: Person) {
        personAPIService.updatePerson(person.id, person).enqueue(object : Callback<Person> {
            override fun onResponse(call: Call<Person>, response: Response<Person>) {
                if (response.isSuccessful) {
                    Log.d("update response", "Updated: " + response.body())
                    updateMessageLiveData.postValue("Updated: " + response.body())
                } else {
                    val message = response.code().toString() + " " + response.message()
                    errorMessageLiveData.postValue(message)
                    Log.d("update response", message)
                }
            }

            override fun onFailure(call: Call<Person>, t: Throwable) {
                errorMessageLiveData.postValue(t.message)
                Log.d("update failure", t.message!!)
            }
        })
    }

    fun sortAndFilter(
        sortCriteria: String? = null,
        descending: Boolean = false,
        filterCriteria: Int? = null,
        filterSearch: String? = null
    ) {
        val currentData = personsLiveData.value ?: return // If data is null, return

        // Apply sorting if sort criteria is provided
        val sortedData = sortData(currentData, sortCriteria, descending)

        // Apply filtering if filter criteria and value are provided
        val filteredData = filterData(sortedData, filterCriteria, filterSearch)

        // Update the LiveData with the sorted and filtered data
        personsLiveData.value = filteredData
    }

    private fun sortData(data: List<Person>, sortCriteria: String?, descending: Boolean): List<Person> {
        return when (sortCriteria) {
            "Name" -> if (descending) data.sortedByDescending { it.name } else data.sortedBy { it.name }
            "Age" -> if (descending) data.sortedByDescending { it.age } else data.sortedBy { it.age }
            "Day" -> if (descending) data.sortedByDescending { it.birthDayOfMonth } else data.sortedBy { it.birthDayOfMonth }
            "Month" -> if (descending) data.sortedByDescending { it.birthMonth } else data.sortedBy { it.birthMonth }
            "Year" -> if (descending) data.sortedByDescending { it.birthYear } else data.sortedBy { it.birthYear }
            else -> data // Default sorting
        }
    }

    private fun filterData(data: List<Person>, filterCriteria: Int?, filterSearch: String?): List<Person> {
        return when (filterCriteria) {
            1 -> data.filter { it.name.contains(filterSearch ?: "", ignoreCase = true) }
            2 -> data.filter { it.age.toString().contains(filterSearch ?: "") }
            3 -> data.filter { it.birthDayOfMonth.toString().contains(filterSearch ?: "") }
            4 -> data.filter { it.birthMonth.toString().contains(filterSearch ?: "") }
            5 -> data.filter { it.birthYear.toString().contains(filterSearch ?: "") }
            else -> data // Default filtering (no filter)
        }
    }
}