package com.example.diceroller

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.diceroller.ui.theme.DiceRollerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DiceRollerTheme {
                Scaffold(modifier = Modifier) { innerPadding ->
                    DiceRollerApp(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}


@Composable
fun DiceWithButtonAndImage(modifier: Modifier = Modifier) {

    var result by remember { mutableStateOf(1) }

    val imageResource = when (result) {
        1 -> R.drawable.dice_1
        2 -> R.drawable.dice_2
        3 -> R.drawable.dice_3
        4 -> R.drawable.dice_4
        5 -> R.drawable.dice_5
        else -> R.drawable.dice_6
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Spacer(modifier = Modifier.weight(1f))

        // DICE IMAGE
        Image(
            painter = painterResource(imageResource),
            contentDescription = "1",
            modifier
                .size(300.dp)
        )

        // Row allows avatar to be on the left side
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp, top = 70.dp, bottom = 100.dp)
        ){
            // Result is the dice number
            AvatarWithName(result)
        }

        // Adds space between the Avatar and Roll button
        Spacer(modifier = Modifier.weight(1f))

        // ROLL BUTTON
        Button(
            onClick = { result = (1..6).random() },
            modifier
                .width(200.dp)
                .height(70.dp),
            colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                containerColor = Color(0xFF34E4EA),
                contentColor = Color.Black
            )
        ){
            Text(
                stringResource(R.string.roll),
                fontSize = 24.sp
            )
        }
        // Adds space between the Roll button and bottom
        Spacer(modifier = Modifier.weight(1f))
    }
}


@Composable
fun AvatarWithName(
    diceNumber: Int,
    modifier: Modifier = Modifier
){
    val avatar = when(diceNumber) {
        1 -> R.drawable.ben
        2 -> R.drawable.jen
        3 -> R.drawable.katy
        4 -> R.drawable.rana
        else -> R.drawable.tanya
    }

    val name = when(diceNumber) {
        1 -> "Ben"
        2 -> "Jen"
        3 -> "Katy"
        4 -> "Rana"
        else -> "Tanya"
    }

    val avatarBorderColor = when(diceNumber) {
        1 -> Color(0xFFffb3ba)
        2 -> Color(0xFFffffba)
        3 -> Color(0xFFffdfba)
        4 -> Color(0xFFbaffc9)
        else -> Color(0xFFbae1ff)
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        // Makes the avatar circular and adds the border
        Box(
            modifier = Modifier
                .size(105.dp)
                .border(7.dp, avatarBorderColor, CircleShape)
                .clip(CircleShape)
        ){
            // AVATAR IMAGE
            Image(
                painter = painterResource(avatar),
                contentDescription = null,
                modifier
                    .size(105.dp)
                    .graphicsLayer {
                        scaleX = 1.2f
                        scaleY = 1.2f
                    }
            )
        }
        // NAMES
        Text(
            name,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(4.dp)
        )
    }
}


@Preview(showBackground = true)
@Composable
fun DiceRollerApp(modifier: Modifier = Modifier) {
    DiceWithButtonAndImage()
}
