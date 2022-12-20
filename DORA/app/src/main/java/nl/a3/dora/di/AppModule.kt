package nl.a3.dora.di

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import nl.a3.dora.data.data_source.DoraDB
import nl.a3.dora.viewmodel.PoiViewModel
import nl.a3.dora.viewmodel.RouteViewModel
import nl.a3.dora.DoraApp
import nl.a3.dora.data.repository.PoiRepositoryImpl
import nl.a3.dora.data.repository.RouteRepositoryImpl
import nl.a3.dora.data.repository.test.DummyRepoPoi
import nl.a3.dora.data.repository.test.DummyRepoRoute
import nl.a3.dora.viewmodel.repository.PoiRepository
import nl.a3.dora.viewmodel.repository.RouteRepository
import javax.inject.Singleton

/**
 * @brief Dagger-Hilt Module which takes care of the necessary Dependency Injections (DI)
 * @see PoiViewModel,
 * @see RouteViewModel
 * @see DoraApp
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
        return PoiRepositoryImpl(db.poiDao())
    }

    @Provides
    @Singleton
    fun provideRouteRepository(db: DoraDB): RouteRepository {
        //return RouteRepositoryImpl(db.routeDao())
        return DummyRepoRoute()
    }

}