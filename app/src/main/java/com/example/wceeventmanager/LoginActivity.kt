package com.example.wceeventmanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import com.android.volley.toolbox.JsonArrayRequest
import com.example.wceeventmanager.Retrofit.ApiInterface
import com.example.wceeventmanager.Retrofit.RetrofitInstance
import com.example.wceeventmanager.Retrofit.SignInBody
import com.example.wceeventmanager.bottomnav.AdminHomeActivity
import com.example.wceeventmanager.bottomnav.Profile
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

        val bundle = Bundle()
        var isGuest = false

        emailFocusListener()
        passwordFocusListener()

        binding.signText.setOnClickListener {
            startActivity(Intent(applicationContext,SignUpActivity::class.java))
        }
        //Guest User
        binding.cagText.setOnClickListener {
            isGuest = true

            bundle.putBoolean("isGuest", isGuest)

            intent = Intent(this@LoginActivity, AdminHomeActivity::class.java)
            intent.putExtras(bundle)
            startActivity(intent)
        }

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
        val validEmail = binding.emailTILLog.helperText  == null
        val passTxt = binding.passwordEditTextLog.text.toString()

        if(validEmail  && passTxt.length >= 8)
           signin(email = binding.emailEditTextLog.text.toString(), password = binding.passwordEditTextLog.text.toString())
        else
            invalidform()
    }

    private fun invalidform() {

        val emailLen = binding.emailEditTextLog.length()

        val passLen = binding.passwordEditTextLog.length()
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
                    getUserInfo()

                    startActivity(Intent(applicationContext, AdminHomeActivity::class.java))
                } else {
                    Toast.makeText(this@LoginActivity, response.body().toString(), Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun getUserInfo() {
        var userInfo = ArrayList<Profile>()
        val apiReq = JsonArrayRequest("https://walchand-event-organizer.herokuapp.com/user/me", {
            for (i in 0 until it.length()) {
                val obj = it.getJSONObject(i)
                val name = obj.getString("name")
                val email = obj.getString("email")
                val isVerified = obj.getBoolean("isVerified")
                val isAdminUser = obj.getBoolean("isAdminUser")
                val isClubUser = obj.getBoolean("isClubUser")

                Log.e("name",name)
             userInfo.add(Profile(name,email,isVerified,isAdminUser,isClubUser))
            }
        },{

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