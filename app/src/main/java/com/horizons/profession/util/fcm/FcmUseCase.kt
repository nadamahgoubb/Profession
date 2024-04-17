package com.horizons.profession.util.fcm


import com.horizons.profession.base.BaseUseCase
import com.horizons.profession.base.DevResponse
import com.horizons.profession.base.ErrorResponse
import com.horizons.profession.base.NetworkResponse
import com.horizons.profession.data.dataSource.repoistry.PrefsHelper
import com.horizons.profession.data.dataSource.repositoy.Repository
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton



private const val TAG = "FcmUseCase"

@Singleton
class FcmUseCase @Inject constructor(
    val repo: Repository
)  :  BaseUseCase<DevResponse<Any>, Any>() {

    fun generateFcmToken(callBack: (String?) -> Unit = {}) {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                return@OnCompleteListener
            }
            // Get new FCM registration token
            val token = task.result
            PrefsHelper.setFCMToken(token)
            sendFcmTokenToServer(FcmParam(token))
        })
    }

    fun generateFcmTokenIfNotExist() {

        if (PrefsHelper.getFcmToken().isNullOrBlank()) {
            generateFcmToken()
        }
    }
    fun sendFcmTokenToServer(params: FcmParam) {
      invoke(CoroutineScope(Dispatchers.IO), params = params)
    }


    override fun executeRemote(params: Any?): Flow<NetworkResponse<DevResponse<Any>, ErrorResponse>> {
        return flow {
            emit(repo.updateFcm(params!! as FcmParam))
        }
    }

}