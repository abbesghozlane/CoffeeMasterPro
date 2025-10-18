package com.coffeemaster.app.ui.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.coffeemaster.app.ui.theme.AppThemeColors

@Composable
fun Button3D(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    text: String,
    icon: ImageVector? = null,
    backgroundColor: androidx.compose.ui.graphics.Color = AppThemeColors.primaryBrown,
    enabled: Boolean = true
) {
    var pressed by remember { mutableStateOf(false) }

    val elevation = animateDpAsState(
        targetValue = if (pressed) 4.dp else 12.dp,
        animationSpec = tween(durationMillis = 100)
    )

    val translationZ = animateDpAsState(
        targetValue = if (pressed) 0.dp else 8.dp,
        animationSpec = tween(durationMillis = 100)
    )

    Card(
        modifier = modifier
            .clickable(enabled = enabled, onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        backgroundColor = if (enabled) backgroundColor else backgroundColor.copy(alpha = 0.5f),
        elevation = elevation.value
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                icon?.let {
                    Icon(imageVector = it, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                }
                Text(
                    text = text,
                    color = AppThemeColors.textWhite,
                    fontSize = 16.sp
                )
            }
        }
    }
}
