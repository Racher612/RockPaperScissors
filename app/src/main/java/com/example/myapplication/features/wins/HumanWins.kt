package com.example.myapplication.features.wins

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.example.myapplication.R
import com.example.myapplication.features.navigation.Screen


@Composable
fun HumanWins(
    navigateToScreen : (String) -> Unit,
){
    Scaffold() {padding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .padding(padding)
                .background(colorResource(id = R.color.green))
                .fillMaxSize()
        ) {
            Text(text = stringResource(id = R.string.human_wins), textAlign = TextAlign.Center)
            Image(painter = painterResource(id = R.drawable.human), contentDescription = null)
            Button(
                onClick = { navigateToScreen(Screen.Main.route) },
                colors = ButtonDefaults.buttonColors(
                    colorResource(id = R.color.gold),
                    colorResource(id = R.color.black)
                )
            ){
                Text(stringResource(id = R.string.human_ok))
            }
        }
    }
}
