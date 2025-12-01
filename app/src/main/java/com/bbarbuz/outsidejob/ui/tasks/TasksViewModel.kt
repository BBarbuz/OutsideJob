package com.bbarbuz.outsidejob.ui.tasks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bbarbuz.outsidejob.data.Task

class TasksViewModel : ViewModel() {

    private val allTasks = listOf(
        Task(1/11/2025, "Kupić mleko", false),
        Task(2, "Napisać raport", true),
        Task(3, "Zadzwonić do klienta", false),
        Task(4, "Zapłacić faktury", true),
        Task(5, "Przegląd auta", false),
    )

    // aktualnie wybrany filtr
    private val _filter = MutableLiveData(TasksFilter.ALL)
    val filter: LiveData<TasksFilter> = _filter

    // lista zadań (na razie pusta / mock)
    private val _tasks = MutableLiveData<List<Task>>(emptyList())
    val tasks: LiveData<List<Task>> = _tasks

     fun setFilter(newFilter: TasksFilter) {
        if (_filter.value != newFilter) {
            _filter.value = newFilter
            applyFilter() // później tu zrobisz filtrowanie listy - na razie jest DEMO
        }
    }

    fun loadTasks() {
        // TODO: później podłączysz repozytorium / API / bazę
        // na razie zostaw pusto albo zrób fake dane
        //_tasks.value = demoTasks()
        applyFilter() // DEMO
    }

    // funkcja DEMO
    private fun applyFilter() {
        val currentFilter = _filter.value ?: TasksFilter.ALL
        val filtered = when (currentFilter) {
            TasksFilter.ALL -> allTasks
            TasksFilter.NEW -> allTasks.filter { !it.isCompleted }
            TasksFilter.COMPLETED -> allTasks.filter { it.isCompleted }
            TasksFilter.OVERDUE -> emptyList()      // na razie pusto
            TasksFilter.SUSPENDED -> emptyList()    // na razie pusto
        }
        _tasks.value = filtered
    }
}
