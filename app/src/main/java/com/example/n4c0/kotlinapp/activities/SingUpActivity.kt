package com.example.n4c0.kotlinapp.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.n4c0.kotlinapp.*
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
            val confirmPassword = editConfirmPassword.text.toString()

            if (isValidEmail(email) && isValidPassword(password) && isValidConfirmPassword(password,confirmPassword)){
                singUpbyEmail(email,password)
            }else{
                toast("Datos incorrectos, ingrese nuevamente los datos")
            }
        }

        editTextEmail.validate {
            editTextEmail.error = if (isValidEmail(it)) null else "El Email no es valido"
        }

        editTextPassword.validate {
            editTextPassword.error = if (isValidPassword(it)) null else "El Password no es valido, debe contener 1 numero, 1 mayuscula, 1 minuscula, 1 caracter especial y minimo 4 caracteres "
        }

        editConfirmPassword.validate {
            editConfirmPassword.error = if (isValidConfirmPassword(editConfirmPassword.text.toString(), it)) null else "Confirma Password, no son iguales"
        }
    }

    private fun singUpbyEmail(email: String, password: String){
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        mAuth.currentUser!!.sendEmailVerification().addOnCompleteListener(this){
                            toast("Se envio un mensaje a tu correo, Porfavor confirmalo")
                            goToActivity<LoginActivity>{
                                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            }
                            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                        }
                    } else {
                        // If sign in fails, display a message to the user.
                        toast("Error al inicar sesion")
                    }
                }
    }

}
