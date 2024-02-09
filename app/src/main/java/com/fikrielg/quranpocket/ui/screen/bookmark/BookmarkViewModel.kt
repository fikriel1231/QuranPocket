package com.fikrielg.quranpocket.ui.screen.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fikrielg.quranpocket.data.repository.QuranRepository
import com.fikrielg.quranpocket.data.source.local.entities.Bookmark
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(private val repository: QuranRepository): ViewModel() {

     val bookmarkState = repository.getBookmarkList().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(500),
        emptyList()
    )

    fun onEvent(event: BookScreenEvent){
        when(event){
            is BookScreenEvent.DeleteAllBookmark -> {
                viewModelScope.launch {
                    repository.deleteAllBookmark()
                }
            }
            is BookScreenEvent.DeleteBookmark -> {
                viewModelScope.launch {
                    repository.deleteBookmark(event.bookmark)
                }
            }
        }
    }
}

sealed class BookScreenEvent{
    data object DeleteAllBookmark: BookScreenEvent()
    data class DeleteBookmark(val bookmark: Bookmark): BookScreenEvent()
}