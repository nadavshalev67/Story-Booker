package com.nadavshalev67.storybooker.events

abstract class StoryEvents {
    open fun onOuterPageImpression(outerPageNumber: Int) {}
    open fun onInnerPageImpression(outerPageNumber: Int, innerPageNumber: Int) {}
    open fun onComplete() {}
}