package com.example.chatapp

import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout

@BindingAdapter("errorLayoutString")
fun bindErrorOnTextInputLayout(
    textInputLayout: TextInputLayout,
    errorMessage: String?
) {
    textInputLayout.error = errorMessage
}
@BindingAdapter("errorLayoutStringId")
fun bindErrorOnTextInputLayout(
    textInputLayout: TextInputLayout,
    errorMessageId: Int?
) {
    textInputLayout.error = errorMessageId?.
    let { textInputLayout.context.getText(it) }
}