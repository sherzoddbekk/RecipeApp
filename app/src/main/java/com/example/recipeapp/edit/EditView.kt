package com.example.recipeapp.edit

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.Gravity
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.recipeapp.DetailedView.DetailedViewModel
import com.example.recipeapp.MainActivity
import com.example.recipeapp.R
import com.example.recipeapp.Network.MyResponse.MyResponse
import com.example.recipeapp.Network.Offer.OfferRequest


@Composable
fun EditView(offerId: String, title: String, description: String,
               viewModel: DetailedViewModel = DetailedViewModel(offerId)
) {
    val context = LocalContext.current

    val name = remember { mutableStateOf(title) }
    val description = remember { mutableStateOf(description) }

    val isProgressVisible = remember { mutableStateOf(false) }

    val response by viewModel.editRecipeData.observeAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White)
            .padding(horizontal = 16.dp, vertical = 90.dp)
            .verticalScroll(rememberScrollState())
    ) {
        NameInput(
            name = name.value,
            onNameChange = { name.value = it })
        Spacer(Modifier.height(16.dp))

        DescriptionInput(
            description = description.value,
            onDescriptionChange = { description.value = it })
        Spacer(Modifier.height(16.dp))

        val validationMsg = stringResource(id = R.string.validationMsg)
        AddNewButton {
            if (isInputValid(name.value, description.value)) {
                viewModel.editOfferById(offerId,
                    OfferRequest(
                        name.value,
                        description.value
                    )
                )

                isProgressVisible.value = true

            } else {
                val toast = Toast.makeText(context, validationMsg, Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.CENTER, 0, 0)
                toast.show()
            }
        }
    }

    response?.let { ProgressWidget(response = it, isVisible = isProgressVisible.value, context) }
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
                else if (response.status == "OK")  stringResource(id = R.string.updated_successfully_msg)
                else stringResource(id = R.string.failed_to_update_msg)
            )
        }

        (context as Activity).finishAffinity()
        context.startActivity(Intent(context, MainActivity::class.java))
    }
}

@Composable
private fun NameInput(name: String, onNameChange: (String) -> Unit) {
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.LightGray),
        value = name,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        onValueChange = { onNameChange(it) },
        label = {
            Text(stringResource(id = R.string.Title))
        }
    )
}


@Composable
private fun DescriptionInput(description: String, onDescriptionChange: (String) -> Unit) {
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .background(color = Color.LightGray),
        value = description,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        onValueChange = { onDescriptionChange(it) },
        label = {
            Text(
                stringResource(id = R.string.Description)
            )
        }
    )
}

@Composable
private fun AddNewButton(onClick: () -> Unit) {

    Button(
        onClick = {
            onClick()
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(vertical = 20.dp)
    ) {
        Text(
            text = stringResource(id = R.string.edit_button_text)
        )
    }
}

private fun isInputValid(
    name: String,
    description: String
): Boolean {
    if (name.isBlank() || description.isBlank()) return false

    return true
}