# LiveReaction
To have Floating Drawable as LiveReaction on Screen, can be broadcasted to other users by using Scoket other realtime PubSub.

![Sammple](https://user-images.githubusercontent.com/34341190/209425115-3b15aff1-2463-4177-93e6-bcce5fb54b1c.gif)

## Installation

Add the following maven repository in root build.gradle:

```bash
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

Add the following dependency in app build.gradle:

```bash
dependencies {
	implementation 'com.github.MohdShamweel:LiveReaction:1.0'
  }
```

## Usage
1. Add `LiveReactionView` in `.xml` file

````xml
 <com.shamweel.livereaction.LiveReactionView
        android:id="@+id/liveReactionView"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content" />
````

2. To perform the reaction call the below code on button click or on realtime-callback:
````kotlin
liveReactionView.performLiveReaction(R.drawable.ic_heart_filled_red)
````

### Others
To limit the On-Screen floating reaction count use: `Default is 30`
````kotlin
liveReactionView.setOnScreenMaxReactionCount(25)
````

To clear all the floating reactions use:
````koltin
liveReactionView.clearReactionView()
````
Other performReaction params
````kotlin
/**
     * Call to perform Floating Reaction
     * @param drawableRes : Drawable Res file to be used to perform Reaction
     * @param isSelf : when true the the onScreenMaxCount is neglected
     * @param duration : The duration to on-screen animation
     */
    reactionView.performLiveReaction(
                drawableRes = R.id.ic_heart_filled_red,
                isSelf = true,
                duration = 2000L
            )
````

## Example:
To know more about implementation please checkout the [Sample App](https://github.com/MohdShamweel/LiveReaction/tree/main/app)

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.




