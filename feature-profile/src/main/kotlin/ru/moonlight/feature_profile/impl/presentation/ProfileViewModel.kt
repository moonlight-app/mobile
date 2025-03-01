package ru.moonlight.feature_profile.impl.presentation

import androidx.lifecycle.viewModelScope
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.moonlight.api.component.OrderUiModel
import ru.moonlight.common.ApiResponse
import ru.moonlight.common.base.BaseUIState
import ru.moonlight.common.base.BaseViewModel
import ru.moonlight.domain.profile.interactor.LogoutInteractor
import ru.moonlight.domain.profile.usecase.LoadProfileAndOrdersUseCase
import ru.moonlight.domain_model.order.OrdersDomainModel
import javax.inject.Inject

@HiltViewModel
internal class ProfileViewModel @Inject constructor(
    private val loadProfileAndOrdersUseCase: LoadProfileAndOrdersUseCase,
    private val logoutInteractor: LogoutInteractor,
): BaseViewModel<ProfileState, ProfileSideEffects>(ProfileState()) {
    val uiState = baseUiState

    private var _orders = MutableStateFlow<PagingData<OrderUiModel>>(PagingData.empty(
        sourceLoadStates = LoadStates(
            refresh = LoadState.Loading,
            prepend = LoadState.Loading,
            append = LoadState.Loading,
        )
    ))
    val orders = _orders.asStateFlow()

    fun dispatch(action: ProfileAction) = intent {
        when (action) {
            ProfileAction.LoadProfile -> loadProfile()
            ProfileAction.ProfileDataClick -> navigateToEditProfile()
            ProfileAction.FavouritesClick -> navigateToFavorites()
            ProfileAction.OrderClick -> navigateToOrders()
            ProfileAction.ChangePasswordClick -> navigateToChangePassword()
            ProfileAction.LogoutClick -> logout()
        }
    }

    private fun loadProfile() {
        viewModelScope.launch {
            updateUiState(BaseUIState.Loading)

            val response = loadProfileAndOrdersUseCase()
            val profileData = response.first
            val orderDataFlow = response.second

            when (profileData) {
                is ApiResponse.Error -> updateUiState(BaseUIState.Error(profileData.msg))
                is ApiResponse.Success -> {
                    intent {
                        val newState = state.copy(
                            email = profileData.data?.email!!,
                            name = profileData.data?.name!!,
                            sex = profileData.data?.gender!!,
                            birthDate = profileData.data?.birthDate!!,
                        )
                        reduce { newState }
                    }

                    orderDataFlow
                        .cachedIn(viewModelScope)
                        .collect { pagingData ->
                            _orders.emit(pagingData.map { it.mapToUiModel() })
                            updateUiState(BaseUIState.Success)
                        }
                }
            }
        }
    }

    private fun navigateToEditProfile() = intent { postSideEffect(ProfileSideEffects.NavigateToEditProfile) }

    private fun navigateToOrders() = intent { postSideEffect(ProfileSideEffects.NavigateToOrders) }

    private fun navigateToFavorites() = intent { postSideEffect(ProfileSideEffects.NavigateToFavorites) }

    private fun navigateToChangePassword() = intent { postSideEffect(ProfileSideEffects.NavigateToChangePassword) }

    private fun logout() = intent {
        updateUiState(BaseUIState.Loading)
        logoutInteractor.invoke()
        postSideEffect(ProfileSideEffects.Logout)
    }

}

private fun OrdersDomainModel.mapToUiModel() =
    OrderUiModel(
        id = productId,
        title = name,
        type = type,
        imageUrl = previewUrl,
        price = price.toString(),
        size = size,
        status = status,
    )