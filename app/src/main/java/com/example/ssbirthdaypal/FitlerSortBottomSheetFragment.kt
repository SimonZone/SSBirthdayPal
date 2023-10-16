package com.example.ssbirthdaypal

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import com.example.ssbirthdaypal.databinding.BottomSheetLayoutBinding
import com.example.ssbirthdaypal.models.PersonsViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.FirebaseAuth

class FilterSortBottomSheetFragment : BottomSheetDialogFragment() {
    private var _binding: BottomSheetLayoutBinding? = null
    private val binding get() = _binding!!
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val viewModel: PersonsViewModel by activityViewModels()

    private val filters = listOf("name", "age", "day", "month", "year")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        inflater.inflate(R.layout.bottom_sheet_layout, container, false)
        _binding = BottomSheetLayoutBinding.inflate(inflater, container, false)
        val filterSpinner = binding.spinnerFilterValue
        // Create ArrayAdapter for each spinner
        val filterAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, filters)
        // Set the dropdown view resource for each adapter
        filterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        filterSpinner.adapter = filterAdapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.bottomApply.setOnClickListener {

            // Get user selections and apply filtering and sorting here
            val sortSearch = binding.editTextSortSearch.text.toString()
            Log.d("Bottom apply", sortSearch)
            val descending = binding.chipSortDescending.isChecked
            Log.d("Bottom apply", "$descending")
            val filterType = (filters.indexOf(binding.spinnerFilterValue.selectedItemPosition.toString()) + 1)
            Log.d("Bottom apply", "$filterType")
            val filterSearch = binding.editTextFilterSearch.text.toString()
            Log.d("Bottom apply", filterSearch)
            Log.d("Bottom apply", "$sortSearch, $descending, $filterType, $filterSearch")
            // Apply sorting and filtering to your data based on user selections
            viewModel.sortAndFilter(sortSearch, descending, filterType, filterSearch)

            // Dismiss the bottom sheet
            dismiss()
        }

        binding.bottomResetAll.setOnClickListener {
            viewModel.reloadWithUser(auth.currentUser!!.email!!)
            dismiss()
        }
    }
}