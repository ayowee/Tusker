package com.example.tusker

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.tusker.databinding.FragmentNewTaskSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class NewTaskSheet(var taskItem: TaskItem?) : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentNewTaskSheetBinding
    private lateinit var taskViewModel: TaskViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = requireActivity()

        if (taskItem != null) {
            binding.taskTitle.text = "Edit Task"
            val editable = Editable.Factory.getInstance()
            binding.name.text = editable.newEditable(taskItem!!.name)
            binding.taskDescription.text = editable.newEditable(taskItem!!.description)
        } else {
            binding.taskTitle.text = "New Task"
        }


        taskViewModel = ViewModelProvider(activity)[TaskViewModel::class.java]
        binding.save.setOnClickListener {
            saveAction()
        }
    }

    private fun saveAction() {
        val name = binding.name.text.toString()
        val description = binding.taskDescription.text.toString()
        if (taskItem == null) {
            val newTask = TaskItem(name, description, null, null)
            taskViewModel.addTaskItem(newTask)
        } else {
            taskViewModel.updateTaskItem(taskItem!!.id, name, description, null)
        }
        binding.name.setText("")
        binding.taskDescription.setText("")
        dismiss()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {
        binding = FragmentNewTaskSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

}