package com.maxcruz.design.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.maxcruz.design.R

@Composable
fun SuitCard(
    modifier: Modifier = Modifier,
    revealed: Boolean = false,
    value: Int? = null,
    suit: String? = null,
    elevation: Dp = 2.dp,
    tag: String = ""
) {
    Card(
        modifier = modifier
            .height(350.dp)
            .width(220.dp),
        shape = RoundedCornerShape(corner = CornerSize(size = 16.dp)),
        elevation = elevation
    ) {
        Box(
            modifier = Modifier.padding(all = 8.dp)
        ) {
            if (!revealed) {
                Image(
                    painter = painterResource(id = R.drawable.bg_backwards),
                    contentDescription = "",
                    modifier = Modifier.fillMaxSize(),
                )
            } else if (value != null && suit != null) {

                Text(
                    text = tag,
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.align(Alignment.TopEnd)
                        .padding(8.dp)
                )

                val card = value.cardName()
                val suiteColor = suit.suiteColor()
                CornerNumber(
                    cardName = card.first,
                    suite = suit,
                    color = suiteColor,
                    modifier = Modifier.align(Alignment.TopStart),
                )
                CardCenter(
                    value = value,
                    card = card,
                    color = suiteColor,
                    suite = suit,
                    modifier = Modifier
                        .align(Alignment.Center),
                )
                CornerNumber(
                    cardName = card.first,
                    suite = suit,
                    color = suiteColor,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .rotate(180f),
                )
            }
        }
    }
}

@Composable
private fun CornerNumber(
    cardName: String,
    suite: String,
    color: Color,
    modifier: Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = cardName,
            style = MaterialTheme.typography.h4,
            color = color,
        )
        SuitImage(
            suit = suite,
            modifier = Modifier.size(24.dp)
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun CardCenter(
    value: Int,
    card: Pair<String, String>,
    color: Color,
    suite: String,
    modifier: Modifier,
) {
    if (value in (1..9)) {
        val columns = value.cardColumns()
        Row(modifier = modifier.padding(vertical = 64.dp)) {
            val column = @Composable { modifier : Modifier, items: Int ->
                Column(
                    modifier = modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    repeat(items) {
                        SuitImage(
                            suit = suite,
                            modifier = modifier.size(36.dp)
                        )
                    }
                }
            }
            column(Modifier, columns.first)
            column(Modifier, columns.second)
            column(Modifier, columns.third)
        }
    } else {
        if (card.first == "A") {
            SuitImage(
                suit = suite,
                modifier = modifier.size(96.dp)
            )
        } else {
            Row(modifier = modifier) {
                Text(
                    text = card.second,
                    style = MaterialTheme.typography.h1,
                    color = color,
                )
                SuitImage(
                    suit = suite,
                    modifier = modifier.size(72.dp)
                )
            }
        }
    }
}

private fun Int.cardColumns(): Triple<Int, Int, Int> {
    // Simple logic to split the suite symbol grid in tree columns
    val value = this + 1
    val part = value / 2
    return if (value % 2 == 0) {
        Triple(part, 0, part)
    } else {
        Triple(part, value - (part * 2), part)
    }
}

private fun Int.cardName(): Pair<String, String> =
    when (this) {
        13 -> "A" to ""
        10 -> "J" to "♝"
        11 -> "Q" to "♛"
        12 -> "K" to "♚"
        in (1..9) -> (this + 1).toString() to ""
        else -> throw IllegalStateException("Unknown card")
    }

private fun String.suiteColor(): Color =
    when (this) {
        in listOf("spades", "clubs") -> Color.Black
        in listOf("hearts", "diamonds") -> Color.Red
        else -> throw IllegalStateException("Unexpected suite name")
    }