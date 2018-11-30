package com.example.n4c0.kotlinapp.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.n4c0.kotlinapp.*
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), GoogleApiClient.OnConnectionFailedListener {


    private val mAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    private val mGoogleApiClient: GoogleApiClient by lazy { getGoogleApiClient() }
    private val RC_GOOGLE_SIGN_IN = 99

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

      buttonLogInGoogle.setOnClickListener{
          val signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient)
          startActivityForResult(signInIntent, RC_GOOGLE_SIGN_IN)
      }

      editTextEmail.validate {
          editTextEmail.error = if (isValidEmail(it)) null else "El Email no es valido"
      }

      editTextPassword.validate {
          editTextPassword.error = if (isValidPassword(it)) null else "El Password no es valido, debe contener 1 numero, 1 mayuscula, 1 minuscula, 1 caracter especial y minimo 4 caracteres "
      }
  }

  private fun getGoogleApiClient(): GoogleApiClient{
      val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
              .requestIdToken(getString(R.string.default_web_client_id))
              .requestEmail()
              .build()

      return GoogleApiClient.Builder(this)
              .enableAutoManage(this, this)
              .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
              .build()
  }

  private fun loginByGoogleAccountIntoFirebase(googleAccount: GoogleSignInAccount){
    val credential = GoogleAuthProvider.getCredential(googleAccount.idToken, null)
      mAuth.signInWithCredential(credential).addOnCompleteListener(this){
          if (mGoogleApiClient.isConnected){
              Auth.GoogleSignInApi.signOut(mGoogleApiClient)
          }
          goToActivity<MainActivity>{
              flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
          }
      }
  }

  private fun logInByEmail(email: String , password: String){
      mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this){
        task -> if (task.isSuccessful){
                    if (mAuth.currentUser!!.isEmailVerified){
                        goToActivity<MainActivity>{
                            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        }
                    }else{
                        toast("Necesitas confirmar tu correo")
                    }
                }else{
                    toast("Error al iniciar sesion")
                }
      }
  }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_GOOGLE_SIGN_IN){
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            if (result.isSuccess){
                val account = result.signInAccount
                loginByGoogleAccountIntoFirebase(account!!)
            }
        }
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        toast("Connection fail!")
    }
}
