package com.example.recipeapp.PreferencesDataStore

import android.view.Gravity
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.recipeapp.R
import kotlinx.coroutines.launch

@Composable
fun Login() {
    // context
    val context = LocalContext.current
    // scope
    val scope = rememberCoroutineScope()
    // datastore Email
    val dataStore = StoreUserEmail(context)
    // get saved email
    val savedEmail = dataStore.getEmail.collectAsState(initial = "")

    val validationMsgEmpty = stringResource(id = R.string.validationMsg)
    val validationMsgFilled = "Welcome back ${savedEmail.value} :)"

    var email by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 90.dp)
    ) {
        Text(
            modifier = Modifier
                .padding(16.dp, top = 30.dp),
            text = stringResource(id = R.string.Authorization),
            color = Color.Gray,
            fontSize = 20.sp
        )
        //email field
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = email,
            onValueChange = { email = it },
        )
        Spacer(modifier = Modifier.height(20.dp))
        // save button
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            onClick = {
                //launch the class in a coroutine scope
                scope.launch {
                    dataStore.saveEmail(email)
                    if(email.trim().isEmpty()) {
                        val toast = Toast.makeText(context, validationMsgEmpty, Toast.LENGTH_SHORT)
                        toast.setGravity(Gravity.CENTER, 0, 0)
                        toast.show()
                    }
                    else{
                        val toast = Toast.makeText(context, validationMsgFilled, Toast.LENGTH_SHORT)
                        toast.setGravity(Gravity.CENTER, 0, 0)
                        toast.show()
                    }
                }
            },
        ) {
            // button text
            Text(
                text = stringResource(id = R.string.Login),
                color = Color.White,
                fontSize = 18.sp
            )
        }
        if(savedEmail.value!! != ""){
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp, horizontal = 14.dp),
                text = "Hello: " + savedEmail.value!!,
                color = Color.Black,
                fontSize = 30.sp
            )
        }
    }
}

