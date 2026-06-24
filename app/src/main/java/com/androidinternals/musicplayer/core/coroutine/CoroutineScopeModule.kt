package com.androidinternals.musicplayer.core.coroutine

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
object CoroutineScopeModule {
    @ApplicationScopeDefaultThread
    @Provides
    @Singleton
    fun provideDefaultScope(): CoroutineScope = CoroutineScope(Dispatchers.Default)

    @ApplicationScopeIoThread
    @Provides
    @Singleton
    fun provideIoScope(): CoroutineScope = CoroutineScope(Dispatchers.IO)

    @ApplicationScopeMainThread
    @Provides
    @Singleton
    fun provideMainScope(): CoroutineScope = CoroutineScope(Dispatchers.Main)

}