package com.practice.view

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.practice.newsapp.R
import com.practice.responses.NewsResponse
import com.practice.viewmodel.NewsViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(viewModel: NewsViewModel, navController: NavController) {


    Scaffold(
        
        
        topBar = {TopBar()},
        content = { Body(paddingValues = it,viewModel,navController)}
        
    )
    
    
}
fun String.capitalizeWords(): String = split(" ").joinToString(" ") {
    it.capitalize()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(){

    val robotoFamily = FontFamily(

        Font(R.font.roboto_bold,FontWeight.Bold),
        Font(R.font.roboto_italic,FontWeight.Normal, FontStyle.Italic),
        Font(R.font.roboto_medium,FontWeight.Medium),
        Font(R.font.roboto_regular,FontWeight.Normal)

    )
    
    CenterAlignedTopAppBar(

        title = {
            Text(
                text = "News App", fontFamily = robotoFamily, fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.title_color)
            )
        },
        navigationIcon = {
            IconButton(onClick = { /* do something */ }) {
                Icon(
                   painter =  painterResource(id = R.drawable.app ),
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

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Body(paddingValues: PaddingValues, viewModel: NewsViewModel, navController: NavController){


    VerticalSlider(paddingValues = paddingValues,viewModel,navController)

}

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun VerticalSlider(paddingValues: PaddingValues, viewModel: NewsViewModel, navController: NavController) {


    LaunchedEffect(Unit) {
        viewModel.getAllNews("bbc-news", 20, "edafb4be1467418283458617c9426fdc")
    }

    val list = viewModel.allNews.collectAsState().value
    var newsList by remember { mutableStateOf(list) }




    val robotoFamily = FontFamily(

        Font(R.font.roboto_bold, FontWeight.Bold),
        Font(R.font.roboto_italic, FontWeight.Normal, FontStyle.Italic),
        Font(R.font.roboto_medium, FontWeight.Medium),
        Font(R.font.roboto_regular, FontWeight.Normal)

    )
    var searchText = remember { mutableStateOf("") }



    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
    ) {

        OutlinedTextField(
            value = searchText.value,
            onValueChange = {
                searchText.value = it },
            placeholder = {
                Text(
                    "Search News...",
                    color = colorResource(id = R.color.label_color),
                    fontSize = 16.sp
                )
            }, singleLine = true,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search Icon",
                    modifier = Modifier.size(35.dp),

                    tint = colorResource(id = R.color.label_color)
                )
            },
            trailingIcon = {

                IconButton(
                    onClick = {

                        if (searchText.value.isNotEmpty()) {
                            newsList = list.filter { newsItem ->
                                val authorMatches = newsItem.author?.contains(searchText.value, ignoreCase = true) ?: false
                                val titleMatches = newsItem.title?.contains(searchText.value, ignoreCase = true) ?: false

                                // Return true if either the author or title matches
                                authorMatches || titleMatches
                            }
                        } else {

                            newsList = list
                        }

                    },
                    modifier = Modifier
                        .size(40.dp)
                        .background(
                            color = colorResource(id = R.color.serach_btn),
                            shape = CircleShape
                        )
                        .clip(CircleShape)
                        .padding(10.dp)
                ) {
                    Icon(

                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = null,
                        tint = Color.White

                    )
                }

            },
            modifier = Modifier

                .padding(20.dp)
                .fillMaxWidth()
                .height(56.dp),
            colors = OutlinedTextFieldDefaults.colors(

                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                focusedBorderColor = colorResource(id = R.color.label_color),
                unfocusedBorderColor = colorResource(id = R.color.label_color),

                ),
            shape = RoundedCornerShape(100),
            textStyle = TextStyle(color = Color.Black, fontSize = 16.sp),

            )

        Text(

            text = "Latest News",
            modifier = Modifier.padding(horizontal = 10.dp),
            fontFamily = robotoFamily,
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.latest),
            fontSize = 16.sp

        )


        if (searchText.value.isEmpty()){

            newsList = list
        }
                Spacer(modifier = Modifier.height(15.dp))

                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),
                ) {


                    items(
                        count = newsList.size
                    ) { index ->

                                Card(

                                modifier = Modifier
                                    .padding(horizontal = 16.dp)
                                    .size(247.dp, 200.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = Color.Transparent

                                ),onClick = {


                                        viewModel.updateSelectedNews(newsList[index])
                                        navController.navigate("NewsScreen")

                                    },
                                border = BorderStroke(1.dp, colorResource(id = R.color.label_color))
                            )
                            {

                                Column(
                                    modifier = Modifier.wrapContentSize(),
                                ) {

                                    Image(

                                        painter = rememberAsyncImagePainter(newsList[index].imageUrl),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(130.dp)
                                            .aspectRatio(0.5f)

                                    )

                                    Spacer(modifier = Modifier.height(10.dp))
                                    newsList[index].title?.let {
                                        Text(

                                            text = it,
                                            fontFamily = FontFamily.Serif,
                                            fontWeight = FontWeight.Medium,
                                            lineHeight = 12.sp,
                                            color = Color.Black,
                                            fontSize = 10.sp,
                                            modifier = Modifier.padding(5.dp),
                                            softWrap = true,
                                            maxLines = 2,
                                            overflow = TextOverflow.Ellipsis


                                        )
                                    }

                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(7.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {

                                        Row(verticalAlignment = Alignment.CenterVertically) {

                                            Icon(

                                                imageVector = Icons.Default.Edit,
                                                contentDescription = null,
                                                tint = colorResource(id = R.color.label_color),
                                                modifier = Modifier.size(8.dp)
                                            )
                                            Spacer(modifier = Modifier.height(2.dp))
                                            newsList[index].author?.let {
                                                Text(
                                                    text = it,
                                                    fontSize = 9.sp,
                                                    fontFamily = robotoFamily,
                                                    fontWeight = FontWeight.Bold,
                                                    color = colorResource(id = R.color.label_color)
                                                )
                                            }
                                        }

                                        val formatter = DateTimeFormatter.ISO_DATE_TIME
                                        val dateTime = LocalDateTime.parse(newsList[index].date, formatter)

                                        val extractedDate = dateTime.toLocalDate()
                                        val extractedTime = dateTime.toLocalTime()

                                        val now = LocalDateTime.now()
                                        val currentDate = now.toLocalDate()
                                        val currentTime = now.toLocalTime()

                                        val daysDifference = ChronoUnit.DAYS.between(extractedDate, currentDate)

                                        val timeDifference = ChronoUnit.HOURS.between(extractedTime, currentTime)

                                       val time = when {
                                            daysDifference > 0 -> {
                                                if (daysDifference.toInt() == 1){

                                                    "$daysDifference day ago"
                                                }else{
                                                    "$daysDifference days ago"
                                                }
                                            }
                                             timeDifference <= 0 -> {
                                                "Less than an hour ago"
                                            }
                                            else -> {
                                                "$timeDifference hours ago"
                                            }
                                        }

                                        Text(
                                            text = time.toString() ,
                                            fontSize = 9.sp,
                                            fontFamily = robotoFamily,
                                            fontWeight = FontWeight.Bold,
                                            color = colorResource(id = R.color.serach_btn)
                                        )

                                    }

                                }
                            }
                        }
                        }
               HorizontalSlider(viewModel,navController)
            }

}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("SuspiciousIndentation")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HorizontalSlider(

    viewModel: NewsViewModel,
    navController: NavController
){

    val robotoFamily = FontFamily(

        Font(R.font.roboto_bold,FontWeight.Bold),
        Font(R.font.roboto_italic,FontWeight.Normal, FontStyle.Italic),
        Font(R.font.roboto_medium,FontWeight.Medium),
        Font(R.font.roboto_regular,FontWeight.Normal)

    )
    var selectedCategory by remember {
        mutableStateOf("business")
    }


    val categories = listOf("business", "science", "health", "entertainment")

    LaunchedEffect(selectedCategory) {

        viewModel.getCategoryAllNews("us", selectedCategory, 20, "edafb4be1467418283458617c9426fdc")
    }

    Spacer(modifier = Modifier.height(15.dp))


    LazyRow(modifier = Modifier.fillMaxWidth()){

        items(categories){ category->


            Row(

                modifier = Modifier.fillMaxWidth().padding(3.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly

            ) {

                    Button(
                        onClick = {

                            selectedCategory = category
                            viewModel.getCategoryAllNews("us", selectedCategory, 20, "edafb4be1467418283458617c9426fdc")
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (selectedCategory == category) colorResource(id = R.color.serach_btn) else Color.Transparent,
                            contentColor = if (selectedCategory == category) Color.White else colorResource(id = R.color.label_color)
                        ),
                        border = BorderStroke(1.dp, color = colorResource(id = R.color.label_color)), modifier = Modifier.padding(2.dp)
                    ) {
                        Text(

                            text = category.capitalizeWords(),
                            fontFamily = robotoFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp

                        )
                    }

            }




        }

    }

//    Row(
//
//        modifier = Modifier.fillMaxWidth(),
//        verticalAlignment = Alignment.CenterVertically,
//        horizontalArrangement = Arrangement.SpaceEvenly
//
//    ) {
//
//        categories.forEach { category ->
//            Button(
//                onClick = {
//
//                    selectedCategory = category
//                   viewModel.getCategoryAllNews("us", selectedCategory, 20, "edafb4be1467418283458617c9426fdc")
//                },
//                colors = ButtonDefaults.buttonColors(
//                    containerColor = if (selectedCategory == category) colorResource(id = R.color.serach_btn) else Color.Transparent,
//                    contentColor = if (selectedCategory == category) Color.White else colorResource(id = R.color.label_color)
//                ),
//                border = BorderStroke(1.dp, color = colorResource(id = R.color.label_color))
//            ) {
//                Text(
//
//                    text = category.capitalizeWords(),
//                    fontFamily = robotoFamily,
//                    fontWeight = FontWeight.Bold,
//                    fontSize = 12.sp
//
//                )
//            }
//        }
//    }

    val newsList = viewModel.allCategoryNews.collectAsState().value


    LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
        ) {

            items(count = newsList.size) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .padding(10.dp),
                    colors = CardDefaults.cardColors(
                        contentColor = Color.Transparent,
                        containerColor = Color.Transparent
                    ), onClick = {


                        viewModel.updateSelectedNews(newsList[it])
                        navController.navigate("NewsScreen")

                    }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {


                        Image(

                            painter = painterResource(id = R.drawable.noimage),
                            contentDescription = null,
                            modifier = Modifier
                                .aspectRatio(0.80F)
                                .clip(RoundedCornerShape(20.dp)),
                            contentScale = ContentScale.Crop

                        )

                        Column(

                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.Start,
                            modifier = Modifier.fillMaxHeight()

                        ) {

                            newsList[it].title?.let { it1 ->
                                Text(

                                    text = it1,
                                    lineHeight = 12.sp,
                                    fontFamily = FontFamily.Serif,
                                    fontWeight = FontWeight.Medium,
                                    color = Color.Black,
                                    fontSize = 10.sp,
                                    modifier = Modifier.padding(5.dp),
                                    softWrap = true,
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis

                                )
                            }
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(horizontal = 5.dp)
                            ) {

                                Icon(

                                    imageVector = Icons.Default.Edit,
                                    contentDescription = null,
                                    tint = colorResource(id = R.color.label_color),
                                    modifier = Modifier.size(8.dp)
                                )
                                var authorName: String
                                if (newsList[it].author.isNullOrEmpty()){

                                    authorName = "Removed"

                                }else{

                                    authorName = newsList[it].author.toString()
                                    newsList[it].author?.let { it1 -> Log.d("Null", it1) }
                                }
                                Text(
                                    text = authorName
                                    ,fontSize = 9.sp,
                                    fontFamily = robotoFamily,
                                    fontWeight = FontWeight.Bold,
                                    color = colorResource(id = R.color.label_color)
                                )
                            }

                            Column(
                                verticalArrangement = Arrangement.SpaceAround,
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.wrapContentSize()
                            ) {


                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(10.dp)
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {

                                        Icon(

                                            imageVector = Icons.Default.List,
                                            contentDescription = null,
                                            tint = colorResource(id = R.color.black),
                                            modifier = Modifier.size(15.dp)
                                        )
                                        Text(
                                            text = selectedCategory,
                                            fontSize = 9.sp,
                                            fontFamily = robotoFamily,
                                            fontWeight = FontWeight.Bold,
                                            color = colorResource(id = R.color.latest)
                                        )
                                    }
                                    val formatter = DateTimeFormatter.ISO_DATE_TIME
                                    val dateTime = LocalDateTime.parse(newsList[it].date, formatter)

                                    val extractedDate = dateTime.toLocalDate()
                                    val extractedTime = dateTime.toLocalTime()

                                    val now = LocalDateTime.now()
                                    val currentDate = now.toLocalDate()
                                    val currentTime = now.toLocalTime()

                                    val daysDifference = ChronoUnit.DAYS.between(extractedDate, currentDate)

                                    val timeDifference = ChronoUnit.HOURS.between(extractedTime, currentTime)

                                    val time = when {
                                        daysDifference > 0 -> {
                                            if (daysDifference.toInt() == 1){

                                                "$daysDifference day ago"
                                            }else{
                                                "$daysDifference days ago"
                                            }
                                        }
                                        timeDifference <= 0 -> {
                                            "Less than an hour ago"
                                        }
                                        else -> {
                                            "$timeDifference hours ago"
                                        }
                                    }
                                    Text(
                                        text = time,
                                        fontSize = 9.sp,
                                        fontFamily = robotoFamily,
                                        fontWeight = FontWeight.Bold,
                                        color = colorResource(id = R.color.serach_btn)
                                    )
                                }

                            }


                        }

                    }
                }
            }
        }

}

@Composable
fun WebView(newsResponse: NewsResponse){




}



