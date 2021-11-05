package com.example.app.network.fixer

import android.util.Base64
import dagger.Module
import dagger.Provides
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.concurrent.TimeUnit

@Module
class FixerApiModule {

    @Provides
    internal fun provideInterceptor(): Interceptor {
        return Interceptor { chain ->
            val original: Request = chain.request()
            val originalHttpUrl: HttpUrl = original.url()
            val url = originalHttpUrl.newBuilder()
                .addQueryParameter("access_key",
                    String(Base64.decode(FixerConstants.NOT_SO_SECRET, Base64.DEFAULT)))
                .build()
            val requestBuilder: Request.Builder = original.newBuilder()
                .url(url)
            val request: Request = requestBuilder.build()
            chain.proceed(request)
        }
    }

    @Provides
    internal fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }

    @Provides
    internal fun provideOkHttpClient(fixerApiKeyInterceptor: Interceptor,
                                     loggingInterceptor: HttpLoggingInterceptor): OkHttpClient  {
        return OkHttpClient.Builder()
            .addInterceptor(fixerApiKeyInterceptor)
            .addInterceptor(loggingInterceptor)
            .readTimeout(FixerConstants.HTTP_REQUEST_TIMEOUT_SEC, TimeUnit.SECONDS)
            .connectTimeout(FixerConstants.HTTP_REQUEST_TIMEOUT_SEC, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    internal fun provideRetrofitService(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(FixerAPI.BASE_URL)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(JacksonConverterFactory.create())
            .client(client)
            .build()
    }

    @Provides
    internal fun provideFixerApi(retrofitService: Retrofit): FixerAPI {
        return retrofitService.create(FixerAPI::class.java)
    }
}