# TmdbAppKoin

Single activity Android app that shows informations about movies and TV shows using [TMDB (The Movie Database)](https://developers.themoviedb.org/3/getting-started/introduction) REST APIs. 

## Features
- **Home page** with *Top rated* movies
- **Movies page** with *Now playing*, *Popular* and *Upcoming* movies
- **TV Shows page** with *TV On the Air*, *Popular* and *Top rated* TV shows
- menu with **Favorites** for movies and TV shows
- detail pages for movies, TV shows (seasons and episodes list for every season), actors

## Android & 3rd party features used
- Kotlin programming language
- material components & themes
- light & dark themes
- single activity with multiple fragments  
- MVVM with LiveData and ViewModel
- navigation components with safe args
- horizontal and vertical RecyclerView (single and multiple types of ViewHolder) with ListAdapter & DiffUtil
- ViewPager v2 with TabLayout for fragments 
- endless scrolling with PagingDataAdapter and PagingSource, using Paging v3
- swipe to refresh  
- coroutines and Flow
- bottom navigation and toolbar menu
- data binding, view binding, binding adapters for loading images
- REST API calls with [Retrofit](https://square.github.io/retrofit/)
- image loading with [Glide](https://github.com/bumptech/glide)
- favorites stored using [Room](https://developer.android.com/training/data-storage/room)
- current item in every page stored using [DataStore](https://developer.android.com/topic/libraries/architecture/datastore)
- dependency injection with [Koin](https://insert-koin.io/docs/setup/v3.2/)
- retry mechanism in case of *5xx* server errors
- custom class for handling of network response (*RetrofitResponseHandler*)