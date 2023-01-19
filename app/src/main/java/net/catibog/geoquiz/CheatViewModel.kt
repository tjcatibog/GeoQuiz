package net.catibog.geoquiz;

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

const val SHOWN_RESULT = "SHOWN_RESULT"

class CheatViewModel(private val savedStateHandle: SavedStateHandle): ViewModel() {
    var shownResult: Boolean
        get() = savedStateHandle[SHOWN_RESULT] ?: false
        set(value) = savedStateHandle.set(SHOWN_RESULT, value)
}
