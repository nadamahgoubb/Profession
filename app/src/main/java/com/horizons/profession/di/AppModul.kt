package com.horizons.profession.di


import android.content.Context
import com.horizons.profession.data.dataSource.remote.ApiInterface
import com.horizons.profession.data.dataSource.repositoy.Repository
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModul {

    @Singleton
    @Provides
    fun provideRepository(
        api: ApiInterface
    ): Repository = Repository(api)

    @Singleton
    @Provides
    fun provideFusedLocationClient(@ApplicationContext context: Context) =
        LocationServices.getFusedLocationProviderClient(context)

}