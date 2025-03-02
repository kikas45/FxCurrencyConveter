package com.example.simplecomposeproject.RateCoverter.di
import android.content.Context
import androidx.room.Room
import com.example.simplecomposeproject.RateCoverter.fxdates.RateRepository
import com.example.simplecomposeproject.RateCoverter.fxdates.api.RateAPI
import com.example.simplecomposeproject.RateCoverter.offlinehistory.RatesDao
import com.example.simplecomposeproject.RateCoverter.offlinehistory.RatesDatabase
import com.example.simplecomposeproject.RateCoverter.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideRateAPI(retrofit: Retrofit): RateAPI {
        return retrofit.create(RateAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideRateRepository(api: RateAPI): RateRepository {
        return RateRepository(api)
    }




    // ✅ Provide Room Database (using Application Context)
    @Provides
    @Singleton
    fun provideRatesDatabase(@ApplicationContext context: Context): RatesDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            RatesDatabase::class.java,
            "user_database"
        ).build()
    }

    // ✅ Provide DAO
    @Provides
    fun provideRatesDao(database: RatesDatabase): RatesDao {
        return database.rateDao()
    }


}