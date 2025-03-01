package ru.moonlight.feature_auth_signup_registration.registration.api.navigation.presentation

import ru.moonlight.common.GenderOption

sealed class RegistrationAction {
    data class UpdateName(val name: String) : RegistrationAction()
    data class UpdateBirthDate(val birthDate: String) : RegistrationAction()
    data class UpdateSex(val sex: GenderOption?) : RegistrationAction()
    data class UpdateEmail(val email: String) : RegistrationAction()
    data class UpdatePassword(val password: String) : RegistrationAction()
    data class UpdateCode(val code: String) : RegistrationAction()
    class RequestCode : RegistrationAction()
    class ReRequestCode : RegistrationAction()
    class ConfirmCode : RegistrationAction()
    //object RequestCode : RegistrationAction()
}