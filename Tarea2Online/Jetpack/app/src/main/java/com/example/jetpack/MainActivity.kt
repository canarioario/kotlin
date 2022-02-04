package com.example.jetpack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpack.ui.theme.JetpackTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            Mycomp()
        }
    }
}

@Composable
fun Mycomp() {
    Row(modifier = Modifier.fillMaxSize().background(Color.White)){
        Myimg()
        MyTexts()

    }
}
@Composable
fun Myimg(){
    Column(modifier = Modifier.padding(8.dp)) {
        Image(painterResource(R.drawable.ic_baseline_home_24),
            "Imagen de prueba"
        )
    }

}
@Composable
fun MyTexts(){
    Column() {
        MyText(text = "Bienveido a mi Jeckpack!")
        MyText("¿Estas Preparado?")
    }

}
@Composable
fun MyText(text: String){
    Text(text)

}
@Preview
@Composable
fun PreviwComp(){
    Mycomp()
}