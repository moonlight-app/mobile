package ru.moonlight.feature_profile_edit.impl.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import ru.moonlight.api.component.ConfirmExitAlerDialogComponent
import ru.moonlight.api.component.TopAppBarComponent
import ru.moonlight.api.theme.MoonlightTheme
import ru.moonlight.common.base.BaseUIState
import ru.moonlight.common.toGenderOptions
import ru.moonlight.feature_profile_edit.R
import ru.moonlight.feature_profile_edit.impl.presentation.ProfileEditViewModel
import ru.moonlight.feature_profile_edit.impl.ui.component.DateOfBirthCalendar
import ru.moonlight.feature_profile_edit.impl.ui.component.GenderField
import ru.moonlight.feature_profile_edit.impl.ui.component.NameTextField
import ru.moonlight.feature_profile_edit.impl.ui.component.SaveButton

@Composable
internal fun ProfileEditRoute(
    onBackClick: () -> Unit,
    name: String,
    gender: String,
    birthDate: String,
    modifier: Modifier = Modifier,
) {
    val viewModel = hiltViewModel<ProfileEditViewModel>()

    LaunchedEffect(Unit) {
        viewModel.initState(name, gender, birthDate)
    }

    val uiState by viewModel.uiState.collectAsState(initial = BaseUIState.Loading)

    var nameState by remember {
        mutableStateOf(name)
    }

    var genderState by remember {
        mutableStateOf(gender)
    }

    var birthDateState by remember {
        mutableStateOf(birthDate)
    }

    var saveButtonEnabled by remember {
        mutableStateOf(false)
    }

    var showExitDialog by remember { mutableStateOf(false) }

    ProfileEditScreen(
        modifier = modifier,
        onBackClick = {
            if (!saveButtonEnabled) onBackClick()
            else showExitDialog = true
        },
        onSaveClick = {
            viewModel.saveProfile(oldName = name, oldGender = gender, oldDate = birthDate )
            onBackClick()
        },
        onNameChange = { newName ->
            viewModel.updateName(newName)
            nameState = newName
            saveButtonEnabled = checkSaveButtonEnabled(name, newName, gender, genderState, birthDate, birthDateState)
        },
        onSexChange = { newGender ->
            viewModel.updateGender(newGender)
            genderState = newGender
            saveButtonEnabled = checkSaveButtonEnabled(name, nameState, gender, newGender, birthDate, birthDateState)
        },
        onBirthDateChange = { newDate ->
            viewModel.updateBirthDate(newDate)
            birthDateState = newDate
            saveButtonEnabled = checkSaveButtonEnabled(name, nameState, gender, genderState, birthDate, newDate)
        },
        onConfirmDialog = {
            showExitDialog = false
            onBackClick()
        },
        onDismissDialog = { showExitDialog = false },
        showDialog = showExitDialog,
        name = nameState,
        gender = genderState,
        birthDate = birthDateState,
        uiState = uiState,
        saveBtnEnabled = saveButtonEnabled,
    )
}

@Composable
private fun ProfileEditScreen(
    onBackClick: () -> Unit,
    onSaveClick: () -> Unit,
    onNameChange: (String) -> Unit,
    onSexChange: (String) -> Unit,
    onBirthDateChange: (String) -> Unit,
    onConfirmDialog: () -> Unit,
    onDismissDialog: () -> Unit,
    showDialog: Boolean,
    name: String,
    gender: String,
    birthDate: String,
    uiState: BaseUIState,
    saveBtnEnabled: Boolean,
    modifier: Modifier = Modifier,
) {
    var isCalendarOpen by remember { mutableStateOf(false) }

    if (showDialog) {
        ConfirmExitAlerDialogComponent(
            onConfirmExit = onConfirmDialog,
            onDismiss = onDismissDialog,
            title = stringResource(R.string.exitWithoutSave),
            text = stringResource(R.string.areUSureExitWithoutSave),
            confirmText = stringResource(R.string.exit),
            dismissText = stringResource(R.string.cancel),
        )
    }

    Scaffold(
        modifier = modifier,
        containerColor = MoonlightTheme.colors.background,
        topBar = {
            TopAppBarComponent(
                onBackClick = onBackClick,
                title = stringResource(R.string.profileDetails),
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(
                MoonlightTheme.dimens.paddingBetweenComponentsSmallVertical
            ),
        ) {
            NameTextField(
                onNameChange = onNameChange,
                name = name,
                isError = uiState is BaseUIState.Error,
            )
            GenderField(
                gender = gender.toGenderOptions(),
                onGenderChoose = { newValue -> onSexChange(newValue.name.lowercase()) },
                isError = uiState is BaseUIState.Error,
            )
            DateOfBirthCalendar(
                onDateSelected = { date -> onBirthDateChange(date) },
                onFieldClick = { isCalendarOpen = true },
                onCalendarDismiss = { isCalendarOpen = false },
                date = birthDate,
                isCalendarOpen = isCalendarOpen,
                isError = uiState is BaseUIState.Error,
            )
            SaveButton(
                onSaveClick = onSaveClick,
                enable = saveBtnEnabled,
            )
        }
    }
}

private fun checkSaveButtonEnabled(
    oldName: String,
    name: String,
    oldGender: String,
    gender: String,
    oldBirthDate: String,
    birthDate: String,
): Boolean {
    if (oldName == name && oldGender == gender && oldBirthDate == birthDate) return false
    if (name.isEmpty() || gender.isEmpty() || birthDate.isEmpty()) return false
    return true
}