package com.ribsky.billing

sealed interface BillingState {
    class Infinite(val date: String = "Назавжди ∞") : BillingState
    data class Discount(val date: String) : BillingState
    data class WelcomeDiscount(val date: String) : BillingState
    object NoDiscount : BillingState
}