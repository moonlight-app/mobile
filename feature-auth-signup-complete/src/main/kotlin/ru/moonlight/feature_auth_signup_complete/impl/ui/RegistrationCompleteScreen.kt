package ru.moonlight.feature_auth_signup_complete.impl.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ru.moonlight.api.theme.MoonlightTheme
import ru.moonlight.feature_auth_signup_complete.impl.ui.component.Logo
import ru.moonlight.feature_auth_signup_complete.impl.ui.component.StartButton

@Composable
internal fun RegistrationCompleteScreen(
    onGetStartClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(top = MoonlightTheme.dimens.paddingFromEdges * 6)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
            MoonlightTheme.dimens.paddingBetweenComponentsBigVertical,
            Alignment.CenterVertically,
        ),
    ) {
        Logo()
        StartButton(onStartClick = onGetStartClick)
    }
}