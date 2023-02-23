package com.plcoding.tracker_domain.di

import com.plcoding.core.data.preferences.Preferences
import com.plcoding.tracker_domain.repository.TrackerRepository
import com.plcoding.tracker_domain.use_case.CalculateMealNutrients
import com.plcoding.tracker_domain.use_case.DeleteTrackedFood
import com.plcoding.tracker_domain.use_case.GetFoodsForDate
import com.plcoding.tracker_domain.use_case.SearchFood
import com.plcoding.tracker_domain.use_case.TrackFood
import com.plcoding.tracker_domain.use_case.TrackerUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object TrackerDomainModule {

    @ViewModelScoped
    @Provides
    fun provideTrackerUseCase(
        repository: TrackerRepository,
        preferences: Preferences
    ): TrackerUseCases {
        return TrackerUseCases(
            trackFood = TrackFood(repository = repository),
            searchFood = SearchFood(repository = repository),
            getFoodsForDate = GetFoodsForDate(repository = repository),
            deleteTrackedFood = DeleteTrackedFood(repository = repository),
            calculateMealNutrients = CalculateMealNutrients(preferences = preferences)
        )
    }
}