package com.horizons.profession.base


import com.horizons.profession.util.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class BaseUseCase<RequestType : BaseResponse, params : Any> :
    BaseCommonUseCase<RequestType, params>() {
    fun invoke(
        scope: CoroutineScope,
        params: params? = null,
        onResult: (Resource<RequestType>) -> Unit = {}
    ) {
        scope.launch(handler(onResult) + Dispatchers.Main) {
            onResult.invoke(Resource.loading())
            runFlow(executeRemote(params), onResult).collect {
             when (it) {
                    is NetworkResponse.Success -> {
                        if (it.code == 200
                        ) {
                                onResult.invoke(Resource.success(it.body))

                        } else {
                            showFailureMessage(onResult, it.body.message)
                        }
                    }

             is NetworkResponse.NetworkError -> {
                 if(it.error.message?.contains("professions.")== true) {
                     showFailureMessage(
                         onResult,
                         "Connection Error"
                     )
                 }
                 else showFailureMessage(
                     onResult,
                     it.error.message
                 )

             }
                    is NetworkResponse.UnknownError -> showFailureMessage(
                        onResult,
                        it.error.toString()
                    )
                    is NetworkResponse.ServerError -> {


                        showFailureMessage(onResult, it.body?.Error)

                    }
                 else -> {
                  //    showFailureMessage(onResult, it.)


                 }

                }
                    onResult.invoke(Resource.loading(false))
                }
            }
        }
    }
