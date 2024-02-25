package com.example.groupassignment2.service
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.groupassignment2.R
import com.example.groupassignment2.data.Card
import com.example.groupassignment2.data.GlobalLink
import com.example.groupassignment2.ui.theme.BlueEnd
import com.example.groupassignment2.ui.theme.BlueStart
import com.example.groupassignment2.ui.theme.GreenEnd
import com.example.groupassignment2.ui.theme.GreenStart

val cards = listOf(
    Card(
        cardType = "portfolio",
        cardDescription = "Bassam Portfolio",
        cardName = "Portfolio",
        color = getGradient(BlueStart, BlueEnd),
    ),
    Card(
        cardType = "AI Assistant",
        cardDescription = "Click here to ask neighbor AI ",
        cardName = "AI Assistant",
        color = getGradient(GreenStart, GreenEnd),
    ),
)

fun getGradient(
    startColor: Color,
    endColor: Color,
): Brush {
    return Brush.horizontalGradient(
        colors = listOf(startColor, endColor)
    )
}

@Composable
fun CardsSection() {
    val context = LocalContext.current

    CompositionLocalProvider(LocalContext provides context) {
        LazyRow {
            items(cards.size) { index ->
                CardItem(card = cards[index]) {
                    val globalLink = GlobalLink()
                    val uri = Uri.parse(globalLink.portfolio) // Change the URL to your desired one
                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    context.startActivity(intent)
                }
                Spacer(modifier = Modifier.width(16.dp))
            }
        }
    }
}

@Composable
fun CardItem(
    card: Card,
    onClick: () -> Unit
) {
    val image = painterResource(id = if (card.cardType == "portfolio") R.drawable.portfolio else R.drawable.assistant)

    var lastItemPaddingEnd = 0.dp
    if (card == cards.last()) {
        lastItemPaddingEnd = 16.dp
    }

    Box(
        modifier = Modifier
            .padding(start = 16.dp, end = lastItemPaddingEnd)
            .clickable { onClick() }
    ) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(25.dp))
                .background(card.color)
                .width(250.dp)
                .height(160.dp)
                .padding(vertical = 12.dp, horizontal = 16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painter = image,
                contentDescription = card.cardName,
                modifier = Modifier.width(60.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = card.cardName,
                color = Color.White,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = card.cardDescription,
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}