package com.example.binarchallengecp4

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.binar.challenge4.data.User
import com.binar.challenge4.database.MyDatabase
import com.example.binarchallengecp4.databinding.FragmentLoginBinding
import com.example.binarchallengecp4.databinding.FragmentRegisterBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FragmentRegister : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    //private var myDatabase: MyDatabase? = null
    lateinit var authRepository: AuthRepository
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRegisterBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //myDatabase = MyDatabase.getInstance(requireContext())
        authRepository = AuthRepository(requireContext())
        binding.btnRegister.setOnClickListener {
            val user = User(
                null,
                binding.etRegisterEmail.text.toString(),
                binding.etRegisterPassword.text.toString()
            )
            lifecycleScope.launch(Dispatchers.IO) {
                authRepository.insertUser(user)
                activity?.runOnUiThread{
                    it.findNavController().popBackStack()
                }
            }
        }
    }
}