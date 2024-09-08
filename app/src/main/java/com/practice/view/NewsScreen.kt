package com.practice.view

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Build
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.practice.newsapp.R
import com.practice.viewmodel.NewsViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.setValue

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NewsScreen(navController: NavHostController, viewModel: NewsViewModel) {


    Scaffold(


        topBar = { NewsBar(navController) },
        content = { NewsBody(viewModel = viewModel,it)}


    )

}
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsBar(navController: NavHostController){

    val robotoFamily = FontFamily(

        Font(R.font.roboto_bold, FontWeight.Bold),
        Font(R.font.roboto_italic, FontWeight.Normal, FontStyle.Italic),
        Font(R.font.roboto_medium, FontWeight.Medium),
        Font(R.font.roboto_regular, FontWeight.Normal)

    )

    CenterAlignedTopAppBar(

        title = {
            Text(
                text = "News App", fontFamily = robotoFamily, fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.title_color)
            )
        },
        navigationIcon = {
            IconButton(onClick = {

                navController.popBackStack()
            }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "App Icon",
                    modifier = Modifier.size(40.dp)
                )
            }
        },
        actions = {
            IconButton(onClick = { /* do something */ }) {
                Icon(

                    imageVector = Icons.Default.Notifications,
                    contentDescription = "Notification Icon",
                    modifier = Modifier.size(38.dp)

                )
            }
        }

    )

}
@Composable
fun NewsBody(viewModel: NewsViewModel,paddingValues: PaddingValues){

    val robotoFamily = FontFamily(

        Font(R.font.roboto_bold, FontWeight.Bold),
        Font(R.font.roboto_italic, FontWeight.Normal, FontStyle.Italic),
        Font(R.font.roboto_medium, FontWeight.Medium),
        Font(R.font.roboto_regular, FontWeight.Normal)

    )
    val selectedNews = viewModel.selectedNews.collectAsState().value

    Box(modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)) {


       // var context = LocalContext.current
        var isLoading by remember {
            mutableStateOf(true)
        }
        val loadingUrl by remember {
            mutableStateOf(selectedNews?.newsUrl)
        }
        AndroidView(factory = {ctx ->

        WebView(ctx).apply {

            settings.javaScriptEnabled = true
            settings.loadWithOverviewMode = true

            webViewClient = object : WebViewClient() {

                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    super.onPageStarted(view, url, favicon)
                    isLoading = true
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    isLoading = false
                }

                override fun onReceivedError(
                    view: WebView?,
                    request: WebResourceRequest?,
                    error: WebResourceError?
                ) {
                    super.onReceivedError(view, request, error)
                    isLoading = false
                }

            }
        }

        },
            update = {
                loadingUrl?.let { it1 -> it.loadUrl(it1) }


            })
        if (isLoading){

            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))

        }
    }
}
