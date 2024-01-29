package com.georgiopoulos.core.design.widget.input

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import com.georgiopoulos.core.design.widget.input.InputFieldStatusBehavior.STICKY

@Composable
fun DesignSystemInputField(
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
    onValueChange: (String) -> Unit = {},
    onStatusChange: (String) -> Unit = {},
    onClearClickListener: () -> Unit = {},
    onActionKeyListener: KeyboardActions = KeyboardActions {},
    onFocusChangeListener: (Boolean) -> Unit = {},
    focusRequester: FocusRequester = remember { FocusRequester() },
    showKeyboardDelay: Long? = null,
) {
    DesignSystemInputFieldImpl(
        text = text,
        hint = hint,
        modifier = modifier,
        enabled = enabled,
        status = status,
        inputFieldStatusType = inputFieldStatusType,
        inputFieldStatusBehavior = inputFieldStatusBehavior,
        leadingIcon = leadingIcon,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        onValueChange = onValueChange,
        onStatusChange = onStatusChange,
        onClearClickListener = onClearClickListener,
        onActionKeyListener = onActionKeyListener,
        onFocusChangeListener = onFocusChangeListener,
        focusRequester = focusRequester,
        showKeyboardDelay = showKeyboardDelay,
    )
}
