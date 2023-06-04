package com.example.recipeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.recipeapp.ui.theme.RecipeAppTheme
import com.example.recipeapp.DetailedView.DetailedView
import com.example.recipeapp.List.OffersList
import com.example.recipeapp.Navigation.Screen
import com.example.recipeapp.PreferencesDataStore.Login
import com.example.recipeapp.Settings.Settings
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@OptIn(ExperimentalPermissionsApi::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RecipeAppTheme() {
                val permissionsState = rememberMultiplePermissionsState(
                    permissions = listOf(
                        android.Manifest.permission.MANAGE_EXTERNAL_STORAGE,
                        android.Manifest.permission.CAMERA,
                    )
                )
                val lifecycleOwner = LocalLifecycleOwner.current
                DisposableEffect(key1 = lifecycleOwner, effect = {
                    val observer = LifecycleEventObserver {
                            _, event ->
                        if(event == Lifecycle.Event.ON_START) {
                            permissionsState.launchMultiplePermissionRequest()
                        }
                    }
                    lifecycleOwner.lifecycle.addObserver(observer)

                    onDispose {
                        lifecycleOwner.lifecycle.removeObserver(observer)
                    }
                })
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = colorResource(id = R.color.offer_list_bg)
                ) {
                    val navController = rememberNavController()
                    Scaffold(
                        backgroundColor = colorResource(id = R.color.white),
                        bottomBar = {
                            BottomNavigationBar(
                                items = listOf(
                                    BottomNavItem(
                                        name = stringResource(id = R.string.Offers),
                                        route = Screen.OfferList.route,
                                        icon = Icons.Default.List
                                    ),
                                    BottomNavItem(
                                        name = stringResource(id = R.string.Authorization),
                                        route = Screen.Authorization.route,
                                        icon = Icons.Default.Person
                                    ),
                                    BottomNavItem(
                                        name = stringResource(id = R.string.Settings),
                                        route = Screen.Settings.route,
                                        icon = Icons.Default.Settings
                                    )

                                ),
                                navController = navController,
                                onItemClick = {
                                    navController.navigate(it.route)
                                }
                            )
                        }) {
                        Navigation(navController = navController)
                    }
                }
            }
        }
    }
}

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.OfferList.route) {
        composable(
            route = Screen.OfferList.route
        ) {
            OffersList(
                onOfferClick = { offerId ->
                    navController.navigate("detailedView/$offerId")
                }
            )
        }

        composable(
            route = Screen.Authorization.route
        ) {
            Login()
        }

        composable(
            route = Screen.Settings.route
        ) {
            Settings()
        }

        composable(
            route = Screen.DetailedViewOfferId.route
        ) { backStackEntry ->
            backStackEntry.arguments?.getString("offerId")?.let { DetailedView(it) }
        }

    }
}

@Composable
fun BottomNavigationBar(
    items: List<BottomNavItem>,
    navController: NavHostController,
    modifier: Modifier = Modifier,
    onItemClick: (BottomNavItem) -> Unit
) {
    val backStackEntry = navController.currentBackStackEntryAsState()

    BottomNavigation(
        modifier = modifier,
        backgroundColor = colorResource(id = R.color.purple_500),
        elevation = 5.dp
    ) {
        items.forEach { item ->
            val selected = item.route == backStackEntry.value?.destination?.route
            BottomNavigationItem(
                selected = selected,
                onClick = { onItemClick(item) },
                selectedContentColor = Color.White,
                unselectedContentColor = Color.Black,
                icon = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(imageVector = item.icon, contentDescription = item.name)
                        Text(
                            text = item.name,
                            textAlign = TextAlign.Center,
                            fontSize = 15.sp
                        )
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview(){
    RecipeAppTheme() {
        Login()
    }
}