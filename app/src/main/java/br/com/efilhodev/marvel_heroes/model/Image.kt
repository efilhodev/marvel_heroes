package br.com.efilhodev.marvel_heroes.model

import androidx.room.Entity
import java.io.Serializable

@Entity
data class Image(val path: String, val extension: String) : Serializable {

    fun getLandscapePath(): String {
        return "$path/landscape_xlarge.$extension"
    }

    fun getPortraitPath(): String {
        return "$path/portrait_fantastic.$extension"
    }
}
