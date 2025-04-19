package com.aliza.simiex.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aliza.simiex.data.repository.LoginRepository
import com.aliza.simiex.utils.extensions.isValidPassword
import com.aliza.simiex.utils.extensions.isValidPhoneNumber
import com.aliza.simiex.utils.extensions.validatePhoneNumber
import com.aliza.simiex.utils.net.ParseRequest
import com.parse.ParseUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: LoginRepository) : ViewModel() {

    private val _phoneNumber = MutableStateFlow("")
    val phoneNumber = _phoneNumber.asStateFlow()

    private val _isPhoneValid = MutableStateFlow(false)
    val isPhoneValid = _isPhoneValid.asStateFlow()

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    private val _isPasswordValid = MutableStateFlow(false)
    val isPasswordValid = _isPasswordValid.asStateFlow()

    fun onPhoneChanged(number: String) {
        if (number.isValidPhoneNumber()) {
            _phoneNumber.value = number
            _isPhoneValid.value = number.validatePhoneNumber()
        }
    }

    fun onPasswordChanged(password: String) {
        _password.value = password
        _isPasswordValid.value = _password.value.isValidPassword()
    }

    private val _loginState = MutableStateFlow<ParseRequest<ParseUser>>(ParseRequest.Idle())
    val loginState: StateFlow<ParseRequest<ParseUser>> = _loginState
    fun login(phoneNumber: String, password: String) {
        viewModelScope.launch {
            _loginState.value = ParseRequest.Loading()
            val result = repository.login(phoneNumber, password)
            _loginState.value = result
        }
    }
}