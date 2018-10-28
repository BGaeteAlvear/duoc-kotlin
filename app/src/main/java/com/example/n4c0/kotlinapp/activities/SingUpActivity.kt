package com.example.n4c0.kotlinapp.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.n4c0.kotlinapp.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sing_up.*

class SingUpActivity : AppCompatActivity() {

    private val mAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sing_up)

        buttonGoLogIn.setOnClickListener(){
            val intent = Intent(this,LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        buttonSingUp.setOnClickListener(){
            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()
            if (isValiEmailAndPassword(email,password)){
                singUpbyEmail(email,password)
            }else{
                Toast.makeText(this,"Datos incorrectos, ingrese nuevamente los datos", Toast.LENGTH_LONG).show()
            }
        }

    }

    private fun singUpbyEmail(email: String, password: String){
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {

                        Toast.makeText(this,"Se envio un mensaje a tu correo, Porfavor confirmalo", Toast.LENGTH_LONG).show()
                        val user = mAuth.currentUser
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(this,"Error al inicar sesion", Toast.LENGTH_LONG).show()
                    }
                }
    }

    private fun isValiEmailAndPassword(email: String, password: String):Boolean{
        return !email.isNullOrEmpty() &&
                !password.isNullOrEmpty() &&
                password === editConfirmPassword.text.toString()
    }
}
