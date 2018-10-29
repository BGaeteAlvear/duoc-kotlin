package com.example.n4c0.kotlinapp.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.n4c0.kotlinapp.R
import com.example.n4c0.kotlinapp.goToActivity
import com.example.n4c0.kotlinapp.toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sing_up.*

class SingUpActivity : AppCompatActivity() {

    private val mAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sing_up)

        buttonGoLogIn.setOnClickListener(){
            goToActivity<LoginActivity>{
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            //overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
        }

        buttonSingUp.setOnClickListener(){
            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()

            if (isValiEmailAndPassword(email,password)){
                singUpbyEmail(email,password)
            }else{
                toast("Datos incorrectos, ingrese nuevamente los datos")
            }
        }

    }

    private fun singUpbyEmail(email: String, password: String){
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {

                        toast("Se envio un mensaje a tu correo, Porfavor confirmalo")

                        goToActivity<LoginActivity>{
                            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        }

                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)

                    } else {
                        // If sign in fails, display a message to the user.
                        toast("Error al inicar sesion")
                    }
                }
    }

    private fun isValiEmailAndPassword(email: String, password: String):Boolean{
        return !email.isNullOrEmpty() &&
                !password.isNullOrEmpty() &&
                password === editConfirmPassword.text.toString()
    }
}
