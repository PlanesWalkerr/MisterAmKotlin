package com.makhovyk.misteramkotlin.data

import io.reactivex.Observable

object ApiProvider {

    const val VERSION = "1.0"
    const val TYPE = "android"

    fun provideGetAppToken(deviceId: String): Observable<Model.AppToken> {
        return ApiService.create().getAppToken(VERSION, TYPE, deviceId)
    }

    fun provideGetAuthToken(username: String, password: String, appToken: String): Observable<Model.AuthToken> {
        return ApiService.create().getAuthToken(username, password, appToken)
    }

    fun provideGetActiveTasks(authToken: String): Observable<List<Model.Task>> {
        return ApiService.create().getActiveTasks(authToken)
    }
}