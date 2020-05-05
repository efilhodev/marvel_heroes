package br.com.efilhodev.marvel_heroes.feature.home.business

data class PageParams(var limit: Int, var offset: Int = 0, var query: String? = null)