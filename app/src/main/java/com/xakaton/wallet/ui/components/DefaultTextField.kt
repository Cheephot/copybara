package com.xakaton.wallet.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.xakaton.wallet.R
import com.xakaton.wallet.ui.utils.clearFocusOnKeyboardDismiss

@Composable
fun DefaultTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    horizontalPadding: Dp = 24.dp,
    minHeight: Dp = 56.dp,
    shape: RoundedCornerShape = RoundedCornerShape(20.dp),
    borderWidth: Dp = 1.dp,
    borderColor: Color = Color(0xFFDDE2F2),
    visualTransformation: VisualTransformation = VisualTransformation.None,
    textStyle: TextStyle = TextStyle(
        fontSize = 16.sp,
        lineHeight = 24.sp,
        fontWeight = FontWeight.W400
    ),
    textColor: Color = Color(0xFF262626),
    @StringRes decorationBoxTextId: Int? = null,
    decorationBoxTextColor: Color = Color(0xFF8589AF),
) {
    val focusRequester = remember { FocusRequester() }
    var isFocused by remember { mutableStateOf(false) }

    val currentBorderColor = if (isFocused) Color(0xFF262626) else borderColor

    Box(
        modifier = modifier
            .padding(horizontal = horizontalPadding)
            .fillMaxWidth()
            .defaultMinSize(minHeight = minHeight)
            .clip(shape = shape)
            .border(
                width = borderWidth,
                color = currentBorderColor,
                shape = shape
            ),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                textStyle = textStyle.copy(color = textColor),
                keyboardOptions = keyboardOptions,
                singleLine = true,
                maxLines = 1,
                visualTransformation = visualTransformation,
                cursorBrush = SolidColor(Color(0xFF262626)),
                decorationBox = {
                    it()

                    if (value.isEmpty() && decorationBoxTextId != null) {
                        Text(
                            text = stringResource(id = decorationBoxTextId),
                            color = decorationBoxTextColor,
                            fontSize = 16.sp,
                            lineHeight = 24.sp,
                            fontWeight = FontWeight.W400
                        )
                    }
                },
                modifier = Modifier
                    .padding(horizontal = 14.dp, vertical = 19.dp)
                    .weight(1f, true)
                    .onFocusChanged { focusState ->
                        isFocused = focusState.isFocused
                    }
                    .focusRequester(focusRequester)
                    .clearFocusOnKeyboardDismiss()
            )

            if (value.isNotEmpty()) {
                IconButton(
                    onClick = { onValueChange("") }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_circle_close),
                        contentDescription = null,
                        tint = Color.Unspecified
                    )
                }
            }
        }
    }
}

@Composable
fun PasswordTexField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    horizontalPadding: Dp = 24.dp,
    minHeight: Dp = 56.dp,
    shape: RoundedCornerShape = RoundedCornerShape(20.dp),
    borderWidth: Dp = 1.dp,
    borderColor: Color = Color(0xFFDDE2F2),
    textStyle: TextStyle = TextStyle(
        fontSize = 16.sp,
        lineHeight = 24.sp,
        fontWeight = FontWeight.W400
    ),
    textColor: Color = Color(0xFF262626),
    @StringRes decorationBoxTextId: Int? = null,
    decorationBoxTextColor: Color = Color(0xFF8589AF),
) {
    var passwordVisible by remember { mutableStateOf(false) }
    val visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation()

    val focusRequester = remember { FocusRequester() }
    var isFocused by remember { mutableStateOf(false) }

    val currentBorderColor = if (isFocused) Color(0xFF262626) else borderColor
    val currentIconColor = if (passwordVisible) Color(0xFF262626) else Color(0xFF262626).copy(alpha = 0.30f)

    Box(
        modifier = modifier
            .padding(horizontal = horizontalPadding)
            .fillMaxWidth()
            .defaultMinSize(minHeight = minHeight)
            .clip(shape = shape)
            .border(
                width = borderWidth,
                color = currentBorderColor,
                shape = shape
            ),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                textStyle = textStyle.copy(color = textColor),
                keyboardOptions = keyboardOptions,
                singleLine = true,
                maxLines = 1,
                visualTransformation = visualTransformation,
                cursorBrush = SolidColor(Color(0xFF262626)),
                decorationBox = {
                    it()

                    if (value.isEmpty() && decorationBoxTextId != null) {
                        Text(
                            text = stringResource(id = decorationBoxTextId),
                            color = decorationBoxTextColor,
                            fontSize = 16.sp,
                            lineHeight = 24.sp,
                            fontWeight = FontWeight.W400
                        )
                    }
                },
                modifier = Modifier
                    .padding(horizontal = 14.dp, vertical = 19.dp)
                    .weight(1f, true)
                    .onFocusChanged { focusState ->
                        isFocused = focusState.isFocused
                    }
                    .focusRequester(focusRequester)
                    .clearFocusOnKeyboardDismiss()
                    .clearFocusOnKeyboardDismiss()
            )

            if (value.isNotEmpty()) {
                IconButton(
                    onClick = { passwordVisible = !passwordVisible }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_visible_eye_eyeball_open_view),
                        contentDescription = null,
                        tint = currentIconColor
                    )
                }
            }
        }
    }
}

class PasswordVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val passwordChar = '•'
        val transformedText = AnnotatedString(passwordChar.toString().repeat(text.length))
        return TransformedText(transformedText, OffsetMapping.Identity)
    }
}