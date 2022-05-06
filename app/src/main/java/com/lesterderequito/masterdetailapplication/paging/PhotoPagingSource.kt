package com.lesterderequito.masterdetailapplication.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.lesterderequito.masterdetailapplication.network.PhotoInfo
import com.lesterderequito.masterdetailapplication.network.RetroService

const val QUERY_PER_PAGE = 20
const val TOTAL_IMAGES = 1000

class PhotoPagingSource(private val retroService: RetroService): PagingSource<Int, PhotoInfo>() {

    private val totalPages = TOTAL_IMAGES / QUERY_PER_PAGE

    override fun getRefreshKey(state: PagingState<Int, PhotoInfo>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PhotoInfo> {
        val pageNumber = params.key ?: 1
        return try {
            val response = retroService.getPhotos(pageNumber)
            val pagedResponse = response.body()

            var nextPageNumber: Int? = null
            if (pageNumber < totalPages) {
                nextPageNumber = pageNumber + 1
            }

            LoadResult.Page(
                data = pagedResponse.orEmpty(),
                prevKey = null,
                nextKey = nextPageNumber
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}