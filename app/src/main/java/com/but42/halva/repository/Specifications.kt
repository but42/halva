package com.but42.halva.repository

/**
 * Created by Mikhail Kuznetsov on 30.07.2018.
 *
 * @author Mikhail Kuznetsov
 */
interface Specification

@Suppress("unused")
interface QuerySpecification<O> : Specification

interface RequestSpecification : Specification