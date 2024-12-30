package com.plcoding.goldchart.core.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

sealed interface UIText {
    data class DynamicString(val value: String) : UIText
    class StringResourceId(
        val id: Int,
        val args: Array<Any> = arrayOf(),
    ) : UIText

    @Composable
    fun asString(): String {
        return when (this) {
            is DynamicString -> value
            is StringResourceId -> stringResource(id = id, formatArgs = args)
        }
    }
}