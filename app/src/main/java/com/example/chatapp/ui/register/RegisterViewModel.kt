package com.example.chatapp.ui.register

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.example.chatapp.R
import com.example.chatapp.base.BaseViewModel
import com.example.chatapp.database.FireStoreUtils
import com.example.chatapp.database.model.User
import com.example.chatapp.isValidEmail
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class RegisterViewModel :BaseViewModel<RegisterNavigator>() {
    val userName = ObservableField<String>()
    val userNameError = ObservableField<Int?>()
    val email = ObservableField<String>()
    val emailError = ObservableField<Int?>()
    val password = ObservableField<String>()
    val passwordError = ObservableField<Int?>()
    val passwordConfirm = ObservableField<String>()
    val passwordConfirmError = ObservableField<Int?>()
    val auth = FirebaseAuth.getInstance()
    fun register(){
        if (!validForm()) return
        auth.createUserWithEmailAndPassword(
            email.get()!!,
            password.get()!!
        ).addOnCompleteListener {
            if (it.isSuccessful){
                //insert user in data base
                insertUserInDatabase(it.result.user?.uid!!)
                navigator?.showLoading("Loading...")
                it.result.user?.uid
            }else{
                navigator?.hideDialoge()
                navigator?.showMessage(it.exception?.localizedMessage?:"")
            }
        }
    }

    private fun insertUserInDatabase(uid: String) {
        val user=User(
            id = uid,
            userName = userName.get(),
            email = email.get())
        FireStoreUtils().insertUserInDatabase(user)
            .addOnCompleteListener {
                navigator?.hideDialoge()
                if (it.isSuccessful){
                    navigator?.showMessage("successful registration")
                    navigator?.gotoHome()
                }else{
                    navigator?.showMessage(it.exception?.localizedMessage?:"")
                }
            }

    }

    fun validForm():Boolean{
        var isValid = true;
        if(userName.get().isNullOrBlank()){
            userNameError.set(R.string.Please_enter_your_name)
            isValid =false
        }else {
            isValid = true;
            userNameError.set(null)
        }
        if(email.get().isNullOrBlank()) {
            emailError.set(R.string.Please_enter_your_email)
            isValid = false;
        }else if(email.get()?.isValidEmail() == false){
            emailError.set(R.string.Please_enter_a_valid_email)
            isValid = false;

        }else {
            isValid=true;
            emailError.set(null)
        }
        if(password.get().isNullOrBlank()){
            isValid = false
            passwordError.set(R.string.please_enter_password)
        }else {
            isValid = true
            passwordError.set(null)
        }
        if(passwordConfirm.get().isNullOrBlank()){
            isValid =false;
            passwordConfirmError.set(R.string.please_renter_password)
        }else if (password.get()?.equals(passwordConfirm.get())==false){
            passwordConfirmError.set(R.string.doesnot_match)

        }else {
            isValid = true
            passwordConfirmError.set(null)
        }
        return isValid;

    }
    fun goToLogin(){
        navigator?.gotoLogin()
    }
}