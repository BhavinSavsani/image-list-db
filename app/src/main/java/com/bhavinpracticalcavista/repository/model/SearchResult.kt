package com.bhavinpracticalcavista.repository.model


import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue


data class SearchResult(
    @SerializedName("id")
    val id: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("description")
    val description: Any?,
    @SerializedName("datetime")
    val datetime: Long?,
    @SerializedName("cover")
    val cover: String?,
    @SerializedName("cover_width")
    val coverWidth: Int?,
    @SerializedName("cover_height")
    val coverHeight: Int?,
    @SerializedName("account_url")
    val accountUrl: String?,
    @SerializedName("account_id")
    val accountId: Long?,
    @SerializedName("privacy")
    val privacy: String?,
    @SerializedName("layout")
    val layout: String?,
    @SerializedName("views")
    val views: Int?,
    @SerializedName("link")
    val link: String?,
    @SerializedName("ups")
    val ups: Int?,
    @SerializedName("downs")
    val downs: Int?,
    @SerializedName("points")
    val points: Int?,
    @SerializedName("score")
    val score: Int?,
    @SerializedName("is_album")
    val isAlbum: Boolean?,
    @SerializedName("vote")
    val vote: Any?,
    @SerializedName("favorite")
    val favorite: Boolean?,
    @SerializedName("nsfw")
    val nsfw: Boolean?,
    @SerializedName("section")
    val section: String?,
    @SerializedName("comment_count")
    val commentCount: Int?,
    @SerializedName("favorite_count")
    val favoriteCount: Int?,
    @SerializedName("topic")
    val topic: String?,
    @SerializedName("topic_id")
    val topicId: Int?,
    @SerializedName("images_count")
    val imagesCount: Int?,
    @SerializedName("in_gallery")
    val inGallery: Boolean?,
    @SerializedName("is_ad")
    val isAd: Boolean?,
    @SerializedName("tags")
    val tags: List<Any?>?,
    @SerializedName("ad_type")
    val adType: Int?,
    @SerializedName("ad_url")
    val adUrl: String?,
    @SerializedName("in_most_viral")
    val inMostViral: Boolean?,
    @SerializedName("include_album_ads")
    val includeAlbumAds: Boolean?,
    @SerializedName("images")
    val images: ArrayList<Image>?,
    @SerializedName("ad_config")
    val adConfig: AdConfig?
) {
    @Parcelize
    data class Image(
        @SerializedName("id")
        val id: String?,
        @SerializedName("title")
        val title: @RawValue Any?,
        @SerializedName("description")
        val description: @RawValue Any?,
        @SerializedName("datetime")
        val datetime: Long?,
        @SerializedName("type")
        val type: String?,
        @SerializedName("animated")
        val animated: Boolean?,
        @SerializedName("width")
        val width: Int?,
        @SerializedName("height")
        val height: Int?,
        @SerializedName("size")
        val size: Long?,
        @SerializedName("views")
        val views: Int?,
        @SerializedName("bandwidth")
        val bandwidth: Long?,
        @SerializedName("vote")
        val vote: @RawValue Any?,
        @SerializedName("favorite")
        val favorite: Boolean?,
        @SerializedName("nsfw")
        val nsfw: @RawValue Any?,
        @SerializedName("section")
        val section: @RawValue Any?,
        @SerializedName("account_url")
        val accountUrl: @RawValue Any?,
        @SerializedName("account_id")
        val accountId: @RawValue Any?,
        @SerializedName("is_ad")
        val isAd: Boolean?,
        @SerializedName("in_most_viral")
        val inMostViral: Boolean?,
        @SerializedName("has_sound")
        val hasSound: Boolean?,
        @SerializedName("tags")
        val tags: @RawValue List<Any?>?,
        @SerializedName("ad_type")
        val adType: Int?,
        @SerializedName("ad_url")
        val adUrl: String?,
        @SerializedName("edited")
        val edited: String?,
        @SerializedName("in_gallery")
        val inGallery: Boolean?,
        @SerializedName("link")
        val link: String?,
        @SerializedName("comment_count")
        val commentCount: @RawValue Any?,
        @SerializedName("favorite_count")
        val favoriteCount: @RawValue Any?,
        @SerializedName("ups")
        val ups: @RawValue Any?,
        @SerializedName("downs")
        val downs: @RawValue Any?,
        @SerializedName("points")
        val points: @RawValue Any?,
        @SerializedName("score")
        val score: @RawValue Any?
    ) : Parcelable

    data class AdConfig(
        @SerializedName("safeFlags")
        val safeFlags: List<String?>?,
        @SerializedName("highRiskFlags")
        val highRiskFlags: List<Any?>?,
        @SerializedName("unsafeFlags")
        val unsafeFlags: List<Any?>?,
        @SerializedName("wallUnsafeFlags")
        val wallUnsafeFlags: List<Any?>?,
        @SerializedName("showsAds")
        val showsAds: Boolean?
    )
}