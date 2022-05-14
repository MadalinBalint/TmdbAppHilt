package com.mendelin.tmdb_hilt.domain.models.response

import androidx.annotation.Keep
import com.mendelin.tmdb_hilt.domain.models.rest_api.*
import java.text.SimpleDateFormat
import java.util.*

@Keep
data class TvShowDetailsResponse(
    val backdrop_path: String?,
    val created_by: List<CreatedByItem>,
    val episode_run_time: List<Int>,
    val first_air_date: String,
    val genres: List<GenreItem>,
    val homepage: String?,
    val id: Int,
    val in_production: Boolean,
    val languages: List<String>,
    val last_air_date: String,
    val last_episode_to_air: EpisodeToAirItem,
    val name: String,
    val next_episode_to_air: Any?,
    val networks: List<ProductionCompanyItem>,
    val number_of_episodes: Int,
    val number_of_seasons: Int,
    val origin_country: List<String>,
    val original_language: String,
    val original_name: String,
    val overview: String,
    val popularity: Float,
    val poster_path: String?,
    val production_companies: List<ProductionCompanyItem>,
    val production_countries: List<ProductionCountryItem>,
    val seasons: List<SeasonItem>,
    val spoken_languages: List<SpokenLanguageItem>,
    val status: String,
    val tagline: String,
    val type: String,
    val vote_average: Float,
    val vote_count: Int,

    val status_message: String = "",
    val success: Boolean,
    val status_code: Int = 200
) {
    fun getYear(date: String): String {
        val sd = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val parsedDate = sd.parse(date)
        val calendar = Calendar.getInstance()
        calendar.time = parsedDate!!
        return calendar.get(Calendar.YEAR).toString()
    }

    fun getAiringDates(): String {
        return "${getYear(first_air_date)} - ${getYear(last_air_date)}"
    }

    fun getGenreList(): String = genres.joinToString(", ") { it.name }

    fun getCreatedByList(): String = created_by.joinToString(", ") { it.name }

    fun getNetworksList(): String =
        networks.joinToString(", ") { "${it.name} (${it.origin_country})" }

    fun getProductionCompaniesList(): String = production_companies.joinToString(", ") {
        if (it.origin_country.isNotEmpty())
            "${it.name} (${it.origin_country})"
        else
            it.name
    }

    fun getOriginalLanguage(): String =
        Locale(original_language).getDisplayLanguage(Locale.getDefault()).toString()
}
