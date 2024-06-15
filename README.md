<div align="center">
  <img src="./images/story-booker-banner.png"/>
  <h1>Story Booker</h1>
  <img alt="Jitpack" src="https://jitpack.io/v/nadavshalev67/Story-Booker.svg" alt="https://jitpack.io/#nadavshalev67/Story-Booker">
</div>

> Bring Stories To Life: A Jetpack Compose Library for Instagram-like Stories

### Demo

https://github.com/nadavshalev67/Story-Booker/assets/36004285/8efc32a2-3d56-45a6-9935-aeb449ab5551

### Features

- Craft Instagram-style stories with full design flexibility:
    - Customize Story Thumbnails Gallery with fine-tuned specifics:
       - Adjustable story size and border size
       - Personalized text styling for distinctive narration
       - Modifiable circle size and border size to suit varied presentation needs
       - Multiple image sourcing capabilities: Thumbnails can load images through URL, Drawable resource or solid color for flexible design
     - Customize Story Content Gallery:
       - Fully customizable, you can render your own Composable on it
       - Time-adjustable top bars: Offers duration customization for versatile storytelling timings.
       - Smooth transitions: Offers cubic transition animations between pages for a seamless viewing experience.
       - Provides event mechanisms for tracking impressions, interactions, and more, helping you understand your user behavior better.
  Achieve all this and more in just a few lines of code, saving time and enhancing productivity.

### Installation

build.gradle(.kts)

```kotlin
implementation ("com.github.nadavshalev67:Story-Booker:<Version>")
```

libs.versions.toml

```toml
story-booker = { module = "com.github.nadavshalev67:Story-Booker", version = "<VERSION>" }
```

### Usage
<img width="334" alt="Thumbnails Example" src="https://github.com/nadavshalev67/Story-Booker/assets/36004285/82eada3d-e2b6-477f-a38a-9892eccf86a8">

## Thumbnails Gallery
```kotlin
val list: List<ThumbnailData> =
    listOf(
        ThumbnailData(
            imageSource =
                ImageSource.Url(
                    url =
                        "https://www.giantbomb.com/a/uploads/scale_medium/16/164924/3083931-8746743194-flat%2C.jpg",
                ),
            text = "Fire",
            isViewedAll = true,
        ),
        ThumbnailData(
            imageSource =
                ImageSource.DrawableRes(
                    resId = R.drawable.first,
                ),
            text = "First",
            isViewedAll = false,
        ),
    )

ThumbnailGallery(
    modifier = Modifier.padding(top = 10.dp),
    thumbnailItems = list,
    thumbnailStyle = ThumbnailStyle(),
    onThumbnailClick = { index, data ->
        // Implement your own logic
    },
)

```

# Customizing Thumbnail Gallery 
```kotlin
data class ThumbnailStyle(
    val readThumbnailsColor: List<Color>,
    val unreadThumbnailsColor: List<Color>,
    val thumbnailCircleSize: Dp,
    val borderSize: Dp,
    val displayShimmer: Boolean,
    val textSize: TextUnit,
    val fontWeight: FontWeight,
) 
```
- readThumbnailsColor & unreadThumbnailsColor: Set color gradients for read and unread thumbnails.
- thumbnailCircleSize & borderSize: Adjust the circle size and its border size.
- displayShimmer: Opt in or out of displaying the shimmer effect on loading.
- textSize & fontWeight: Customize the text appearance within the thumbnail.

##  Story Gallery

![Story Gallery demo](https://github.com/nadavshalev67/Story-Booker/assets/36004285/fee0342b-3a66-476f-b4f8-dc39afaa02c1)

```kotlin
val contentGallery: Map<Int, List<T>> =
    mapOf(
        0 to listOf(PokemonEntity(), PokemonEntity()),
        1 to listOf(PokemonEntity(),PokemonEntity()),
    )

StoryGallery(
    modifier = Modifier.padding(it).background(Color.Black),
    storyGalleryData = StoryGalleryData(
            pages = contentGallery,
            initialPage = 0, 
            progressBarDurationMillis = 2000, 
        ),
    storyEvents = storyEventsListener
) { page, innerPageSingleData ->
   // Your composable implementation for drawing inside the gallery for each item
}

```

> **_NOTE:_**  For the Story Gallery Item to function correctly, it should implement the LoadingData interface. This allows the library to display a loading progress bar when the data is not yet loaded. If you prefer to implement your own progress indicator, you may set isLoading to true by default in the LoadingData interface.

# Gallery Events
```kotlin
val storyEventsListener =
    object : StoryEvents<T>() {
        override fun onOuterPageImpression(outerPageNumber: Int) {
        // Triggers when an out-page is viewed
        }
        
        override fun onInnerPageImpression(
            outerPageNumber: Int,
            innerPageNumber: Int,
            item: T,
        ) {
        // Triggers when an inner-page of an out-page is viewed
        }

        // Triggers when the story reaches completion
        override fun onComplete() {}
    }
```

# License

```
Copyright 2024 nadavshalev67

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```









