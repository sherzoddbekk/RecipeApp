package com.example.recipeapp.Navigation

sealed class Screen(val route: String) {
    object OfferList: Screen(route = "offersList")
    object DetailedView: Screen(route = "detailedView")
    object DetailedViewOfferId: Screen(route = "detailedView/{offerId}")
    object Authorization: Screen(route = "authorization")
    object Settings: Screen(route = "settings")
}