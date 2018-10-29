package com.example.n4c0.kotlinapp.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.n4c0.kotlinapp.R
import com.example.n4c0.kotlinapp.goToActivity
import com.example.n4c0.kotlinapp.toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

  private val mAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_login)



    buttonLogIn.setOnClickListener{
      val email = editTextEmail.text.toString()
      val password = editTextPassword.text.toString()

      if (isValdEmailAndPassword(email, password)){
          logInByEmail(email,password)
      }
    }

    textViewForgetPassword.setOnClickListener{
      goToActivity<ForgotPasswordActivity>()
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
    }

    buttonCreateAccount.setOnClickListener{
      goToActivity<SingUpActivity>()
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
    }

  }

  private fun logInByEmail(email: String , password: String){

      mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this){
        task -> if (task.isSuccessful){
                    toast("¡Bienvenido!")
                }else{
                    toast("Error al iniciar sesion")
                }
      }
  }

  private fun isValdEmailAndPassword(email: String, password: String): Boolean{
    return !email.isNullOrEmpty() && !password.isNullOrEmpty()
  }
}
