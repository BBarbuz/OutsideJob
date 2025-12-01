package com.bbarbuz.outsidejob.ui.tasks

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuProvider
import androidx.core.view.MenuHost
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.bbarbuz.outsidejob.R
import com.bbarbuz.outsidejob.databinding.FragmentTasksBinding
import com.bbarbuz.outsidejob.ui.tasks.TasksViewModel

class TasksFragment : Fragment() {
    private var _binding: FragmentTasksBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TasksViewModel by viewModels()
    private lateinit var adapter: TasksAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        // RecyclerView
        val recycler = view.findViewById<RecyclerView>(R.id.tasksRecyclerView)
        adapter = TasksAdapter()
        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = adapter

        // Menu
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                //menu.clear()
                menuInflater.inflate(R.menu.menu_tasks, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.action_tasks_all -> {
                        viewModel.setFilter(TasksFilter.ALL); true
                    }

                    R.id.action_tasks_new -> {
                        viewModel.setFilter(TasksFilter.NEW); true
                    }

                    R.id.action_tasks_completed -> {
                        viewModel.setFilter(TasksFilter.COMPLETED); true
                    }

                    R.id.action_tasks_overdue -> {
                        viewModel.setFilter(TasksFilter.OVERDUE); true
                    }

                    R.id.action_tasks_suspended -> {
                        viewModel.setFilter(TasksFilter.SUSPENDED); true
                    }

                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        // obserwacje – na razie tylko szkielety
        viewModel.filter.observe(viewLifecycleOwner) { filter ->
            // TODO: później odśwież UI / RecyclerView pod aktualny filtr
        }

        viewModel.tasks.observe(viewLifecycleOwner) { tasks ->
            // TODO: później podłącz adapter
            adapter.submitList(tasks)
        }

        // startowy load – na razie nic nie robi, ale jest miejsce
        viewModel.loadTasks()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTasksBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textDesc = getString(R.string.tasks_fragment_decription)
//        binding.textDescription.text = textDesc

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}