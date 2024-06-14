package com.nadavshalev67.storybooker.events

abstract class StoryEvents<T> {
    open fun onOuterPageImpression(outerPageNumber: Int) {}
    open fun onInnerPageImpression(outerPageNumber: Int, innerPageNumber: Int, item: T) {}
    open fun onComplete() {}
}