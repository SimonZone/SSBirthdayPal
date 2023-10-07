package com.example.ssbirthdaypal

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.ssbirthdaypal.databinding.FragmentFirstBinding
import com.google.firebase.auth.FirebaseAuth

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        binding.buttonLogin.setOnClickListener {
            Log.d("Login", "Beginning login")
            val email = binding.editTextEmail.text.trim().toString()
            if (email.isEmpty()) {
                binding.editTextEmail.error = "No email"
                return@setOnClickListener
            }
            val password = binding.editTextPassword.text.trim().toString()
            if (password.isEmpty()) {
                binding.editTextPassword.error = "No password"
                return@setOnClickListener
            }
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("signIn success", "signInWithEmailAndPassword:success")
                        val user = auth.currentUser
                        findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("signIn failure", "signInWithEmailAndPassword:failure", task.exception)
                        binding.textviewMessage.text =
                            "Authentication failed: " + task.exception?.message
                    }
                }
        }

        binding.buttonRegister.setOnClickListener {
            Log.d("Register", "Beginning register")
            val email = binding.editTextEmail.text.trim().toString()
            if (email.isEmpty()) {
                binding.editTextEmail.error = "No email"
                return@setOnClickListener
            }
            val password = binding.editTextPassword.text.trim().toString()
            if (password.isEmpty()) {
                binding.editTextPassword.error = "No password"
                return@setOnClickListener
            }
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        Log.d("createUser success", "createUserWithEmail:success")
                        val user = auth.currentUser
                        findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
                    } else {
                        Log.w("createUser failure", "createUserWithEmail:failure", task.exception)
                        binding.textviewMessage.text =
                            "Registration failed: " + task.exception?.message
                    }
                }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}