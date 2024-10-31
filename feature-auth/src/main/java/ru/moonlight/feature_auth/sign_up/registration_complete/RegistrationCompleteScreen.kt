package ru.moonlight.feature_auth.sign_up.registration_complete

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import ru.moonlight.feature_auth.R
import ru.moonlight.theme.MoonlightTheme
import ru.moonlight.ui.ButtonComponent
import ru.moonlight.ui.TextAuthComponent

@Composable
fun RegistrationCompleteScreen(
    onGetStartClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = MoonlightTheme.colors.background),
        contentAlignment = Alignment.TopCenter,
    ) {
        Column(
            modifier = Modifier
                .padding(top = MoonlightTheme.dimens.paddingFromEdges * 7)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(
                MoonlightTheme.dimens.paddingBetweenComponentsBigVertical,
                Alignment.CenterVertically
            ),
        ) {
            TextAuthComponent(
                subTitleText = stringResource(R.string.accountComplete),
                bodyText = stringResource(R.string.accountWasCreatedAndConfirmed),
            )
            ButtonComponent(
                modifier = Modifier
                    .fillMaxWidth(0.55f),
                onClick = {
                    Toast.makeText(context, "Navigate back to profile", Toast.LENGTH_SHORT).show()
                    onGetStartClick()
                },
                text = stringResource(R.string.getStart),
            )
        }
    }
}