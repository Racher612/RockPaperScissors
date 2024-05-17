package com.example.myapplication.features.game

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myapplication.R
import com.example.myapplication.features.navigation.Screen
import kotlinx.coroutines.delay


fun randomFigure() : Int{
    return listOf(
        Figure.Rock.name,
        Figure.Paper.name,
        Figure.Scissors.name
    ).random()
}

fun Rules(
    playerChoice : Int,
    computerChoice: Int
) : Int {
    // 0 - draw, 1 - player wins, -1 - computer wins
    if (playerChoice == computerChoice){
        return 0
    }
    when(playerChoice) {
        R.drawable.rock -> when(computerChoice){
            R.drawable.scissors -> return 1
            R.drawable.paper -> return -1
        }
        R.drawable.paper -> when(computerChoice){
            R.drawable.rock -> return 1
            R.drawable.scissors -> return -1
        }
        R.drawable.scissors -> when(computerChoice){
            R.drawable.paper -> return 1
            R.drawable.rock -> return -1
        }
    }
    return 0
}

@Composable
fun RoundedText(
    text : String,
    backGround : Color,
    fontColor : Color
    ){
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(42.dp)
            .background(backGround, CircleShape)
            .clip(CircleShape)
    ){
        Text(
            text = text,
            color = fontColor,
            textAlign = TextAlign.Center,
            fontSize = 24.sp,
            )
    }
}

@Composable
fun ButtonBack(
    navigateToScreen: (String) -> Unit
){
    Button(
        onClick = { navigateToScreen(Screen.Main.route) },
        contentPadding = PaddingValues(0.dp),
        colors = ButtonDefaults.buttonColors(
            colorResource(id = R.color.gold),
            colorResource(id = R.color.green)
        ),
        modifier = Modifier
            .clip(CircleShape)
            .size(60.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.back),
            contentDescription = null
        )
    }
}

@Composable
fun TopBar(
    navigateToScreen: (String) -> Unit,
    computerWins : Int,
    playerWins : Int,
    rounds: Int,
    viewModel: GameScreenViewModel
){

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(colorResource(id = R.color.gold))
            .fillMaxWidth()
            .padding(top = 36.dp)
            .height(60.dp)
    ){
        ButtonBack {
            navigateToScreen(it)
            viewModel.play.value = true
            viewModel.clear()
            viewModel.clearCount()
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = stringResource(id = R.string.count) + ":",
                fontSize = 28.sp,
            )
            RoundedText(
                text = computerWins.toString(),
                backGround = colorResource(id = R.color.red),
                fontColor = colorResource(id = R.color.gold)
            )
            Text(
                text = "-",
                fontSize = 28.sp
            )
            RoundedText(
                text = playerWins.toString(),
                backGround = colorResource(id = R.color.green),
                fontColor = colorResource(id = R.color.black)
            )
            Text(
                text = " | ",
                fontSize = 28.sp
            )
            Text(
                text = rounds.toString(),
                fontSize = 28.sp
            )
        }
        Spacer(Modifier.width(40.dp))
    }
}

@Composable
fun DisplayFigure(
    icon : Int,
    size : Int,
    modifier: Modifier = Modifier
){
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .clip(CircleShape)
            .size(size.dp)
            .background(colorResource(id = R.color.gold))
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = null,
            modifier = Modifier.size((size - 40).dp),
            tint = colorResource(id = R.color.black)

        )
    }
}

@Composable
fun ButtonFigure(
    icon : Int,
    size : Int,
    enabled : Boolean,
    onClick : () -> Unit,
    modifier: Modifier = Modifier
){
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            colorResource(id = R.color.gold),
            colorResource(id = R.color.black)
        ),
        enabled = enabled,
        modifier = modifier
            .clip(CircleShape)
            .size(size.dp)
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = null,
            modifier = Modifier.size((size - 40).dp)
        )
    }
}

