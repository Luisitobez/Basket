package luisitobez.jjvh.basket.ui.Screen.Game

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import luisitobez.jjvh.basket.domain.repository.GameRepository
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val repository: GameRepository
) : ViewModel()