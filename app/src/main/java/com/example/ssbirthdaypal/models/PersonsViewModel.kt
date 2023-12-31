package com.example.ssbirthdaypal.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.ssbirthdaypal.reposity.PersonsRepository

class PersonsViewModel : ViewModel() {
    private val repository = PersonsRepository()
    val personsLiveData: LiveData<List<Person>> = repository.personsLiveData
    val errorMessageLiveData: LiveData<String> = repository.errorMessageLiveData

    fun reloadWithUser(email: String) {
        repository.getPersonsByUserId(email)
    }

    operator fun get(index: Int): Person? {
        return personsLiveData.value?.get(index)
    }

    fun add(person: Person) {
        repository.add(person)
    }

    fun delete(id: Int) {
        repository.delete(id)
    }

    fun update(person: Person) {
        repository.update(person)
    }

    fun sortAndFilter(
        sortCriteria: Int,
        descending: Boolean = false,
        filterCriteria: Int,
        filterSearch: String? = null
    )  {
        repository.sortAndFilter(sortCriteria, descending, filterCriteria, filterSearch)
    }


}
