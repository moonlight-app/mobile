package ru.moonlight.ui

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import ru.moonlight.theme.MoonlightTheme
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun CalendarWithTextFieldComponent(
    onClick: () -> Unit,
    onCalendarDismiss: () -> Unit,
    onDateSelected: (String) -> Unit,
    date: String?,
    isCalendarOpen: Boolean,
    placeholder: String,
    modifier: Modifier = Modifier,
    enable: Boolean = true,
    isError: Boolean = false,
    focusedTextColor: Color = MoonlightTheme.colors.text,
    unfocusedTextColor: Color = MoonlightTheme.colors.text,
    focusedContainerColor: Color = Color.Transparent,
    focusedLabelColor: Color = MoonlightTheme.colors.highlightComponent,
    unfocusedLabelColor: Color = MoonlightTheme.colors.component,
    focusedBorderColor: Color = MoonlightTheme.colors.highlightComponent,
    unfocusedBorderColor: Color = MoonlightTheme.colors.component,
    errorBorderColor: Color = MoonlightTheme.colors.error,
    errorTextColor: Color = MoonlightTheme.colors.error,
    disabledBorderColor: Color = MoonlightTheme.colors.disabledComponent,
    disabledTextColor: Color = MoonlightTheme.colors.disabledText,
    textStyle: TextStyle = MoonlightTheme.typography.textField,
) {
    Box(
        modifier = modifier
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = date ?: "",
            onValueChange = {},
            readOnly = true,
            textStyle = textStyle,
            placeholder = {
                Text(placeholder)
            },
            isError = isError,
            enabled = enable,
            singleLine = true,
            shape = MoonlightTheme.shapes.textFieldShape,
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = focusedTextColor,
                unfocusedTextColor = unfocusedTextColor,
                focusedContainerColor = focusedContainerColor,
                focusedLabelColor = focusedLabelColor,
                unfocusedLabelColor = unfocusedLabelColor,
                focusedBorderColor = focusedBorderColor,
                unfocusedBorderColor = unfocusedBorderColor,
                errorBorderColor = errorBorderColor,
                errorTextColor = errorTextColor,
                disabledBorderColor = disabledBorderColor,
                disabledTextColor = disabledTextColor,
            ),
            interactionSource = remember { MutableInteractionSource() }
                .also { interactionSource ->
                    LaunchedEffect(interactionSource) {
                        interactionSource.interactions.collect {
                            if (it is PressInteraction.Release) {
                                onClick()
                            }
                        }
                    }
                }
        )

        if (isCalendarOpen) {
            CalendarView(
                modifier = Modifier.fillMaxWidth(),
                onDateSelected = { date ->
                    onCalendarDismiss()
                    onDateSelected(date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")))
                },
                onDismiss = { onCalendarDismiss() }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarView(
    onDateSelected: (LocalDate) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    focusedTextColor: Color = MoonlightTheme.colors.text,
    unfocusedTextColor: Color = MoonlightTheme.colors.text,
    focusedContainerColor: Color = Color.Transparent,
    focusedLabelColor: Color = MoonlightTheme.colors.highlightComponent,
    unfocusedLabelColor: Color = MoonlightTheme.colors.component,
    focusedBorderColor: Color = MoonlightTheme.colors.highlightComponent,
    unfocusedBorderColor: Color = MoonlightTheme.colors.component,
    errorBorderColor: Color = MoonlightTheme.colors.error,
    errorTextColor: Color = MoonlightTheme.colors.error,
    disabledBorderColor: Color = MoonlightTheme.colors.disabledComponent,
    disabledTextColor: Color = MoonlightTheme.colors.disabledText,
    textStyle: TextStyle = MoonlightTheme.typography.textField,
) {
    val currentDate = remember { LocalDate.now() }
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = currentDate.toEpochDay() * 24 * 60 * 60 * 1000
    )

    DatePickerDialog(
        modifier = modifier,
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    onDateSelected(
                        datePickerState.selectedDateMillis?.let {
                            Instant.ofEpochMilli(it).atZone(
                                ZoneId.systemDefault()).toLocalDate()
                        } ?: LocalDate.now()
                    )
                    onDismiss()
                }
            ) {
                Text(
                    text = stringResource(id = R.string.choose),
                    style = textStyle,
                    color = MoonlightTheme.colors.text,
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    stringResource(id = R.string.cancel),
                    style = textStyle,
                    color = MoonlightTheme.colors.hintText,
                )
            }
        },
        colors = DatePickerDefaults.colors(
            containerColor = MoonlightTheme.colors.card,
            titleContentColor = MoonlightTheme.colors.text,
            headlineContentColor = MoonlightTheme.colors.highlightComponent,
            subheadContentColor = MoonlightTheme.colors.highlightComponent,
            selectedDayContainerColor = MoonlightTheme.colors.highlightComponent,
            selectedDayContentColor = MoonlightTheme.colors.highlightText,
            dayContentColor = MoonlightTheme.colors.hintText,
            todayContentColor = MoonlightTheme.colors.hintText,
            todayDateBorderColor = MoonlightTheme.colors.hintText,
            weekdayContentColor = MoonlightTheme.colors.hintText,
            disabledDayContentColor = MoonlightTheme.colors.disabledComponent,
            navigationContentColor = MoonlightTheme.colors.text,
        ),
    ) {
        DatePicker(
            modifier = Modifier,
            state = datePickerState,
            showModeToggle = true,
            colors = DatePickerDefaults.colors(
                containerColor = MoonlightTheme.colors.card,
                titleContentColor = MoonlightTheme.colors.text,
                headlineContentColor = MoonlightTheme.colors.highlightComponent,
                subheadContentColor = MoonlightTheme.colors.highlightComponent,
                selectedDayContainerColor = MoonlightTheme.colors.highlightComponent,
                selectedDayContentColor = MoonlightTheme.colors.highlightText,
                dayContentColor = MoonlightTheme.colors.hintText,
                todayContentColor = MoonlightTheme.colors.hintText,
                todayDateBorderColor = MoonlightTheme.colors.hintText,
                weekdayContentColor = MoonlightTheme.colors.hintText,
                disabledDayContentColor = MoonlightTheme.colors.disabledComponent,
                navigationContentColor = MoonlightTheme.colors.text,
                dividerColor = Color.Transparent,
                dateTextFieldColors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = focusedTextColor,
                    unfocusedTextColor = unfocusedTextColor,
                    focusedContainerColor = focusedContainerColor,
                    focusedLabelColor = focusedLabelColor,
                    unfocusedLabelColor = unfocusedLabelColor,
                    focusedBorderColor = focusedBorderColor,
                    unfocusedBorderColor = unfocusedBorderColor,
                    errorBorderColor = errorBorderColor,
                    errorTextColor = errorTextColor,
                    disabledBorderColor = disabledBorderColor,
                    disabledTextColor = disabledTextColor,
                )
            )
        )
    }
}
