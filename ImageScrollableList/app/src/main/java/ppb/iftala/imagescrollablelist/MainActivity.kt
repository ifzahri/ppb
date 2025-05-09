package ppb.iftala.imagescrollablelist
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ppb.iftala.imagescrollablelist.ui.theme.ScrollableCardListTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ScrollableCardListTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ImageCardList(cardItems = sampleCardItems)
                }
            }
        }
    }
}

// Data class to represent each card item
data class CardItem(
    val id: Int,
    val imageResId: Int,
    val title: String,
    val description: String
)

// Sample data for our card list
val sampleCardItems = listOf(
    CardItem(
        id = 1,
        imageResId = R.drawable.demo,
        title = "Beautiful Mountains",
        description = "Serene mountain landscape with snow-capped peaks and green valleys."
    ),
    CardItem(
        id = 2,
        imageResId = R.drawable.demo,
        title = "Ocean Sunset",
        description = "Golden sunset over the calm ocean waters."
    ),
    CardItem(
        id = 3,
        imageResId = R.drawable.demo,
        title = "Forest Path",
        description = "A winding path through a dense, lush green forest."
    ),
    CardItem(
        id = 4,
        imageResId = R.drawable.demo,
        title = "Desert Dunes",
        description = "Rolling sand dunes stretching as far as the eye can see."
    ),
    CardItem(
        id = 5,
        imageResId = R.drawable.demo,
        title = "City Skyline",
        description = "Urban skyline with modern architecture and skyscrapers."
    ),
    CardItem(
        id = 6,
        imageResId = R.drawable.demo,
        title = "Autumn Colors",
        description = "Vibrant fall foliage with red, orange, and yellow leaves."
    ),
    CardItem(
        id = 7,
        imageResId = R.drawable.demo,
        title = "Tropical Beach",
        description = "White sandy beach with crystal clear waters and palm trees."
    ),
    CardItem(
        id = 8,
        imageResId = R.drawable.demo,
        title = "Northern Lights",
        description = "Spectacular aurora borealis lighting up the night sky."
    ),
    CardItem(
        id = 9,
        imageResId = R.drawable.demo,
        title = "Waterfall",
        description = "Powerful waterfall cascading down rocky cliffs."
    ),
    CardItem(
        id = 10,
        imageResId = R.drawable.demo,
        title = "Snowy Cabin",
        description = "Cozy cabin surrounded by snow-covered pine trees."
    )
)

@Composable
fun ImageCardList(cardItems: List<CardItem>) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(cardItems) { item ->
            ImageCard(cardItem = item)
        }
    }
}

@Composable
fun ImageCard(cardItem: CardItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(280.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            // Image takes up 70% of the card
            Image(
                painter = painterResource(id = cardItem.imageResId),
                contentDescription = cardItem.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )

            // Text content takes up the rest of the card
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = cardItem.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = cardItem.description,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ImageCardPreview() {
    ScrollableCardListTheme {
        ImageCard(
            cardItem = CardItem(
                id = 1,
                imageResId = R.drawable.demo,
                title = "Beautiful Mountains",
                description = "Serene mountain landscape with snow-capped peaks and green valleys."
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ImageCardListPreview() {
    ScrollableCardListTheme {
        ImageCardList(cardItems = sampleCardItems.take(3))
    }
}