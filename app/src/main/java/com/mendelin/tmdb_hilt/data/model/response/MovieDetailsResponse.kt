package com.mendelin.tmdb_hilt.data.model.response

import androidx.annotation.Keep
import com.mendelin.tmdb_hilt.data.model.rest_api.GenreItem
import com.mendelin.tmdb_hilt.data.model.rest_api.ProductionCompanyItem
import com.mendelin.tmdb_hilt.data.model.rest_api.ProductionCountryItem
import com.mendelin.tmdb_hilt.data.model.rest_api.SpokenLanguageItem
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

@Keep
data class MovieDetailsResponse(
    val adult: Boolean,
    val backdrop_path: String?,
    val belongs_to_collection: Any?,
    val budget: Int,
    val genres: List<GenreItem>,
    val homepage: String?,
    val id: Int,
    val imdb_id: String?,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Float,
    val poster_path: String?,
    val production_companies: List<ProductionCompanyItem>,
    val production_countries: List<ProductionCountryItem>,
    val release_date: String,
    val revenue: Int,
    val runtime: Int,
    val spoken_languages: List<SpokenLanguageItem>,
    val status: String,
    val tagline: String?,
    val title: String,
    val video: Boolean,
    val vote_average: Float,
    val vote_count: Int,

    val status_message: String = "",
    val success: Boolean = true,
    val status_code: Int = 200
) {
    fun getYear(): String {
        if (release_date.isNotEmpty()) {
            val sd = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val date = sd.parse(release_date)
            val calendar = Calendar.getInstance()
            calendar.time = date!!
            return calendar.get(Calendar.YEAR).toString()
        }

        return ""
    }

    fun getMovieRuntime(): String {
        val hours = runtime / 60
        val minutes = runtime % 60

        return if (hours > 0) "${hours}h ${minutes}m" else "${minutes}m"
    }

    fun getGenreList(): String = genres.joinToString(", ") { it.name }

    fun getOriginalLanguage(): String =
        Locale(original_language).getDisplayLanguage(Locale.getDefault()).toString()

    fun getBudgetDetails(): String = NumberFormat.getCurrencyInstance(Locale.US).format(budget)
}
