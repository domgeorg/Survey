package com.example.survey.common.custom.layout_manager

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView

class HorizontalLayoutManager(context: Context) :
    LinearLayoutManager(context, RecyclerView.HORIZONTAL, false) {

    override fun canScrollHorizontally(): Boolean {
        return false
    }

    override fun smoothScrollToPosition(
        recyclerView: RecyclerView,
        state: RecyclerView.State?,
        position: Int
    ) {
        val linearSmoothScroller = ForceHorizontalLinearSmoothScroller(recyclerView.context)
        linearSmoothScroller.targetPosition = position
        startSmoothScroll(linearSmoothScroller)
    }
}


class ForceHorizontalLinearSmoothScroller(context: Context) : LinearSmoothScroller(context) {
    override fun calculateDxToMakeVisible(view: android.view.View, snapPreference: Int): Int {
        val layoutManager = layoutManager ?: return 0
        val params = view.layoutParams as RecyclerView.LayoutParams
        val left = layoutManager.getDecoratedLeft(view) - params.leftMargin
        val right = layoutManager.getDecoratedRight(view) + params.rightMargin
        val start = layoutManager.paddingLeft
        val end = layoutManager.width - layoutManager.paddingRight
        return calculateDtToFit(left, right, start, end, snapPreference)
    }
}