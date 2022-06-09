package com.example.wceeventmanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import com.example.wceeventmanager.Retrofit.ApiInterface
import com.example.wceeventmanager.Retrofit.RetrofitInstance
import com.example.wceeventmanager.Retrofit.SignInBody
import com.example.wceeventmanager.bottomnav.AdminHomeActivity
import com.example.wceeventmanager.databinding.ActivityLoginBinding
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        emailFocusListener()
        passwordFocusListener()

        binding.btnLogin2.setOnClickListener {
            onLogin()
        }

        supportActionBar!!.hide()
    }

    private fun passwordFocusListener() {
        binding.passwordEditTextLog.setOnFocusChangeListener { _, focused ->
            if(!focused){
                if(binding.passwordEditTextLog.text?.isEmpty() == true){
                    binding.passwordTILLog.helperText = "Can't Be Empty"
                    return@setOnFocusChangeListener
                }
                else{
                    binding.passwordTILLog.helperText = validPass()
                }
            }
        }
    }

    private fun emailFocusListener() {
        binding.emailEditTextLog.setOnFocusChangeListener { _, focused ->

            if(!focused){
                if(binding.emailEditTextLog.text?.isEmpty() == true){
                    binding.emailTILLog.helperText = "Can't Be Empty"
                    return@setOnFocusChangeListener
                }
                else{
                    binding.emailTILLog.helperText = validEmail()
                }

            }
        }
    }

    private fun onLogin() {
//        val validEmail = binding.emailTILLog.helperText  == null
//        val passTxt = binding.passwordEditTextLog.text.toString()
//
//        if(validEmail  && passTxt.length >= 8)
           signin(email = binding.emailEditTextLog.text.toString(), password = binding.passwordEditTextLog.text.toString())
//        else
//            invalidform()
    }

    private fun invalidform() {
        var emailLen = binding.emailEditTextLog.length()
        var passLen = binding.passwordEditTextLog.length()
        if(emailLen == 0 && passLen == 0){
            Toast.makeText(this,"Invalid Username or password", Toast.LENGTH_LONG).show()
        }
        Toast.makeText(this,"Invalid Username or password", Toast.LENGTH_LONG).show()
        return
    }


    private fun signin(email: String, password: String) {
        val retIn = RetrofitInstance.getRetrofitInstance().create(ApiInterface::class.java)
        val signInInfo = SignInBody(email, password)
        retIn.signin(signInInfo).enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(
                    this@LoginActivity,
                    t.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.code() == 200) {
                    Toast.makeText(this@LoginActivity, "Login success!", Toast.LENGTH_SHORT).show()

                    startActivity(Intent(applicationContext, AdminHomeActivity::class.java))
                } else {
                    Toast.makeText(this@LoginActivity, "Login failed!", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }


    private fun validEmail(): String? {
        val emailTxt = binding.emailEditTextLog.text.toString()
        if(!Patterns.EMAIL_ADDRESS.matcher(emailTxt).matches()){
            return "Invalid Email Address"
        }
        return null
    }

    private fun validPass(): String? {
        val passTxt = binding.passwordEditTextLog.text.toString()
        if(passTxt.length <8 ){
            return "Minimum 8 Character Password !!"
        }
        return null
    }
}