package com.example.diceroller

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.diceroller.ui.theme.DiceRollerTheme
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow

// Main activity to set up the application
//test
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Setting the content of the app using Compose
        setContent {
            DiceRollerTheme {
                Scaffold(modifier = Modifier) { innerPadding ->
                    DiceRollerApp(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

// Core UI function to display the dice, game button, and state messages
@Composable
fun DiceWithButtonAndImage(modifier: Modifier = Modifier) {
    // Game state variables
    var result by remember { mutableStateOf<Int?>(null) } // Dice result, null if no roll yet
    var attemptsLeft by remember { mutableStateOf(3) } // Remaining dice rolls
    var isGameStarted by remember { mutableStateOf(false) } // Tracks game start state
    var selectedAvatarIndex by remember { mutableStateOf(1) } // Random avatar for game character

    // Dynamic message based on game state
    val message = when {
        !isGameStarted -> "Welcome! You have 3 chances to roll the dice. Get a 6 to win. Rolling a 3 gives you another turn!"
        result == null -> "Press Roll to begin!"
        result == 3 -> "Unlucky! You get another roll!"
        result == 6 -> "You win!"
        attemptsLeft == 0 -> "Game Over! No more chances."
        else -> "Sorry, try again! Attempts left: $attemptsLeft"
    }

    // Determine which dice image to display based on the result
    val imageResource = when (result) {
        1 -> R.drawable.dice_1
        2 -> R.drawable.dice_2
        3 -> R.drawable.dice_3
        4 -> R.drawable.dice_4
        5 -> R.drawable.dice_5
        6 -> R.drawable.dice_6
        else -> null
    }

    // Layout: Vertically arranged UI
    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f)) // Spacer for vertical spacing

        // Display dice image if the game is started and there is a result
        if (imageResource != null && isGameStarted && result != null) {
            Image(
                painter = painterResource(imageResource),
                contentDescription = "Dice roll: $result",
                modifier = Modifier.size(300.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp)) // Additional spacing

        // Avatar and speech bubble for displaying game messages
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp, top = 70.dp, bottom = 100.dp)
        ) {
            AvatarWithSpeechBubble(
                diceNumber = if (!isGameStarted) 1 else selectedAvatarIndex,
                message = message
            )
        }

        Spacer(modifier = Modifier.weight(1f)) // Spacer for vertical spacing

        // Roll or Reset button
        Button(
            onClick = {
                if (!isGameStarted) {
                    isGameStarted = true // Start the game
                } else if (attemptsLeft > 0) {
                    val newResult = (1..6).random() // Roll the dice
                    result = newResult // Update result

                    if (newResult == 6) {
                        attemptsLeft = 0 // Player wins
                    } else if (newResult != 3) {
                        attemptsLeft -= 1 // Deduct an attempt unless 3 is rolled
                    }
                    selectedAvatarIndex = (1..5).random() // Randomize avatar
                } else {
                    // Reset the game
                    result = null
                    attemptsLeft = 3
                    isGameStarted = false
                }
            },
            modifier = Modifier
                .width(200.dp)
                .height(70.dp),
            colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                containerColor = Color(0xFF673AB7)
            )
        ) {
            // Button text changes based on game state
            Text(
                text = when {
                    !isGameStarted -> "Start Game"
                    attemptsLeft == 0 || result == 6 -> "Restart"
                    else -> "Roll"
                },
                fontSize = 24.sp
            )
        }

        Spacer(modifier = Modifier.weight(1f)) // Spacer for vertical spacing
    }
}

// Avatar and speech bubble component
@Composable
fun AvatarWithSpeechBubble(
    diceNumber: Int,
    message: String,
    modifier: Modifier = Modifier
) {
    // Determine avatar image, name, and border color based on the avatar index
    val avatar = when (diceNumber) {
        1 -> R.drawable.ben
        2 -> R.drawable.jen
        3 -> R.drawable.katy
        4 -> R.drawable.rana
        else -> R.drawable.tanya
    }

    val name = when (diceNumber) {
        1 -> "Ben"
        2 -> "Jen"
        3 -> "Katy"
        4 -> "Rana"
        else -> "Tanya"
    }

    val avatarBorderColor = when (diceNumber) {
        1 -> Color(0xFFffb3ba)
        2 -> Color(0xFFffffba)
        3 -> Color(0xFFffdfba)
        4 -> Color(0xFFbaffc9)
        else -> Color(0xFFbae1ff)
    }

    // Layout for avatar and speech bubble
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.TopStart
    ) {
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Avatar image with a circular border
            Box(
                modifier = Modifier
                    .size(105.dp)
                    .border(7.dp, avatarBorderColor, CircleShape)
                    .clip(CircleShape)
            ) {
                Image(
                    painter = painterResource(avatar),
                    contentDescription = null,
                    modifier = Modifier.size(105.dp)
                )
            }

            // Display avatar name
            Text(
                text = name,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(4.dp)
            )
        }

        // Speech bubble with message
        Box(
            modifier = Modifier
                .align(Alignment.TopStart)
                .offset(x = 80.dp, y = (-60).dp)
                .padding(horizontal = 14.dp, vertical = 7.dp)
                .border(2.dp, Color.Black, shape = CircleShape)
                .clip(CircleShape)
                .background(Color.White)
                .padding(14.dp)
                .widthIn(max = 270.dp)
        ) {
            Text(
                text = message,
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

// App preview for the Composable
@Preview(showBackground = true)
@Composable
fun DiceRollerApp(modifier: Modifier = Modifier) {
    DiceWithButtonAndImage()
}

