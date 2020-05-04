package br.com.efilhodev.marvel_heroes.plugin.repository

import br.com.efilhodev.marvel_heroes.plugin.retrofit.API
import javax.inject.Inject

abstract class BaseRepositoryImpl {
    @Inject
    lateinit var api: API
}