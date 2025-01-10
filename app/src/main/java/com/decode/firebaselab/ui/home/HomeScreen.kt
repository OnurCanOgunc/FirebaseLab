package com.decode.firebaselab.ui.home


import android.R.attr.contentDescription
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LibraryMusic
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Pause
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Speaker
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.firestore.FirebaseFirestore
import com.decode.firebaselab.R
import com.decode.firebaselab.ui.theme.categoryBackground
import com.decode.firebaselab.ui.theme.gray2
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter.State.Empty.painter
import com.decode.firebaselab.data.model.Artist
import com.decode.firebaselab.ui.theme.black

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    name: String = "",
    homeViewModel: HomeViewModel = HomeViewModel()
) {

    val artist by homeViewModel.artist.collectAsState()

    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val itemWidth = (screenWidth - 40.dp) / 2
    val categories =
        arrayOf("Liked Songs", "Recently Played", "Top Lists", "Following", "New Releases", "Mood")

    Scaffold(
        modifier = modifier.fillMaxSize().background(black),
        topBar = { TopBar() },
        bottomBar = { BottomBar() }) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = black)
                    .padding(paddingValues),
            ) {
                Categories()
                Spacer(modifier = Modifier.height(20.dp))

                //Error
                LazyVerticalStaggeredGrid(
                    columns = StaggeredGridCells.Fixed(2),
                    modifier = Modifier.padding(10.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalItemSpacing = 10.dp
                ) {
                    items(categories.size) {
                        CategoryItem(category = categories[it])
                    }
                }
                Text(
                    text = "Your top mixes",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(10.dp)
                )
                LazyRow(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(20.dp),
                ) {
                    items(items = artist) {
                        SongsItem(modifier = Modifier.width(itemWidth), artist = it)
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "More of what you like",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(start = 10.dp)
                )
                SongPlayerCard()
            }
        }
}


@Composable
fun TopBar(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .padding(vertical = 16.dp, horizontal = 20.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Good afternoon ",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.Notifications,
                contentDescription = null,
                tint = Color.White
            )
            Icon(
                imageVector = Icons.Outlined.AccessTime,
                contentDescription = null,
                tint = Color.White
            )
            Icon(
                imageVector = Icons.Outlined.Settings,
                contentDescription = null,
                tint = Color.White
            )
        }
    }
}

@Composable
fun Categories(modifier: Modifier = Modifier) {
    val categories = arrayOf("Music", "Podcasts")
    Row(
        modifier = modifier
            .padding(horizontal = 10.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        for (i in categories) {
            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(containerColor = categoryBackground)
            ) {
                Text(text = i, color = Color.White)
            }
        }
    }
}

@Composable
fun CategoryItem(modifier: Modifier = Modifier, category: String) {
    Row(
        modifier = modifier
            .background(categoryBackground, shape = RoundedCornerShape(10.dp))
            .size(179.dp, 59.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(R.drawable.img),
            contentDescription = null,
            modifier = Modifier
                .size(59.dp)
                .clip(RoundedCornerShape(10.dp)),
            contentScale = ContentScale.FillHeight
        )
        Text(text = category, modifier = modifier.padding(horizontal = 10.dp), color = Color.White)
    }
}

@Composable
fun SongsItem(modifier: Modifier = Modifier,artist: Artist) {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        AsyncImage(
            model = artist.image,
            contentDescription = null,
            modifier = Modifier.size(147.dp)
        )
        Text(
            text = "${artist.name} | ${artist.description}",
            color = gray2,
            fontSize = 14.sp,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.width(147.dp)
        )
    }
}

@Composable
fun SongPlayerCard(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(58.dp)
            .padding(horizontal = 4.dp)
            .background(color = Color(0xFF6E4C4C), shape = RoundedCornerShape(8.dp)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(R.drawable.img),
            contentDescription = null,
            modifier = Modifier
                .padding(start = 6.dp)
                .size(44.dp)
                .clip(RoundedCornerShape(4.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Mast Magan",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "Arijit Singh",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = Color(0xFFE0E0E0)
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        IconButton(onClick = {}) {
            Icon(
                imageVector = Icons.Outlined.Speaker,
                contentDescription = "Device",
                tint = Color.White
            )
        }

        IconButton(onClick = {}) {
            Icon(
                imageVector = Icons.Outlined.Pause,
                contentDescription = "Play/Pause",
                tint = Color.White
            )
        }
    }
}

@Composable
fun BottomBar(modifier: Modifier = Modifier) {
    var selectedItem by remember { mutableStateOf(BottomNavigationItem.Home) }

    NavigationBar(
        modifier = modifier,
        contentColor = Color.White,
        containerColor = black,

    ) {
        BottomNavigationItem.entries.forEach {
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = it.icon,
                        contentDescription = it.title,
                        tint = Color.White
                    )
                },
                selected = it == selectedItem,
                onClick = {
                    selectedItem = it
                },
                label = { Text(text = it.title,color = Color.White) },
            )
        }
    }
}

enum class BottomNavigationItem(val icon: ImageVector, val title: String) {
    Home(
        icon = Icons.Default.Home,
        title = "Home",
    ),
    Favorites(
        icon = Icons.Default.Search,
        title = "Notification",
    ),
    Notification(
        icon = Icons.Default.LibraryMusic,
        title = "Your Library",
    )
}