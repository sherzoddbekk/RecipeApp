package com.example.recipeapp.DetailedView

import android.app.Activity
import android.content.Context
import android.content.Intent
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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.recipeapp.Network.MyResponse.MyResponse
import com.example.recipeapp.Recipe.Recipe
import com.example.recipeapp.R
import com.example.recipeapp.edit.EditActivity

@Composable
fun DetailedView(offerId: String,
                 viewModel: DetailedViewModel = DetailedViewModel(offerId)
) {

    val context = LocalContext.current
    val offer by viewModel.recipeLiveData.observeAsState()
    val recipe by viewModel.recipeData.observeAsState()

    val navController = rememberNavController()
    val isProgressVisible = remember { mutableStateOf(false) }

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
            DetailedViewBtns(viewModel, offer!!, navController, isProgressVisible, context)
        }
    }

    recipe?.let {ProgressWidget(
            response = it,
            isVisible = isProgressVisible.value,
            context
        )
    }

//    if (recipe!!.code == 200){
//        navController.navigateUp()
//    }
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
private fun DetailedViewBtns(viewModel: DetailedViewModel, offer: Recipe, navController: NavController, isProgressVisible: MutableState<Boolean>, context: Context){
    Row(modifier = Modifier
        .fillMaxWidth()
        .height(100.dp)
        .width(400.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom){
        Button(onClick = {
            navController.navigateUp()
            viewModel.deleteOneRecipeById(offer.id.toString())
            isProgressVisible.value = true
        }) {
            Text(
                text = stringResource(id = R.string.deleteDetailedViwe),
                color = Color.White,
                fontSize = 18.sp
            )
        }
        Button(onClick = {
            val intent = Intent(context, EditActivity::class.java)
            intent.putExtra("id", offer.id.toString())
            intent.putExtra("title", offer.name)
            intent.putExtra("description", offer.description)
            context.startActivity(intent)
        }) {
            Text(
                text = stringResource(id = R.string.editDetailedViwe),
                color = Color.White,
                fontSize = 18.sp
            )
        }
    }
}

@Composable
private fun ProgressWidget(response: MyResponse, isVisible: Boolean, context: Context) {
    if (isVisible) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent)
        ) {
            Text(
                modifier = Modifier
                    .background(Color.Gray)
                    .padding(20.dp)
                    .align(Alignment.Center),
                fontSize = 25.sp,
                text =
                if (response.status.isEmpty()) stringResource(id = R.string.add_new_in_progress_mgs)
                else if (response.status == "OK")  stringResource(id = R.string.deleted_successfully_msg)
                else stringResource(id = R.string.failed_to_delete_msg)
            )
        }

        (LocalContext.current as Activity).onBackPressed()
//        context.startActivity(Intent(context, MainActivity::class.java))
    }
}