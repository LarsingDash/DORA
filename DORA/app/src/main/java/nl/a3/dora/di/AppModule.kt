package nl.a3.dora.di

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import nl.a3.dora.data.data_source.DoraDB
import nl.a3.dora.viewmodel.PoiViewModel
import nl.a3.dora.DoraApp
import nl.a3.dora.data.repository.test.DummyRepoPoi
import nl.a3.dora.viewmodel.repository.PoiRepository
import javax.inject.Singleton

/**
 * @brief Dagger-Hilt Module which takes care of the necessary Dependency Injections (DI)
 * @see PoiViewModel,
 * @see DoraApp
 * TODO Add repository of routes to the DI
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDoraDB(app: Application): DoraDB {
        return DoraDB.getDBInstance(context= app)
    }

    @Provides
    @Singleton
    fun providePoiRepository(db: DoraDB): PoiRepository {
        return DummyRepoPoi()
    }

}