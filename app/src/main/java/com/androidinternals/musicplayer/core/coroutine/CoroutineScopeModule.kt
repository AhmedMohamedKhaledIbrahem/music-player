package com.androidinternals.musicplayer.core.coroutine

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

@Module
@InstallIn(SingletonComponent::class)
object CoroutineScopeModule {
    @ApplicationScopeDefaultThread
    @Provides
    @Singleton
    fun provideDefaultScope(): CoroutineScope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    @ApplicationScopeIoThread
    @Provides
    @Singleton
    fun provideIoScope(): CoroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    @ApplicationScopeMainThread
    @Provides
    @Singleton
    fun provideMainScope(): CoroutineScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

}