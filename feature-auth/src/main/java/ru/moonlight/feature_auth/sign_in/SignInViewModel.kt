package ru.moonlight.feature_auth.sign_in

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import ru.moonlight.common.di.Dispatcher
import ru.moonlight.common.di.MoonlightDispatchers
import ru.moonlight.data.repository.AuthRepository
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    @Dispatcher(MoonlightDispatchers.IO) val dispatcher: CoroutineDispatcher,
    private val authRepository: AuthRepository,
): ViewModel() {

    fun login(login: String, password: String): Boolean {
        val job = viewModelScope.launch(dispatcher) {
            authRepository.login(login, password)
        }
        return true
    }

}