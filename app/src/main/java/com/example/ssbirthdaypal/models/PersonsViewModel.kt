package com.example.ssbirthdaypal.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.ssbirthdaypal.reposity.PersonsRepository

class PersonsViewModel : ViewModel() {
    private val repository = PersonsRepository()
    val personsLiveData: LiveData<List<Person>> = repository.personsLiveData
    val errorMessageLiveData: LiveData<String> = repository.errorMessageLiveData
    val updateMessageLiveData: LiveData<String> = repository.updateMessageLiveData

    //init { reload() }

    fun reload() {
        repository.getPersons()
    }

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



}