package com.example.wceeventmanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import com.example.wceeventmanager.Retrofit.ApiInterface
import com.example.wceeventmanager.Retrofit.RetrofitInstance
import com.example.wceeventmanager.Retrofit.UserBody
import com.example.wceeventmanager.bottomnav.AdminHomeActivity
import com.example.wceeventmanager.databinding.ActivitySignUpBinding
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignUpBinding

    //created mutable strings to store values
    val email = MutableStateFlow("")
    val password = MutableStateFlow("")
    val name = MutableStateFlow("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        emailFocusListener()
        passwordFocusListener()
        nameFocusListener()

        binding.signText.setOnClickListener {
            startActivity(Intent(applicationContext,LoginActivity::class.java))
        }
        binding.cagText.setOnClickListener {
            startActivity(Intent(applicationContext,AdminHomeActivity::class.java))
        }

        //get text from edittext and set to strings
        with(binding) {

            emailEditText.doOnTextChanged { text, start, before, count ->
//                if(!Patterns.EMAIL_ADDRESS.matcher(text.toString()).matches()){
//                    binding.emailTIL.helperText = "Invalid Email Address"
//                }
//                else{
//                    binding.emailTIL.helperText = ""
//                }
                email.value = text.toString()
            }
            passwordEditText.doOnTextChanged { text, start, before, count ->
                if(text?.length!! < 8){
                    binding.passwordTIL.helperText = "Minimum 8 Character Password"
                }
                else if(text.isEmpty()){
                    binding.passwordTIL.helperText = "Can't Empty"
                }
                else{
                    binding.passwordTIL.helperText = ""
                }
                password.value = text.toString()
            }
                nameEditText.doOnTextChanged { text, start, before, count ->
                name.value = text.toString()
            }
        }

        binding.btnsignup2.setOnClickListener{
            onsignup()
        }

        supportActionBar!!.hide()
    }


    private fun onsignup() {
        val validEmail = binding.emailEditText.text
        val passTxt = binding.passwordEditText.text.toString()
        val name = binding.nameEditText.text.toString()

        if(Patterns.EMAIL_ADDRESS.matcher(validEmail.toString()).matches() && passTxt.length >= 8 && name.length>1)
            signup(email = binding.emailEditText.text.toString(), name = binding.nameEditText.text.toString(), password = binding.passwordEditText.text.toString())
        else
            invalidform()
    }

    private fun signup(email: String, name: String, password: String) {
        val retIn = RetrofitInstance.getRetrofitInstance().create(ApiInterface::class.java)
        val registerInfo = UserBody(email,name,password)

        retIn.registerUser(registerInfo).enqueue(object :
            Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(
                    this@SignUpActivity,
                    t.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.code() == 200 || response.code() == 201) {
                    Toast.makeText(this@SignUpActivity, "Registration success!", Toast.LENGTH_SHORT)
                        .show()
                }
                else if(response.code() == 401){
                    Toast.makeText(this@SignUpActivity,"Email already exists", Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(this@SignUpActivity,"Something went wrong", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun invalidform() {
        Toast.makeText(this,"Please Fill All Fields !!", Toast.LENGTH_LONG).show()
        return
    }

    private fun emailFocusListener() {
        binding.emailEditText.setOnFocusChangeListener { _, focused ->

            if(!focused){
                if(binding.emailEditText.text?.isEmpty() == true){
                    binding.emailTIL.helperText = "Can't Be Empty"
                    return@setOnFocusChangeListener
                }
                else{
                    binding.emailTIL.helperText = validEmail()
                }

            }
        }
    }

    private fun validEmail(): String? {
        val emailTxt = binding.emailEditText.text.toString()
        if(!Patterns.EMAIL_ADDRESS.matcher(emailTxt).matches()){
            return "Invalid Email Address"
        }
        return null
    }
    private fun passwordFocusListener() {
        binding.passwordEditText.setOnFocusChangeListener { _, focused ->
            if(!focused){
                if(binding.passwordEditText.text?.isEmpty() == true){
                    binding.passwordTIL.helperText = "Can't Be Empty"
                    return@setOnFocusChangeListener
                }
                else{
                    binding.passwordTIL.helperText = validPass()
                }

            }
        }
    }

    private fun validPass(): String? {
        val passTxt = binding.passwordEditText.text.toString()
        if(passTxt.length <8 ){
            return "Minimum 8 Character Password !!"
        }
        return null
    }

    private fun nameFocusListener() {
        binding.nameEditText.setOnFocusChangeListener { _, focused ->

            if(!focused){
                if(binding.nameEditText.text?.isEmpty() == true){
                    binding.nameTIL.helperText = "Can't Be Empty"
                    return@setOnFocusChangeListener
                }
                else{
                    binding.nameTIL.helperText = validName()
                }
            }
        }
    }

    private fun validName(): String? {
        var name =binding.nameEditText.text
        if(name?.equals("abc") == true){
            return "Enter correct name"
        }
        return null

    }
}