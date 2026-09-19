package luisitobez.jjvh.basket.ui.core.navigation

import android.app.Activity
import android.content.Context
import androidx.lifecycle.ViewModel
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import luisitobez.jjvh.basket.BuildConfig
import javax.inject.Inject

class InterstitialViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(InterstitialAdState())
    val uiState: StateFlow<InterstitialAdState> = _uiState.asStateFlow()

    private val _interstitialAd =
        MutableStateFlow<InterstitialAd?>(null)

    val interstitialAd = _interstitialAd.asStateFlow()

    private var isLoading = false

    private var loadJob: Job? = null

    fun getInterstitialAd(context: Context) {

        // Ya tenemos un anuncio cargado
        if (_interstitialAd.value != null) {
            return
        }

        // Ya estamos cargando uno
        if (isLoading) {
            return
        }

        isLoading = true

        InterstitialAd.load(
            context.applicationContext,
            BuildConfig.INTERSECTIAL_AD_ID,

            AdRequest.Builder().build(),

            object : InterstitialAdLoadCallback() {

                override fun onAdLoaded(ad: InterstitialAd) {
                    isLoading = false
                    _interstitialAd.value = ad
                }

                override fun onAdFailedToLoad(error: LoadAdError) {
                    isLoading = false
                    _interstitialAd.value = null
                }
            }
        )
    }

    fun isLoaded(): Boolean {
        return _interstitialAd.value != null
    }

    fun showAd(context: Context) {

        val activity = context as? Activity ?: return

        val ad = _interstitialAd.value ?: return

        // Lo eliminamos antes de mostrarlo
        _interstitialAd.value = null

        ad.fullScreenContentCallback =
            object : FullScreenContentCallback() {

                override fun onAdDismissedFullScreenContent() {
                    getInterstitialAd(activity)
                }

                override fun onAdFailedToShowFullScreenContent(
                    adError: AdError
                ) {
                    getInterstitialAd(activity)
                }
            }

        ad.show(activity)
    }

    fun showAdWhenReady(context: Context) {
        loadJob?.cancel()

        loadJob = CoroutineScope(Dispatchers.Main.immediate).launch {
            if (!isLoaded()) {
                getInterstitialAd(context)
            }

            repeat(50) {

                if (isLoaded()) {
                    showAd(context)
                    return@launch
                }

                delay(100)
            }
        }
    }

    override fun onCleared() {
        loadJob?.cancel()
        super.onCleared()
    }

    fun onAction(context: Context) {
        _uiState.value = _uiState.value.copy(actions = _uiState.value.actions + 1)
        if (_uiState.value.actions >= 7) {
            _uiState.value = _uiState.value.copy(actions = 0)
            showAdWhenReady(context = context)
        }
    }
}

data class InterstitialAdState(
    val actions: Int = 0
)