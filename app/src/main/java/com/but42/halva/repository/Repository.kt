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
    private val items: List<ListItem>

    init {
        val time = "13:46"
        val string = StringBuilder("0")
        val list = mutableListOf(ListItem(string.toString(), time))
        for (i in 1..50) {
            string.append(" $i")
            list.add(ListItem(string.toString(), time))
        }
        items = list
    }

    override fun request(spec: RequestSpecification): Completable {
        TODO("not implemented")
    }

    @Suppress("UNCHECKED_CAST")
    override fun <O> query(spec: QuerySpecification<O>): Flowable<O> = when (spec) {
        is GetListSpec -> Flowable.just(items)
        else -> throw IllegalArgumentException("Wrong query specification ${spec.javaClass.simpleName}")
    }.map { it as O }
}

@Module interface RepositoryModule {
    @Binds @Singleton fun bindsRepository(repository: RepositoryImpl) : Repository
}