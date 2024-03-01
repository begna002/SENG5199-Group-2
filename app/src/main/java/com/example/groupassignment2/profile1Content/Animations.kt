package com.example.groupassignment2.profile1Content

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.runtime.Composable

@Composable
internal fun AnimatedScaleInOutTransition(
    visible: Boolean,
    content: @Composable AnimatedVisibilityScope.() -> Unit
) {
    AnimatedVisibility(
        visible = visible,
        enter = scaleIn(
            animationSpec = tween(400)
        ),
        exit = scaleOut(
            animationSpec = tween(300)
        ),
        content = content
    )
}

@Composable
internal fun AnimatedScaleInFadeOutTransition(
    visible: Boolean,
    content: @Composable AnimatedVisibilityScope.() -> Unit
) {
    AnimatedVisibility(
        visible = visible,
        enter = scaleIn(
            animationSpec = tween(400)
        ),
        exit = fadeOut(
            animationSpec = tween(300)
        ),
        content = content
    )
}