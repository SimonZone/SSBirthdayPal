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
    ): View {
        inflater.inflate(R.layout.bottom_sheet_layout, container, false)
        _binding = BottomSheetLayoutBinding.inflate(inflater, container, false)
        val filterSpinner = binding.spinnerFilterBy
        val sortSpinner = binding.spinnerSortBy
        // Create ArrayAdapter for each spinner
        val filterAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, filters)
        // Set the dropdown view resource for each adapter
        filterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        filterSpinner.adapter = filterAdapter
        sortSpinner.adapter = filterAdapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.bottomApply.setOnClickListener {

            // Get user selections and apply filtering and sorting here
            val sortBy = binding.spinnerSortBy.selectedItemPosition + 1
            Log.d("Bottom apply", "$sortBy")
            val descending = binding.chipSortDescending.isChecked
            Log.d("Bottom apply", "$descending")
            val filterType = binding.spinnerFilterBy.selectedItemPosition + 1
            Log.d("Bottom apply", "$filterType")
            val filterSearch = binding.editTextFilterSearch.text.toString()
            Log.d("Bottom apply", filterSearch)
            Log.d("Bottom apply", "$sortBy, $descending, $filterType, $filterSearch")
            // Apply sorting and filtering to your data based on user selections
            viewModel.sortAndFilter(sortBy, descending, filterType, filterSearch)

            // Dismiss the bottom sheet
            dismiss()
        }

        binding.bottomResetAll.setOnClickListener {
            viewModel.reloadWithUser(auth.currentUser!!.email!!)
            dismiss()
        }
    }
}