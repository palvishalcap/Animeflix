package com.example.animeflix.ui.composables

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun AnimeVideoPlayer(
    videoUrl: String,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    val youtubeId = remember(videoUrl) {
        videoUrl.substringAfter("embed/").substringBefore("?")
    }

    AndroidView(
        factory = { ctx ->
            android.webkit.WebView(ctx).apply {
                setLayerType(android.view.View.LAYER_TYPE_HARDWARE, null)

                settings.apply {
                    javaScriptEnabled = true
                    domStorageEnabled = true
                    mediaPlaybackRequiresUserGesture = false
                    loadWithOverviewMode = true
                    useWideViewPort = true
                    allowFileAccess = true
                    allowContentAccess = true
                    cacheMode = android.webkit.WebSettings.LOAD_DEFAULT
                    mixedContentMode = android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW

                    userAgentString =
                        "Mozilla/5.0 (Linux; Android 10) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.120 Mobile Safari/537.36"
                }

                webChromeClient = android.webkit.WebChromeClient()

                webViewClient = android.webkit.WebViewClient()

                val htmlContent = """
                    <!DOCTYPE html>
                    <html>
                    <head>
                        <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
                        <style>
                            * { margin: 0; padding: 0; }
                            html, body { 
                                width: 100%; 
                                height: 100%; 
                                background: #000; 
                                overflow: hidden;
                            }
                            #player {
                                position: absolute;
                                top: 0;
                                left: 0;
                                width: 100%;
                                height: 100%;
                            }
                        </style>
                    </head>
                    <body>
                        <div id="player"></div>
                        <script>
                            var tag = document.createElement('script');
                            tag.src = "https://www.youtube.com/iframe_api";
                            var firstScriptTag = document.getElementsByTagName('script')[0];
                            firstScriptTag.parentNode.insertBefore(tag, firstScriptTag);
                            
                            var player;
                            function onYouTubeIframeAPIReady() {
                                player = new YT.Player('player', {
                                    height: '100%',
                                    width: '100%',
                                    videoId: '$youtubeId',
                                    playerVars: {
                                        'playsinline': 1,
                                        'autoplay': 0,
                                        'controls': 1,
                                        'rel': 0,
                                        'modestbranding': 1,
                                        'fs': 1,
                                        'enablejsapi': 1,
                                        'origin': 'https://www.youtube.com'
                                    },
                                    events: {
                                        'onReady': onPlayerReady,
                                        'onError': onPlayerError
                                    }
                                });
                            }
                            
                            function onPlayerReady(event) {
                                console.log('Player ready');
                            }
                            
                            function onPlayerError(event) {
                                console.log('Player error: ' + event.data);
                            }
                        </script>
                    </body>
                    </html>
                """.trimIndent()

                loadDataWithBaseURL(
                    "https://www.youtube-nocookie.com",
                    htmlContent,
                    "text/html",
                    "UTF-8",
                    null
                )

                layoutParams = android.widget.FrameLayout.LayoutParams(
                    android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                    android.view.ViewGroup.LayoutParams.MATCH_PARENT
                )
            }
        },
        modifier = modifier
    )
}