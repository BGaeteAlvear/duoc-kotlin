package com.example.n4c0.kotlinapp.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.n4c0.kotlinapp.*
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_forgot_password.*

class ForgotPasswordActivity : AppCompatActivity() {
    //Inicializar Autenticacion de Firebase
    private val mAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
        //Campo de texto Email
        editTextEmail.validate {
            editTextEmail.error = if (isValidEmail(it)) null else "El email no es valido"
        }
        //Boton volver al Login
        buttonGoLogIn.setOnClickListener{
            goToActivity<LoginActivity>{
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            //Animacion de movimiento fade_in
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
        //Boton Olvide el password
        buttonForgot.setOnClickListener{
            val email = editTextEmail.text.toString()
            if (isValidEmail(email)){
                mAuth.sendPasswordResetEmail(email).addOnCompleteListener(this) {
                    toast("El email fue enviado para tu nueva password")
                    goToActivity<LoginActivity>{
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    }
                    //Animacion de movimiento fade_in
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                }
            }else{
                toast("Asegurate que el email es correcto")
            }
        }
    }
}
