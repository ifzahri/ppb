package ppb.iftala.diceroller

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ppb.iftala.diceroller.ui.theme.DiceRollerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DiceRollerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DiceRollerApp()
                }
            }
        }
    }
}

@Composable
fun DiceRollerApp() {
    var diceValue by remember { mutableStateOf(1) }
    var isRolling by remember { mutableStateOf(false) }
    var diceHistory by remember { mutableStateOf(listOf<Int>()) }

    val rotation by animateFloatAsState(
        targetValue = if (isRolling) 360f else 0f,
        animationSpec = tween(500),
        finishedListener = {
            isRolling = false
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.app_name),
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(200.dp)
                .padding(16.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0xFFE0E0E0))
                .clickable {
                    rollDice(
                        onRollStart = { isRolling = true },
                        onNewValue = { newValue ->
                            diceValue = newValue
                            diceHistory = diceHistory + newValue
                            if (diceHistory.size > 10) {
                                diceHistory = diceHistory.drop(1)
                            }
                        }
                    )
                }
        ) {
            Image(
                painter = painterResource(getDiceImageResource(diceValue)),
                contentDescription = diceValue.toString(),
                modifier = Modifier
                    .size(140.dp)
                    .rotate(rotation)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                rollDice(
                    onRollStart = { isRolling = true },
                    onNewValue = { newValue ->
                        diceValue = newValue
                        diceHistory = diceHistory + newValue
                        if (diceHistory.size > 10) {
                            diceHistory = diceHistory.drop(1)
                        }
                    }
                )
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = stringResource(R.string.roll),
                fontSize = 18.sp
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        if (diceHistory.isNotEmpty()) {
            Text(
                text = stringResource(R.string.roll_history),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(8.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                diceHistory.forEach { value ->
                    Image(
                        painter = painterResource(getDiceImageResource(value)),
                        contentDescription = value.toString(),
                        modifier = Modifier
                            .size(40.dp)
                            .padding(horizontal = 4.dp)
                    )
                }
            }
        }
    }
}

private fun rollDice(onRollStart: () -> Unit, onNewValue: (Int) -> Unit) {
    onRollStart()
    val newValue = (1..6).random()
    onNewValue(newValue)
}

private fun getDiceImageResource(diceValue: Int): Int {
    return when (diceValue) {
        1 -> R.drawable.dice_1
        2 -> R.drawable.dice_2
        3 -> R.drawable.dice_3
        4 -> R.drawable.dice_4
        5 -> R.drawable.dice_5
        else -> R.drawable.dice_6
    }
}

@Preview(showBackground = true)
@Composable
fun DiceRollerPreview() {
    DiceRollerTheme {
        DiceRollerApp()
    }
}