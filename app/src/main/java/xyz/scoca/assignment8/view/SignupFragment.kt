package xyz.scoca.assignment8.view

import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import xyz.scoca.assignment8.R
import xyz.scoca.assignment8.data.ClientPreferences
import xyz.scoca.assignment8.databinding.FragmentSignupBinding
import xyz.scoca.assignment8.model.auth.AuthResponse
import xyz.scoca.assignment8.network.NetworkHelper


class SignupFragment : Fragment() {
    private lateinit var binding: FragmentSignupBinding
    private var username = ""
    private var email = ""
    private var password = ""
    private var gender = "male"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignupBinding.inflate(inflater,container,false)
        binding.btnSignUp.setOnClickListener {
            validateUser()
        }
        return binding.root
    }
    private fun register(username: String, email: String, password: String, gender: String) {
        NetworkHelper().userService?.register(username,email,password,gender)
            ?.enqueue(object : Callback<AuthResponse> {
                override fun onResponse(
                    call: Call<AuthResponse>,
                    response: Response<AuthResponse>
                ) {
                    val status = response.body()?.status.toString()
                    val errorMsg = response.body()?.message.toString()

                    if (status == "true") {
                        with(ClientPreferences(requireContext())) {
                            this.setUserEmail(response.body()?.user!![0].email)
                            this.setRememberMe(true)
                        }
                        findNavController().navigate(R.id.action_registerFragment_to_homeFragment)
                    } else
                        Toast.makeText(requireContext(), errorMsg, Toast.LENGTH_SHORT)
                            .show()
                }

                override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                    Toast.makeText(requireContext(),t.message.toString(), Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun validateUser() {
        username = binding.etUsername.text.toString()
        email = binding.etEmail.text.toString()
        password = binding.etPassword.text.toString()

        binding.radioGroup.setOnCheckedChangeListener { radioGroup, id ->
            val radioButton : RadioButton = radioGroup.findViewById(id)
            gender = radioButton.text.toString()
        }

        if (TextUtils.isEmpty(username)) {
            binding.etUsername.error = "Enter name"
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.etEmail.error = "Invalid email format"
        } else if (TextUtils.isEmpty(password)) {
            binding.etPassword.error = "Enter password"
        } else {
            register(username,email,password,gender)
        }
    }
}