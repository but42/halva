package com.but42.halva.repository

import dagger.Binds
import dagger.Module
import io.reactivex.Completable
import io.reactivex.Flowable
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Mikhail Kuznetsov on 30.07.2018.
 *
 * @author Mikhail Kuznetsov
 */
interface Repository {
    fun request(spec: RequestSpecification): Completable
    fun <O> query(spec: QuerySpecification<O>): Flowable<O>
}

class RepositoryImpl @Inject constructor() : Repository {
    override fun request(spec: RequestSpecification): Completable {
        TODO("not implemented")
    }

    override fun <O> query(spec: QuerySpecification<O>): Flowable<O> {
        TODO("not implemented")
    }
}

@Module interface RepositoryModule {
    @Binds @Singleton fun bindsRepository(repository: RepositoryImpl) : Repository
}