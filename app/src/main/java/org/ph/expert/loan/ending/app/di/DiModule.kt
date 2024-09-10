package org.ph.expert.loan.ending.app.di

import org.ph.expert.loan.ending.app.data.repository.RepositoryAnalyticImpl
import org.ph.expert.loan.ending.app.data.repository.RepositoryServerImpl
import org.ph.expert.loan.ending.app.data.repository.ServiceImpl
import org.ph.expert.loan.ending.app.data.repository.SharedKeeperImpl
import org.ph.expert.loan.ending.app.domain.repository.RepositoryAnalytic
import org.ph.expert.loan.ending.app.domain.repository.RepositoryServer
import org.ph.expert.loan.ending.app.domain.repository.Service
import org.ph.expert.loan.ending.app.domain.repository.SharedKeeper
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class DiModule {

    @Binds
    @Singleton
    abstract fun bindService(service: ServiceImpl): Service

    @Binds
    @Singleton
    abstract fun bindKeeper(sharedKeeper: SharedKeeperImpl): SharedKeeper

    @Binds
    @Singleton
    abstract fun bindRepositoryAnalytic(repository: RepositoryAnalyticImpl): RepositoryAnalytic

    @Binds
    @Singleton
    abstract fun bindRepositoryServer(repository: RepositoryServerImpl): RepositoryServer

}