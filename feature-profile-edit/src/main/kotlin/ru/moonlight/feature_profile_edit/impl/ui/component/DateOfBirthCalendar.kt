package ru.moonlight.feature_profile_edit.impl.ui.component

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.moonlight.api.theme.MoonlightTheme
import ru.moonlight.feature_profile_edit.R
import ru.moonlight.api.component.CalendarWithTextFieldLabelComponent

@Composable
internal fun DateOfBirthCalendar(
    onDateSelected: (String) -> Unit,
    onCalendarDismiss: () -> Unit,
    onFieldClick: () -> Unit,
    date: String?,
    isCalendarOpen: Boolean,
    isError: Boolean,
    modifier: Modifier = Modifier,
) {
    CalendarWithTextFieldLabelComponent(
        modifier = modifier
            .padding(horizontal = MoonlightTheme.dimens.paddingBetweenComponentsHorizontal),
        onDateSelected = onDateSelected,
        isCalendarOpen = isCalendarOpen,
        onFieldClick = onFieldClick,
        date = date,
        onCalendarDismiss = onCalendarDismiss,
        label = stringResource(R.string.birthDate),
        isError = isError,
    )
}