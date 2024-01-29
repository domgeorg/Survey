package com.georgiopoulos.core.design.widget.input

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.georgiopoulos.core.design.theme.DesignSystemTheme
import com.georgiopoulos.core.design.widget.input.InputFieldStatusBehavior.NON_STICKY
import com.georgiopoulos.core.design.widget.input.InputFieldStatusBehavior.STICKY
import com.georgiopoulos.core.design.widget.input.InputFieldStatusType.ERROR
import com.georgiopoulos.core.design.widget.input.InputFieldStatusType.WARNING
import kotlinx.coroutines.delay
import com.georgiopoulos.core_resources.R as Resources

@Composable
internal fun DesignSystemInputFieldImpl(
    text: String,
    hint: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    status: String = "",
    inputFieldStatusType: InputFieldStatusType? = null,
    inputFieldStatusBehavior: InputFieldStatusBehavior = STICKY,
    leadingIcon: @Composable (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
    showKeyboardDelay: Long? = null,
    hideKeyboard: Boolean = false,
    onValueChange: (String) -> Unit = {},
    onStatusChange: (String) -> Unit = {},
    onClearClickListener: () -> Unit = {},
    onActionKeyListener: KeyboardActions = KeyboardActions {},
    onFocusChangeListener: (Boolean) -> Unit = {},
    focusRequester: FocusRequester = remember { FocusRequester() },
) {
    var hasFocus by remember { mutableStateOf(false) }

    val focusManager = LocalFocusManager.current

    Column(
        modifier = modifier,
    ) {
        TextField(
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = when {
                    !enabled -> DesignSystemTheme.colors.neutralColors.neutral3
                    inputFieldStatusType == ERROR -> DesignSystemTheme.colors.inputFieldBackgroundError
                    else -> DesignSystemTheme.colors.inputFieldBackground
                },
                focusedContainerColor = when {
                    !enabled -> DesignSystemTheme.colors.neutralColors.neutral3
                    inputFieldStatusType == ERROR -> DesignSystemTheme.colors.inputFieldBackgroundError
                    else -> DesignSystemTheme.colors.inputFieldBackground
                },
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                cursorColor = DesignSystemTheme.colors.inputFieldCursorColor,
            ),
            visualTransformation = visualTransformation,
            leadingIcon = leadingIcon,
            keyboardOptions = keyboardOptions,
            keyboardActions = onActionKeyListener,
            modifier = modifier
                .focusRequester(focusRequester)
                .onFocusChanged { focusState ->
                    hasFocus = focusState.hasFocus
                    onFocusChangeListener(hasFocus)
                }
                .border(
                    width = 2.dp,
                    color = if (hasFocus && enabled && inputFieldStatusType != ERROR) {
                        DesignSystemTheme.colors.primaryColors.purple
                    } else {
                        Color.Transparent
                    },
                    shape = RoundedCornerShape(
                        size = 8.dp,
                    ),
                ),
            shape = RoundedCornerShape(
                size = 8.dp,
            ),
            textStyle = DesignSystemTheme.typography.inputField.textStyle,
            singleLine = true,
            onValueChange = {
                if (inputFieldStatusBehavior == NON_STICKY) {
                    onStatusChange("")
                }
                onValueChange(it)
            },
            value = text,
            enabled = enabled,
            trailingIcon = {
                if (text.isNotBlank() && enabled) {
                    IconButton(
                        onClick = {
                            onClearClickListener()
                            onValueChange("")
                            if (inputFieldStatusBehavior == NON_STICKY) {
                                onStatusChange("")
                            }
                        },
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(
                                id = Resources.drawable.icon_exit,
                            ),
                            tint = DesignSystemTheme.colors.inputFieldClearButtonColor,
                            contentDescription = null,
                        )
                    }
                }
            },
            label = {
                Text(
                    text = hint,
                    style = LocalTextStyle.current.merge(
                        DesignSystemTheme.typography.inputField.labelTextStyle,
                    ),
                )
            },
        )

        Text(
            modifier = Modifier.padding(start = 16.dp),
            text = if (enabled) {
                status
            } else {
                ""
            },
            style = when (inputFieldStatusType) {
                WARNING -> DesignSystemTheme.typography.inputField.statusWarningTextStyle
                ERROR -> DesignSystemTheme.typography.inputField.statusErrorTextStyle
                else -> DesignSystemTheme.typography.inputField.statusTextStyle
            },
        )

        if (hideKeyboard) {
            focusManager.clearFocus(true)
        } else {
            showKeyboardDelay?.let {
                LaunchedEffect(Unit) {
                    delay(it)
                    focusRequester.requestFocus()
                }
            }
        }
    }
}