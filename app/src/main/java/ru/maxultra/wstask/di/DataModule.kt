package ru.maxultra.wstask.di

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.Request
import ru.maxultra.wstask.data.entities.SocketRequest
import ru.maxultra.wstask.data.network.WSS_ENDPOINT

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().build()
    }

    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    }

    @Provides
    fun provideSocketRequestAdapter(moshi: Moshi): JsonAdapter<SocketRequest> {
        return moshi.adapter(SocketRequest::class.java)
    }

    @Provides
    fun provideWebSocketRequest(): Request {
        return Request.Builder().url(WSS_ENDPOINT).build()
    }
}
