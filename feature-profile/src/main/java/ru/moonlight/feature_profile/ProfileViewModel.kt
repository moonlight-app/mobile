package ru.moonlight.feature_profile

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
class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    @Dispatcher(MoonlightDispatchers.IO) private val dispatcher: CoroutineDispatcher,
): ViewModel() {

    fun logout() {
        viewModelScope.launch(dispatcher) {
            authRepository.logout()
        }
    }

}