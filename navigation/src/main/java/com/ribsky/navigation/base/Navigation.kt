package com.ribsky.navigation.base

interface Base<T> {

    interface Params
}

interface Navigation<T> : Base<T> {

    fun navigate(navigation: T)
}

interface NavigationWithParams<T, P : Base.Params> : Base<T> {

    fun navigate(navigation: T, params: P)
}

interface NavigationWithResult<T, R> : Base<T> {

    fun navigate(navigation: T, callback: R)
}

interface NavigationWithResultAndParams<T, P : Base.Params, R> : Base<T> {

    fun navigate(navigation: T, params: P, callback: R)
}