@Composable
fun ResultScreen(
    padding: PaddingValues,
    viewModel: GameScreenViewModel
) {
    val waiting by viewModel.waiting

    LaunchedEffect(key1 = waiting) {
        delay(500)

        viewModel.waiting.value = false
        viewModel.playerDisplayFigure = viewModel.playerChoice
        viewModel.computerDisplayFigure = viewModel.computerChoice

        delay(500)

        val result = Rules(
            viewModel.playerChoice.intValue,
            viewModel.computerChoice.intValue
        )
        if (result == -1){
            viewModel.computerFigureSize.intValue = ButtonSize.Win.size
            viewModel.computerWins.intValue += 1
        }
        if (result == 1){
            viewModel.playerFigureSize.intValue = ButtonSize.Win.size
            viewModel.playerWins.intValue += 1
        }
    }

    Column(
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(padding)
            .background(colorResource(id = R.color.green))
            .fillMaxSize()
    ){
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(11f)
        ){
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(24.dp)
            ){
                DisplayFigure(
                    icon = viewModel.computerDisplayFigure.intValue,
                    size = viewModel.computerFigureSize.intValue,
                )
            }
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(24.dp)
            ){
                DisplayFigure(
                    icon = viewModel.playerDisplayFigure.intValue,
                    size = viewModel.playerFigureSize.intValue,
                )
            }
        }
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .weight(2f)
        ){
            Button(
                onClick = {
                    viewModel.play.value = !viewModel.play.value
                    viewModel.clear()
                },
                colors = ButtonDefaults.buttonColors(
                    colorResource(id = R.color.gold),
                    colorResource(id = R.color.black)
                ),
            ){
                Text(
                    text = stringResource(id = R.string.next_round),
                    fontSize = 24.sp
                )
            }
        }

    }
}

@Composable
fun PrepareScreen(
    padding : PaddingValues,
    viewModel: GameScreenViewModel
){
    Column(
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(padding)
            .fillMaxSize()
            .background(colorResource(id = R.color.green))
    ){
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = stringResource(id = R.string.choose) + ":",
            fontSize = 24.sp,
            modifier = Modifier
                .background(colorResource(id = R.color.gold), RoundedCornerShape(8.dp))
                .weight(0.7f)
                .clip(RoundedCornerShape(8.dp))
                .padding(4.dp)
        )
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .weight(4f)){
            ButtonFigure(
                icon = R.drawable.rock,
                size = viewModel.rockSize.intValue,
                enabled = true,
                onClick = {
                    viewModel.playerChoice.intValue = Figure.Rock.name
                    viewModel.rockSize.intValue = ButtonSize.Large.size
                    viewModel.paperSize.intValue = ButtonSize.Small.size
                    viewModel.scissorsSize.intValue = ButtonSize.Small.size
                },
                modifier = Modifier
            )
        }

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .weight(4f)
        ) {
            ButtonFigure(
                icon = R.drawable.paper,
                size = viewModel.paperSize.intValue,
                enabled = true,
                onClick = {
                    viewModel.playerChoice.intValue = Figure.Paper.name
                    viewModel.paperSize.intValue = ButtonSize.Large.size
                    viewModel.rockSize.intValue = ButtonSize.Small.size
                    viewModel.scissorsSize.intValue = ButtonSize.Small.size
                },
                modifier = Modifier
            )
            ButtonFigure(
                icon = R.drawable.scissors,
                size = viewModel.scissorsSize.intValue,
                enabled = true,
                onClick = {
                    viewModel.playerChoice.intValue = Figure.Scissors.name
                    viewModel.scissorsSize.intValue = ButtonSize.Large.size
                    viewModel.paperSize.intValue = ButtonSize.Small.size
                    viewModel.rockSize.intValue = ButtonSize.Small.size
                },
                modifier = Modifier
            )
        }
        Box(
            modifier = Modifier
                .weight(1.5f)
        ){
            Button(
                onClick = {
                    viewModel.play.value = !viewModel.play.value
                    viewModel.computerChoice.intValue = randomFigure()
                },
                colors = ButtonDefaults.buttonColors(
                    colorResource(id = R.color.gold),
                    colorResource(id = R.color.black)
                )
            ){
                Text(
                    text = stringResource(id = R.string.play),
                    fontSize = 24.sp
                )
            }
        }
    }
}

@Composable
fun GameScreen(
    navigateToScreen : (String) -> Unit,
    rounds : Int,
    viewModel: GameScreenViewModel = hiltViewModel()
){
    val computerWins by viewModel.computerWins
    val playerWins by viewModel.playerWins

    if (viewModel.playerWins.intValue == rounds){
        LaunchedEffect(key1 = true) {
            delay(1000)
            viewModel.clear()
            viewModel.clearCount()
            navigateToScreen(Screen.HumanVictory.route)
        }
    }

    if (viewModel.computerWins.intValue == rounds){
        LaunchedEffect(key1 = true) {
            delay(1000)
            viewModel.clear()
            viewModel.clearCount()
            navigateToScreen(Screen.RobotVictory.route)
        }
    }

    Scaffold(
        topBar = { TopBar(navigateToScreen, computerWins, playerWins, rounds, viewModel) },
        modifier = Modifier
            .background(colorResource(id = R.color.green))
    ) {padding ->
        if (viewModel.play.value){
            PrepareScreen(
                padding = padding,
                viewModel = viewModel
            )
        } else {
            ResultScreen(
                padding,
                viewModel
            )
        }
    }
}


