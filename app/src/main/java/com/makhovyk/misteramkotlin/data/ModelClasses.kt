package com.makhovyk.misteramkotlin.data

import java.util.*

object Model {
    data class AppToken(val token: String)
    data class AuthToken(val authToken: String)
    data class Position(val longitude: String, val latitude: String)
    data class Address(val title: String, val description: String)
    data class Route(val distance: Double, val duration: Long, val positions: List<Position>)
    data class Order(
        val id: Int, val active: Boolean, val type: String, val time: Long, val number: String,
        val amount: Float, val tags: List<String>, val address: Address
    )

    data class Action(val type: String, val title: String, val orderId: Int, val popupData: Any)
    data class Task(val orders: List<Order>, val route: Route, val action: Action, val callToClientPhoneCallAction: Any)
}