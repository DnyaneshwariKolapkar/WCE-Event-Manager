package com.example.wceeventmanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.activity.viewModels
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.wceeventmanager.Retrofit.ApiInterface
import com.example.wceeventmanager.Retrofit.RetrofitInstance
import com.example.wceeventmanager.Retrofit.SignInBody
import com.example.wceeventmanager.bottomnav.*
import com.example.wceeventmanager.databinding.ActivityLoginBinding
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private val fragmentViewModel : FragmentViewModel by viewModels()

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

                    val useremail = email
                    getUserInfo(useremail)

                    //startActivity(Intent(applicationContext, AdminHomeActivity::class.java))
                } else {
                    Toast.makeText(this@LoginActivity, response.body().toString(), Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun getUserInfo(useremail: String) {
        val queue = Volley.newRequestQueue(this)

        val apiurl = "https://walchand-event-organizer.herokuapp.com/getuserdetails/$useremail"

        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, apiurl, null,
            { response ->
                val id = response.getString("_id")
                val name = response.getString("name")
                val email = response.getString("email")
                val isVerified = response.getBoolean("isVerified")
                val isAdminUser = response.getBoolean("isAdminUser")
                val isClubUser = response.getBoolean("isClubUser")
                val token = response.getString("token")

                val bundle = Bundle()
                bundle.putString("name", name)
                bundle.putString("email", email)
                bundle.putBoolean("isVerified", isVerified)
                bundle.putBoolean("isAdminUser", isAdminUser)
                bundle.putBoolean("isClubUser", isClubUser)
                bundle.putString("token", token)

             //   fragmentViewModel.setData(name)

                var fragmentprof = ProfileFragment()

                if(bundle.getBoolean("isVerified")) {
                    fragmentprof.arguments = bundle

                    //supportFragmentManager.beginTransaction().add(R.id.home, eventListFragment).commit()

                    Toast.makeText(this, "${fragmentprof.arguments} ${bundle.getString("name")}", Toast.LENGTH_LONG).show()
                }

                Toast.makeText(this, "$isAdminUser $isClubUser $isVerified", Toast.LENGTH_SHORT).show()

                intent = Intent(this@LoginActivity, AdminHomeActivity::class.java)
                intent.putExtras(bundle)
                startActivity(intent)

            },
            {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
            }
        )
        queue.add(jsonObjectRequest)

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