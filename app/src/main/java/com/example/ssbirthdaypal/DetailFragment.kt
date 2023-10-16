package com.example.ssbirthdaypal

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.ssbirthdaypal.models.PersonsViewModel
import com.example.ssbirthdaypal.databinding.FragmentDetailBinding
import com.example.ssbirthdaypal.models.Person
import com.google.firebase.auth.FirebaseAuth

class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PersonsViewModel by activityViewModels()

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = requireArguments()
        val detailFragmentArgs: DetailFragmentArgs = DetailFragmentArgs.fromBundle(bundle)
        val position = detailFragmentArgs.position
        val person = viewModel[position]
        if (person == null) {
            binding.textviewMessage.text = "no such person"
            return
        }
        binding.editTextName.setText(person.name)
        binding.editTextAge.setText(person.age.toString())
        binding.editTextDay.setText(person.birthDayOfMonth.toString())
        binding.editTextMonth.setText(person.birthMonth.toString())
        binding.editTextYear.setText(person.birthYear.toString())
        binding.editTextRemark.setText(person.remarks.toString())

        binding.buttonBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.buttonDelete.setOnClickListener {
            viewModel.delete(person.id)
            findNavController().popBackStack()
        }

        binding.buttonUpdate.setOnClickListener {
            val name = binding.editTextName.text.toString().trim()
            val day = binding.editTextDay.text.toString().trim()
            val month = binding.editTextMonth.text.toString().trim()
            val year = binding.editTextYear.text.toString().trim()
            val remark = binding.editTextRemark.text.toString().trim()
            val personToUpdate = Person(person.id, auth.currentUser!!.email, name, year.toInt(), month.toInt(), day.toInt(),remark,null,null)

            Log.d("detail update", "update ${personToUpdate.name}")
            viewModel.update(personToUpdate)
            findNavController().popBackStack()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
