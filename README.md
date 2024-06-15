<div align="center">
  <img src="./images/story-booker-banner.png"/>
  <h1>Story Booker</h1>
  <img alt="Jitpack" src="https://jitpack.io/v/nadavshalev67/Story-Booker.svg" alt="https://jitpack.io/#nadavshalev67/Story-Booker">
</div>

> Bring Stories To Life: A Jetpack Compose Library for Instagram-like Stories

### Demo

https://github.com/nadavshalev67/Story-Booker/assets/36004285/b9a3ef0c-ea4e-4f6c-8ba7-2dcdd8b478e6

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










