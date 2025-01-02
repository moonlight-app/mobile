package ru.moonlight.feature_auth_signup_registration.registration.impl.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.moonlight.api.theme.MoonlightTheme
import ru.moonlight.feature_auth_signup_registration.R
import ru.moonlight.api.component.CalendarWithTextFieldComponent

@Composable
internal fun DateOfBirthCalendar(
    onDateSelected: (String) -> Unit,
    onCalendarDismiss: () -> Unit,
    onFieldClick: () -> Unit,
    date: String?,
    isCalendarOpen: Boolean,
    enable: Boolean,
    isError: Boolean,
    modifier: Modifier = Modifier,
) {
    CalendarWithTextFieldComponent(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = MoonlightTheme.dimens.paddingFromEdges),
        onDateSelected = onDateSelected,
        isCalendarOpen = isCalendarOpen,
        onFieldClick = onFieldClick,
        date = date,
        onCalendarDismiss = onCalendarDismiss,
        placeholder = stringResource(R.string.birthDate),
        enable = enable,
        isError = isError,
    )
}