package br.com.efilhodev.marvel_heroes.feature.home.business

data class PageParams(var limit: Int, var offset: Int, var query: String? = null)