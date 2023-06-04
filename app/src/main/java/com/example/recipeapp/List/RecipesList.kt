package com.example.recipeapp.List

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.recipeapp.R
import com.example.recipeapp.Offer.Offer
import com.example.recipeapp.addNew.AddNewActivity

@Composable
fun OffersList(
    onOfferClick: (String) -> Unit = {},
    viewModel: ListViewModel = ListViewModel()
) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxWidth()
            .height(100.dp)
            .padding(vertical = 20.dp, horizontal = 16.dp),
        text = stringResource(id = R.string.navigation_offers_title),
        color = Color.Black,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold
    )
    val context = LocalContext.current
    Box(modifier = Modifier.fillMaxSize()) {
        val offers by viewModel.offersLiveData.observeAsState()
        LazyColumn(
            modifier = Modifier
                .fillMaxHeight()
                .padding(horizontal = 0.dp, vertical = 90.dp)
                .background(
                    colorResource(id = R.color.offer_list_bg)
                )
                .padding(0.dp, 0.dp, 0.dp, 40.dp),
        ) {
            offers?.let {
                items(items = it.toList(), itemContent = { item ->
                    OfferItem(offer = item, onOfferClick)
                })
            }
        }

//        Button(
//            modifier = Modifier
//                .fillMaxWidth()
//                .fillMaxWidth()
//                .height(220.dp)
//                .align(Alignment.BottomCenter)
//                .padding(vertical = 80.dp, horizontal = 16.dp),
//            onClick = { context.startActivity(Intent(context, AddNewActivity::class.java)) }) {
//            Text(
//                text = stringResource(id = R.string.list_add_new_text),
//                color = Color.White,
//                fontSize = 18.sp
//            )
//        }
        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(vertical = 80.dp, horizontal = 16.dp),
            onClick = {
                context.startActivity(Intent(context, AddNewActivity::class.java))
            }) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = stringResource(id = R.string.list_add_new_text)
            )
        }
    }
}

@Composable
fun OfferItem(offer: Offer, onOfferClick: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp, 10.dp, 15.dp, 0.dp)
            .clickable {
                onOfferClick(offer.id.toString())
                println(offer.id.toString())
            }
    ) {
        Name(name = offer.name)
        Description(description = offer.description)
        Divider(
            modifier = Modifier
                .padding(top = 10.dp)
                .background(colorResource(id = R.color.offer_list_bg))
        )
    }
}

@Composable
private fun Name(name: String) {
    Text(
        text = name,
        color = Color.Black,
        fontSize = 20.sp,
        fontFamily = FontFamily.Serif,
        textAlign = TextAlign.Center
    )
}

@Composable
private fun Description(description: String) {
    Text(
        text = description,
        color = Color.DarkGray,
        fontSize = 16.sp,
        fontFamily = FontFamily.SansSerif
    )
}