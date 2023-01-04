package com.example.profession.di



import com.example.profession.data.dataSource.repoistry.PrefsHelper
import com.example.profession.base.NetworkResponseAdapterFactory
import com.example.profession.data.dataSource.remote.ApiBase
import com.example.profession.data.dataSource.remote.ApiInterface
 import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    @Provides
    @Singleton
    fun proideokHttpClient():OkHttpClient{
        val interceptor = HttpLoggingInterceptor()
            .apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

         return   OkHttpClient.Builder()
            .addInterceptor { chain: Interceptor.Chain ->
                val original = chain.request()
                val requestBuilder = original.newBuilder()
                    .method(original.method, original.body)
                    //  .addHeader(Constants.Token_HEADER ,DataStoreManger()?.read(Constants.Token_KEY))
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Accept", "application/json")
          //          .addHeader("lang", /*PrefsHelper.getLanguage()*/ "en")

                .addHeader("Authorization", "Bearer" + PrefsHelper.getToken())

                val request = requestBuilder.build()
                chain.proceed(request)
            }
            .addInterceptor(interceptor)

            .build()

    }


    var gson = GsonBuilder()
        .setLenient().disableHtmlEscaping()
        .create()

    @Provides
    @Singleton
    fun provideClient(okHttpClient:OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(ApiBase.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(NetworkResponseAdapterFactory())
            .build()


    @Provides
    @Singleton
    fun provideApiInterface(retrofit: Retrofit): ApiInterface =
        retrofit.create(ApiInterface::class.java)
}