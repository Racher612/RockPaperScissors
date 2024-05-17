package com.example.myapplication.features.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myapplication.R
import com.example.myapplication.features.navigation.Screen

@Composable
fun Logo(){
    Image(
        painter = painterResource(id = R.drawable.logo),
        contentDescription = null,
        modifier = Modifier.size(300.dp)
    )
}

@Composable
fun PlayButton(
    navigateToScreen : (String) -> Unit,
    viewModel: MainScreenViewModel
){
    Button(
        onClick = { navigateToScreen(Screen.Game.route + "/" + viewModel.rounds.intValue.toString()) },
        colors = ButtonDefaults.buttonColors(
            colorResource(id = R.color.gold),
            colorResource(id = R.color.black)
        ),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.width(208.dp)
    ){
        Text(
            text = stringResource(id = R.string.play),
            fontSize = 24.sp
        )
    }
}

@Composable
fun DropDown(
    viewModel: MainScreenViewModel
){
    val expanded = rememberSaveable { mutableStateOf(false) }

    Column {
        Button(
            onClick = { expanded.value = !expanded.value },
            colors = ButtonDefaults.buttonColors(
                colorResource(id = R.color.gold),
                colorResource(id = R.color.black)
            ),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.width(208.dp)
        ){
            Text(
                stringResource(id = R.string.rounds) +
                        ": " + viewModel.rounds.value,
                fontSize = 16.sp
            )
        }
        DropdownMenu(
            expanded = expanded.value,
            offset = DpOffset(0.dp, 0.dp),
            onDismissRequest = { expanded.value = !expanded.value },
            modifier = Modifier
                .background(colorResource(id = R.color.green))
                .padding(0.dp)
        ) {
            listOf(1, 3, 5).forEach{
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                ){
                    DropdownMenuItem(
                        text = { Text(stringResource(id = R.string.rounds) + ": " + it.toString()) },
                        onClick = {
                            viewModel.rounds.value = it
                            expanded.value = !expanded.value
                        },
                        modifier = Modifier
                            .width(208.dp)
                            .background(
                                colorResource(id = R.color.gold),
                                RoundedCornerShape(8.dp)
                            )
                            .border(
                                2.dp,
                                colorResource(id = R.color.black),
                                RoundedCornerShape(8.dp)
                            )
                            .clip(RoundedCornerShape(8.dp))
                    )
                }
            }
        }
    }
}




@Composable
fun MainScreen(
    navigateToScreen : (String) -> Unit,
    viewModel: MainScreenViewModel = hiltViewModel()
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround,
        modifier = Modifier
            .background(colorResource(id = R.color.green))
            .fillMaxSize()
    ) {
        Logo()
        DropDown(
            viewModel
        )
        PlayButton(
            navigateToScreen,
            viewModel
        )
    }
}