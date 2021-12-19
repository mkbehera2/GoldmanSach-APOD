package com.goldmansachs.assignment.model.apiservice


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Keep
@Parcelize
@Entity(tableName = "favorite")
data class ApodFavoriteEntity(

    @PrimaryKey(autoGenerate = true)
    var id: Int,
    @ColumnInfo(name = "copyright")
    val copyright: String?,
    @ColumnInfo(name = "date")
    val date: String?,
    @ColumnInfo(name = "explanation")
    val explanation: String?,
    @ColumnInfo(name = "hdurl")
    val hdurl: String?,
    @ColumnInfo(name = "media_type")
    val mediaType: String?,
    @ColumnInfo(name = "service_version")
    val serviceVersion: String?,
    @ColumnInfo(name = "title")
    val title: String?,
    @ColumnInfo(name = "url")
    val url: String?
) : Parcelable {}