
/* I`m not creator of this code. I just modified it a little bit
*  Creator is -> https://github.com/prabhatsdp
* */


package ru.moonlight.impl.template.otp

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
/**
 * A utility object providing default styles for OTP (One-Time Password) text fields.
 * These defaults are used to create pre-configured instances of [DigitContainerStyle.Outlined]
 * and [DigitContainerStyle.Underlined] for use in [OTPTemplate].
 */
internal object OtpTextFieldDefaults {

    /**
     * Returns a pre-configured outlined style for [OTPTemplate].
     *
     * @param size The size of the digit container.
     * @param shape The shape of the digit container.
     * @param containerColor The background color of the digit container.
     * @param focusedBorderColor The border color when the digit container is focused.
     * @param unfocusedBorderColor The border color when the digit container is not focused.
     * @param focusedBorderWidth The border width when the digit container is focused.
     * @param unfocusedBorderWidth The border width when the digit container is not focused.
     * @param errorColor The color used to indicate an error state in the digit container.
     * @return A pre-configured [DigitContainerStyle.Outlined] instance.
     */
    @Composable
    fun outlinedContainer(
        height: Dp = 65.dp,
        weight: Dp = 50.dp,
        shape: Shape = RoundedCornerShape(12.dp),
        containerColor: Color = MaterialTheme.colorScheme.background,
        focusedBorderColor: Color = MaterialTheme.colorScheme.primary,
        unfocusedBorderColor: Color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
        focusedBorderWidth: Dp = 2.dp,
        unfocusedBorderWidth: Dp = 2.dp,
        errorColor: Color = MaterialTheme.colorScheme.error,
    ): DigitContainerStyle.Outlined {
        return DigitContainerStyle.Outlined(
            height = height,
            weight = weight,
            shape = shape,
            containerColor = containerColor,
            focusedBorderColor = focusedBorderColor,
            unfocusedBorderColor = unfocusedBorderColor,
            focusedBorderWidth = focusedBorderWidth,
            unfocusedBorderWidth = unfocusedBorderWidth,
            errorColor = errorColor,
        )
    }

    /**
     * Returns a pre-configured underlined style for [OTPTemplate].
     *
     * @param size The size of the digit container.
     * @param containerColor The background color of the digit container.
     * @param focusedBorderColor The border color when the digit container is focused.
     * @param unfocusedBorderColor The border color when the digit container is not focused.
     * @param focusedBorderWidth The border width when the digit container is focused.
     * @param unfocusedBorderWidth The border width when the digit container is not focused.
     * @param errorColor The color used to indicate an error state in the digit container.
     * @return A pre-configured [DigitContainerStyle.Underlined] instance.
     */
    @Composable
    fun underlinedContainer(
        height: Dp = 65.dp,
        weight: Dp = 50.dp,
        containerColor: Color = MaterialTheme.colorScheme.background,
        focusedBorderColor: Color = MaterialTheme.colorScheme.primary,
        unfocusedBorderColor: Color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
        focusedBorderWidth: Dp = 2.dp,
        unfocusedBorderWidth: Dp = 2.dp,
        errorColor: Color = MaterialTheme.colorScheme.error,
    ): DigitContainerStyle.Underlined {
        return DigitContainerStyle.Underlined(
            height = height,
            weight = weight,
            containerColor = containerColor,
            focusedBorderColor = focusedBorderColor,
            unfocusedBorderColor = unfocusedBorderColor,
            focusedBorderWidth = focusedBorderWidth,
            unfocusedBorderWidth = unfocusedBorderWidth,
            errorColor = errorColor
        )
    }

}