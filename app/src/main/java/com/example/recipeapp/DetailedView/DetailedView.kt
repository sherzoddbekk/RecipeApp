package com.example.recipeapp.DetailedView

import androidx.compose.foundation.background
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.res.stringResource
import coil.compose.rememberAsyncImagePainter
import com.example.recipeapp.R

@Composable
fun DetailedView(offerId: String,
                 viewModel: DetailedViewModel = DetailedViewModel(offerId)
) {

    val offer by viewModel.offerLiveData.observeAsState()

    if (offer != null) {
        Column(
            modifier = Modifier
                .background(colorResource(id = R.color.offer_list_bg))
                .padding(horizontal = 16.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter("https://images.pexels.com/photos/6248902/pexels-photo-6248902.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1"),
                contentDescription = null,
                modifier = Modifier.size(400.dp)
            )
            Name(name = "Title: " + offer!!.name)
            Description(description = "Description: " + offer!!.description)
            MyDivider()
            Spacer(Modifier.height(16.dp))
            DetailedViewBtns()
        }
    }
}

@Composable
private fun Name(name: String) {
    Text(
        text = name,
        color = Color.Black,
        fontSize = 30.sp,
        fontFamily = FontFamily.Serif,
        textAlign = TextAlign.Center
    )
}

@Composable
private fun Description(description: String) {
    Text(
        modifier = Modifier.padding(top = 10.dp),
        text = description,
        color = Color.DarkGray,
        fontSize = 22.sp,
        fontFamily = FontFamily.SansSerif
    )
}

@Composable
private fun MyDivider() {
    Divider(
        color = Color.LightGray
    )
}

@Composable
private fun DetailedViewBtns(){
    Row(modifier = Modifier
        .fillMaxWidth()
        .height(100.dp)
        .width(400.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom){
        Button(onClick = { }) {
            Text(
                text = stringResource(id = R.string.deleteDetailedViwe),
                color = Color.White,
                fontSize = 18.sp
            )
        }
        Button(onClick = { }) {
            Text(
                text = stringResource(id = R.string.editDetailedViwe),
                color = Color.White,
                fontSize = 18.sp
            )
        }
    }
}