/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import com.example.androiddevchallenge.ui.theme.MyTheme

private val cats = listOf(
    Cat(CatId("c1"), "Hector", "Bengal", 4, "Fiesty"),
    Cat(CatId("c2"), "Horatio", "Bengal", 2, "Attention seeking"),
    Cat(CatId("c3"), "Hercules", "Bengal", 8, "Chilled"),
    Cat(CatId("c4"), "Tibbles", "Tabby", 7, "Chilled"),
    Cat(CatId("c5"), "Michael", "Persian", 4, "Uncouth"),
    Cat(CatId("c6"), "Spot", "Tabby", 7, "Happy go lucky"),
    Cat(CatId("c7"), "Felix", "Siamese", 1, "Hunter"),
    Cat(CatId("c8"), "Rosie", "Ragdoll", 5, "Friendly"),
    Cat(CatId("c9"), "Jim", "Ragdoll", 4, "Friendly"),
    Cat(CatId("c10"), "Tutem", "Sphynx", 2, "Withdrawn"),
    Cat(CatId("c11"), "Ka", "Sphynx", 2, "Aloof"),
    Cat(CatId("c12"), "Moon", "Sphynx", 3, "Boss"),
)

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTheme {
                val navController = rememberNavController()
                NavHost(navController, startDestination = "overview") {
                    composable("overview") { MyApp(navController) }
                    composable("details/{catId}") {
                        AdoptionDetails(CatId(it.arguments?.getString("catId")!!))
                    }
                }
            }
        }
    }
}

// Start building your app here!
@Composable
fun MyApp(navController: NavHostController) {
    Surface(color = MaterialTheme.colors.background) {
        Column {
            Header("Kitty Katdoption Center")
            CatsList(cats = cats, navController)
        }
    }
}

@Composable
fun Header(title: String) {
    Text(
        text = title,
        textAlign = TextAlign.Center,
        maxLines = 1,
        textDecoration = TextDecoration.Underline,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Green)
            .padding(PaddingValues(20.dp))
    )
}

@Composable
fun CatsList(
    cats: List<Cat>,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    Box(modifier) {
        LazyColumn(
            Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(20.dp)
        ) {
            // TODO should be in ViewModel
            val grouped: Map<String, List<Cat>> = cats.groupBy { it.type }

            grouped.forEach { (initial, cats) ->
                item {
                    CharacterHeader(char = initial, Modifier.fillParentMaxWidth())
                }
                items(cats) { cat ->
                    CatListItem(
                        cat = cat,
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        navController.navigate("details/${cat.id.raw}")
                    }
                }
            }
        }
    }
}

@Composable
fun CatListItem(
    cat: Cat,
    modifier: Modifier = Modifier,
    onClickAction: () -> Unit
) {
    Column(
        modifier = modifier
            .clickable { onClickAction.invoke() }
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(PaddingValues(0.dp, 10.dp, 0.dp, 10.dp))
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_cat),
                contentDescription = "Picture of ${cat.name}",
                modifier = Modifier
                    .height(60.dp)
                    .width(60.dp)
                    .padding(PaddingValues(end = 10.dp)),
                contentScale = ContentScale.Fit,
            )
            Column(Modifier.padding(PaddingValues(end = 5.dp))) {
                Text(text = cat.name)
                Text(text = cat.attitude)
            }
            Column(Modifier.padding(PaddingValues(start = 5.dp))) {
                Text(text = cat.type)
                Text(text = "Age ${cat.age}")
            }
        }
    }
}

@Composable
fun CharacterHeader(
    char: String,
    modifier: Modifier = Modifier,
) {
    Text(
        modifier = modifier,
        text = char[0].toString()
    )
}

@Preview("Light Theme", widthDp = 360, heightDp = 640)
@Composable
fun LightPreview() {
    MyTheme {
//        MyApp(navController)
    }
}

@Preview("Dark Theme", widthDp = 360, heightDp = 640)
@Composable
fun DarkPreview() {
    MyTheme(darkTheme = true) {
//        MyApp(navController)
    }
}

data class Cat(
    val id: CatId,
    val name: String,
    val type: String,
    val age: Int,
    val attitude: String
)

data class CatId(val raw: String)

@Composable
fun AdoptionDetails(
    catId: CatId,
) {
    val cat = cats.find { it.id == catId }!!
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_cat),
            contentDescription = null,
            modifier = Modifier
                .height(220.dp)
                .fillMaxWidth()
                .padding(PaddingValues(bottom = 10.dp)),
            contentScale = ContentScale.Crop,

        )
        Text(text = "Name: ${cat.name}")
        Text(text = "Age: ${cat.age}")
        Text(text = "Type: ${cat.type}")
        Text(text = "Attitude: ${cat.attitude}")
    }
}

@Preview("Adoption Details", widthDp = 360, heightDp = 640)
@Composable
fun AdoptionDetailsPreview() {
    MyTheme {
        AdoptionDetails(cats[0].id)
    }
}
