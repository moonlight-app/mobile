package ru.moonlight.feature_auth_signup_complete

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.moonlight.theme.MoonlightTheme
import ru.moonlight.ui.ButtonComponent
import ru.moonlight.ui.TextAuthComponent

@Composable
fun RegistrationCompleteScreen(
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
        TextAuthComponent(
            subTitleText = stringResource(R.string.accountComplete),
            bodyText = stringResource(R.string.accountWasCreatedAndConfirmed),
        )
        ButtonComponent(
            modifier = Modifier
                .fillMaxWidth(0.55f),
            onClick = { onGetStartClick() },
            text = stringResource(R.string.getStart),
        )
    }
}