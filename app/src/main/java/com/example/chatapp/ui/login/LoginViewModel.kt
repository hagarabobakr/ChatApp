package com.example.chatapp.ui.login

import androidx.databinding.ObservableField
import com.example.chatapp.R
import com.example.chatapp.base.BaseViewModel
import com.google.firebase.auth.FirebaseAuth

class LoginViewModel : BaseViewModel<LoginNavigator>() {
    val email = ObservableField<String>()
    val emailError = ObservableField<Int?>()
    val password = ObservableField<String>()
    val passwordError = ObservableField<Int?>()
    val auth = FirebaseAuth.getInstance()
    fun login() {
        if (!validForm()) return

        auth.signInWithEmailAndPassword(
            email.get()!!,
            password.get()!!
        ).addOnCompleteListener {
            if (it.isSuccessful){
                navigator?.showMessage(it.result.user?.uid?:" ")
                return@addOnCompleteListener
            }else{
                navigator?.showMessage(it.exception?.localizedMessage?:"")
            }
        }
    }

    fun validForm(): Boolean {
        var isValid = true
        if (email.get().isNullOrBlank()) {
            emailError.set(R.string.Please_enter_your_email)
            isValid = false
        } else {
            isValid = true
            emailError.set(null)
        }
        if (password.get().isNullOrBlank()) {
            isValid = false
            passwordError.set(R.string.please_enter_password)
        } else {
            isValid = true
            passwordError.set(null)
        }

        return isValid;

    }
    fun gotoRegister(){
        navigator?.gotoRegister()
    }

}
