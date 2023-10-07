package com.example.ssbirthdaypal

import android.R
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.ssbirthdaypal.databinding.FragmentAddFriendBinding
import com.example.ssbirthdaypal.databinding.FragmentFirstBinding
import com.example.ssbirthdaypal.models.Person
import com.example.ssbirthdaypal.models.PersonsViewModel
import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


class AddFriendFragment : Fragment() {

    private var _binding: FragmentAddFriendBinding? = null
    private val binding get() = _binding!!
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val viewModel: PersonsViewModel by activityViewModels()

    private val days = (1..31).map { it.toString() }
    private val months = listOf(
        "January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"
    )
    private val years = (1900..2023).map { it.toString() }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddFriendBinding.inflate(inflater, container, false)

        val daySpinner = binding.spinnerDay
        val monthSpinner = binding.spinnerMonth
        val yearSpinner = binding.spinnerYear

        // Create ArrayAdapter for each spinner
        val dayAdapter = ArrayAdapter(requireContext(), R.layout.simple_spinner_item, days)
        val monthAdapter = ArrayAdapter(requireContext(), R.layout.simple_spinner_item, months)
        val yearAdapter = ArrayAdapter(requireContext(), R.layout.simple_spinner_item, years)

        // Set the dropdown view resource for each adapter
        dayAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        monthAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        yearAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)

        daySpinner.adapter = dayAdapter
        monthSpinner.adapter = monthAdapter
        yearSpinner.adapter = yearAdapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.errorMessageLiveData.observe(viewLifecycleOwner) { errorMessage ->
            binding.textViewError.text = errorMessage
        }

        binding.buttonCancel.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.buttonAdd.setOnClickListener {
            val name = binding.editTextName.text.toString().trim()
            val remarks = binding.editTextRemark.text.toString().trim()
            val selectedDay = binding.spinnerDay.selectedItem.toString().toInt()
            val selectedMonth = (months.indexOf(binding.spinnerMonth.selectedItem.toString()) + 1)
            val selectedYear = binding.spinnerYear.selectedItem.toString().toInt()

            val personToAdd = Person(1, null, name, selectedYear, selectedMonth, selectedDay, remarks,null,null)

            Log.d("button add", "add ${personToAdd.name}, ${personToAdd.birthYear}, ${personToAdd.birthMonth}, ${personToAdd.birthDayOfMonth}, remarks: ${personToAdd.remarks}")
            viewModel.add(personToAdd)
            findNavController().popBackStack()
        }
    }
}