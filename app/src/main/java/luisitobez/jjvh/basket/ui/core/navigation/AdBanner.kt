    package luisitobez.jjvh.basket.ui.core.navigation

    import androidx.compose.foundation.layout.fillMaxWidth
    import androidx.compose.runtime.Composable
    import androidx.compose.ui.Modifier
    import androidx.compose.ui.viewinterop.AndroidView
    import com.google.android.gms.ads.AdRequest
    import com.google.android.gms.ads.AdSize
    import com.google.android.gms.ads.AdView
    import luisitobez.jjvh.basket.BuildConfig


    @Composable
    fun AdBanner() {
        AndroidView(
            factory = { context ->
                AdView(context).apply {
                    setAdSize(AdSize.BANNER)
                    adUnitId = BuildConfig.BANNER_AD_ID
                    loadAd(
                         AdRequest.Builder().build()
                    )
                }
            },
            modifier = Modifier.fillMaxWidth(),
            onRelease = { adView ->
                adView.destroy()
            },
            /*update = { adView ->
                adView.loadAd(
                    AdRequest.Builder().build()
                )
            }*/
        )
    }