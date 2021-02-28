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
import androidx.compose.foundation.layout.*
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
import com.example.androiddevchallenge.ui.theme.MyTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTheme {
                MyApp()
            }
        }
    }
}

// Start building your app here!
@Composable
fun MyApp() {
    val cats = listOf(
        Cat("Hector", "Bengal", 4, "Fiesty"),
        Cat("Horatio", "Bengal", 2, "Attention seeking"),
        Cat("Hercules", "Bengal", 8, "Chilled"),
        Cat("Tibbles", "Tabby", 7, "Chilled"),
        Cat("Michael", "Persian", 4, "Uncouth"),
        Cat("Spot", "Tabby", 7, "Happy go lucky"),
        Cat("Felix", "Siamese", 1, "Hunter"),
        Cat("Rosie", "Ragdoll", 5, "Friendly"),
        Cat("Jim", "Ragdoll", 4, "Friendly"),
        Cat("Tutem", "Sphynx", 2, "Withdrawn"),
        Cat("Ka", "Sphynx", 2, "Aloof"),
        Cat("Moon", "Sphynx", 3, "Boss"),
    )
    Surface(color = MaterialTheme.colors.background) {
        Column {
            Header("Kitty Katdoption Center")
            CatsList(cats = cats)
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
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }


        }
    }
}

@Composable
fun CatListItem(
    cat: Cat,
    modifier: Modifier = Modifier,
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

@Composable
fun CharacterHeader(
    char: String,
    modifier: Modifier = Modifier,
) {
    Text(text = char[0].toString())
}

@Preview("Light Theme", widthDp = 360, heightDp = 640)
@Composable
fun LightPreview() {
    MyTheme {
        MyApp()
    }
}

@Preview("Dark Theme", widthDp = 360, heightDp = 640)
@Composable
fun DarkPreview() {
    MyTheme(darkTheme = true) {
        MyApp()
    }
}

data class Cat(
    val name: String,
    val type: String,
    val age: Int,
    val attitude: String
)
