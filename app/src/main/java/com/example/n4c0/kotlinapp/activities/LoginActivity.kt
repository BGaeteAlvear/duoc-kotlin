package com.example.n4c0.kotlinapp.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.n4c0.kotlinapp.*
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

      if (isValidEmail(email) && isValidPassword(password)){
          logInByEmail(email,password)
      }else{
          toast("Datos incorrectos, ingrese nuevamente los datos")
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

      editTextEmail.validate {
          editTextEmail.error = if (isValidEmail(it)) null else "El Email no es valido"
      }

      editTextPassword.validate {
          editTextPassword.error = if (isValidPassword(it)) null else "El Password no es valido, debe contener 1 numero, 1 mayuscula, 1 minuscula, 1 caracter especial y minimo 4 caracteres "
      }
  }

  private fun logInByEmail(email: String , password: String){
      mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this){
        task -> if (task.isSuccessful){
                    if (mAuth.currentUser!!.isEmailVerified){
                        toast("¡Bienvenido!")
                    }else{
                        toast("Necesitas confirmar tu correo")
                    }


                }else{
                    toast("Error al iniciar sesion")
                }
      }
  }
}
