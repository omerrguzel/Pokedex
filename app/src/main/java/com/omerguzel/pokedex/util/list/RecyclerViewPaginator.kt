package tr.com.allianz.digitall.util.list

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE

abstract class RecyclerViewPaginator(recyclerView: RecyclerView) : RecyclerView.OnScrollListener() {

    private val batchSize = 1

    private var currentPage: Int = 0

    private val threshold = 2

    private val layoutManager: RecyclerView.LayoutManager?

    private val startSize: Int
        get() = ++currentPage

    private val maxSize: Int
        get() = currentPage + batchSize


    abstract fun loadMore(start: Int, count: Int)

    init {
        recyclerView.addOnScrollListener(this)
        this.layoutManager = recyclerView.layoutManager
    }

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        if (newState == SCROLL_STATE_IDLE) {
            val visibleItemCount = layoutManager!!.childCount
            val totalItemCount = layoutManager.itemCount

            var firstVisibleItemPosition = 0
            if (layoutManager is GridLayoutManager) {
                firstVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
            } else if (layoutManager is LinearLayoutManager) {
                firstVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
            }

            if (visibleItemCount + firstVisibleItemPosition + threshold >= totalItemCount) {
                loadMore(startSize, maxSize)
            }
        }
    }

    fun reset() {
        currentPage = 0
    }
}
