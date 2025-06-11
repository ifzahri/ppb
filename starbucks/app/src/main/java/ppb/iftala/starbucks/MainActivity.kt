package ppb.iftala.starbucks

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ppb.iftala.starbucks.ui.theme.StarbucksCloneTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StarbucksCloneTheme {
                StarbucksApp()
            }
        }
    }
}

// Data Models
data class MenuItem(
    val id: Int,
    val name: String,
    val description: String,
    val price: Double,
    val imageRes: Int,
    val category: String
)

data class CartItem(
    val menuItem: MenuItem,
    val quantity: Int
)

// Main App Composable
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StarbucksApp() {
    var selectedTab by remember { mutableStateOf(0) }
    var cartItems by remember { mutableStateOf(listOf<CartItem>()) }

    val tabs = listOf("Home", "Menu", "Cart", "Profile")
    val tabIcons = listOf(
        Icons.Default.Home,
        Icons.Default.Favorite,
        Icons.Default.ShoppingCart,
        Icons.Default.Person
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Starbucks",
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF00704A)
                ),
                actions = {
                    IconButton(onClick = { /* Notifications */ }) {
                        Icon(
                            Icons.Default.Notifications,
                            contentDescription = "Notifications",
                            tint = Color.White
                        )
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = Color.White,
                contentColor = Color(0xFF00704A)
            ) {
                tabs.forEachIndexed { index, title ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                tabIcons[index],
                                contentDescription = title,
                                tint = if (selectedTab == index) Color(0xFF00704A) else Color.Gray
                            )
                        },
                        label = {
                            Text(
                                title,
                                color = if (selectedTab == index) Color(0xFF00704A) else Color.Gray
                            )
                        },
                        selected = selectedTab == index,
                        onClick = { selectedTab = index }
                    )
                }
            }
        }
    ) { paddingValues ->
        when (selectedTab) {
            0 -> HomeScreen(
                modifier = Modifier.padding(paddingValues),
                onAddToCart = { item ->
                    val existingItem = cartItems.find { it.menuItem.id == item.id }
                    cartItems = if (existingItem != null) {
                        cartItems.map {
                            if (it.menuItem.id == item.id) {
                                it.copy(quantity = it.quantity + 1)
                            } else it
                        }
                    } else {
                        cartItems + CartItem(item, 1)
                    }
                }
            )
            1 -> MenuScreen(
                modifier = Modifier.padding(paddingValues),
                onAddToCart = { item ->
                    val existingItem = cartItems.find { it.menuItem.id == item.id }
                    cartItems = if (existingItem != null) {
                        cartItems.map {
                            if (it.menuItem.id == item.id) {
                                it.copy(quantity = it.quantity + 1)
                            } else it
                        }
                    } else {
                        cartItems + CartItem(item, 1)
                    }
                }
            )
            2 -> CartScreen(
                modifier = Modifier.padding(paddingValues),
                cartItems = cartItems,
                onUpdateQuantity = { itemId, newQuantity ->
                    cartItems = if (newQuantity > 0) {
                        cartItems.map {
                            if (it.menuItem.id == itemId) {
                                it.copy(quantity = newQuantity)
                            } else it
                        }
                    } else {
                        cartItems.filter { it.menuItem.id != itemId }
                    }
                }
            )
            3 -> ProfileScreen(modifier = Modifier.padding(paddingValues))
        }
    }
}

// Home Screen
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onAddToCart: (MenuItem) -> Unit
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF8F8F8)),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            // Welcome Banner
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF00704A)),
                shape = RoundedCornerShape(16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.horizontalGradient(
                                colors = listOf(Color(0xFF00704A), Color(0xFF006B47))
                            )
                        )
                        .padding(24.dp)
                ) {
                    Column(
                        modifier = Modifier.align(Alignment.CenterStart)
                    ) {
                        Text(
                            "Good Morning!",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            "Start your day with your favorite coffee",
                            fontSize = 16.sp,
                            color = Color.White.copy(alpha = 0.9f)
                        )
                    }
                }
            }
        }

        item {
            // Quick Actions
            Text(
                "Quick Order",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2D2D2D)
            )

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(vertical = 12.dp)
            ) {
                items(getSampleMenuItems().take(3)) { item ->
                    QuickOrderCard(
                        item = item,
                        onAddToCart = onAddToCart
                    )
                }
            }
        }

        item {
            // Featured Items
            Text(
                "Featured Items",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2D2D2D)
            )
        }

        items(getSampleMenuItems().take(5)) { item ->
            FeaturedItemCard(
                item = item,
                onAddToCart = onAddToCart
            )
        }
    }
}

// Menu Screen
@Composable
fun MenuScreen(
    modifier: Modifier = Modifier,
    onAddToCart: (MenuItem) -> Unit
) {
    var selectedCategory by remember { mutableStateOf("All") }
    val categories = listOf("All", "Coffee", "Tea", "Frappuccino", "Food")
    val menuItems = getSampleMenuItems()

    val filteredItems = if (selectedCategory == "All") {
        menuItems
    } else {
        menuItems.filter { it.category == selectedCategory }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF8F8F8)),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            // Category Tabs
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                items(categories) { category ->
                    FilterChip(
                        onClick = { selectedCategory = category },
                        label = { Text(category) },
                        selected = selectedCategory == category,
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = Color(0xFF00704A),
                            selectedLabelColor = Color.White
                        )
                    )
                }
            }
        }

        items(filteredItems) { item ->
            MenuItemCard(
                item = item,
                onAddToCart = onAddToCart
            )
        }
    }
}

// Cart Screen
@Composable
fun CartScreen(
    modifier: Modifier = Modifier,
    cartItems: List<CartItem>,
    onUpdateQuantity: (Int, Int) -> Unit
) {
    val totalPrice = cartItems.sumOf { it.menuItem.price * it.quantity }

    if (cartItems.isEmpty()) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    Icons.Default.ShoppingCart,
                    contentDescription = null,
                    modifier = Modifier.size(64.dp),
                    tint = Color.Gray
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    "Your cart is empty",
                    fontSize = 18.sp,
                    color = Color.Gray
                )
            }
        }
    } else {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(Color(0xFFF8F8F8))
        ) {
            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(cartItems) { cartItem ->
                    CartItemCard(
                        cartItem = cartItem,
                        onUpdateQuantity = onUpdateQuantity
                    )
                }
            }

            // Checkout Section
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            "Total",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            "$${String.format("%.2f", totalPrice)}",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF00704A)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = { /* Proceed to checkout */ },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF00704A)
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            "Proceed to Checkout",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}

// Profile Screen
@Composable
fun ProfileScreen(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF8F8F8)),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            // Profile Header
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF00704A)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "JD",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        "John Doe",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        "Gold Member",
                        fontSize = 16.sp,
                        color = Color(0xFFD4AF37)
                    )
                }
            }
        }

        item {
            ProfileMenuItem(
                icon = Icons.Default.Person,
                title = "Edit Profile",
                onClick = { }
            )
        }

        item {
            ProfileMenuItem(
                icon = Icons.Default.Settings,
                title = "Order History",
                onClick = { }
            )
        }

        item {
            ProfileMenuItem(
                icon = Icons.Default.Favorite,
                title = "Favorites",
                onClick = { }
            )
        }

        item {
            ProfileMenuItem(
                icon = Icons.Default.Settings,
                title = "Settings",
                onClick = { }
            )
        }

        item {
            ProfileMenuItem(
                icon = Icons.Default.Home,
                title = "Help & Support",
                onClick = { }
            )
        }
    }
}

// Component Cards
@Composable
fun QuickOrderCard(
    item: MenuItem,
    onAddToCart: (MenuItem) -> Unit
) {
    Card(
        modifier = Modifier
            .width(120.dp)
            .clickable { onAddToCart(item) },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFF0F0F0)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.ShoppingCart,
                    contentDescription = null,
                    tint = Color(0xFF00704A),
                    modifier = Modifier.size(32.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                item.name,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                maxLines = 2
            )

            Text(
                "$${String.format("%.2f", item.price)}",
                fontSize = 12.sp,
                color = Color(0xFF00704A),
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun FeaturedItemCard(
    item: MenuItem,
    onAddToCart: (MenuItem) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFFF0F0F0)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.ShoppingCart,
                    contentDescription = null,
                    tint = Color(0xFF00704A),
                    modifier = Modifier.size(28.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    item.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )

                Text(
                    item.description,
                    fontSize = 14.sp,
                    color = Color.Gray,
                    maxLines = 2
                )

                Text(
                    "$${String.format("%.2f", item.price)}",
                    fontSize = 16.sp,
                    color = Color(0xFF00704A),
                    fontWeight = FontWeight.Bold
                )
            }

            IconButton(
                onClick = { onAddToCart(item) }
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "Add to cart",
                    tint = Color(0xFF00704A)
                )
            }
        }
    }
}

@Composable
fun MenuItemCard(
    item: MenuItem,
    onAddToCart: (MenuItem) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFFF0F0F0)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.ShoppingCart,
                    contentDescription = null,
                    tint = Color(0xFF00704A),
                    modifier = Modifier.size(36.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    item.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )

                Text(
                    item.description,
                    fontSize = 14.sp,
                    color = Color.Gray,
                    maxLines = 2
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    "$${String.format("%.2f", item.price)}",
                    fontSize = 18.sp,
                    color = Color(0xFF00704A),
                    fontWeight = FontWeight.Bold
                )
            }

            Button(
                onClick = { onAddToCart(item) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF00704A)
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Add")
            }
        }
    }
}

@Composable
fun CartItemCard(
    cartItem: CartItem,
    onUpdateQuantity: (Int, Int) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFFF0F0F0)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.ShoppingCart,
                    contentDescription = null,
                    tint = Color(0xFF00704A),
                    modifier = Modifier.size(28.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    cartItem.menuItem.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )

                Text(
                    "$${String.format("%.2f", cartItem.menuItem.price)}",
                    fontSize = 14.sp,
                    color = Color(0xFF00704A),
                    fontWeight = FontWeight.Bold
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {
                        onUpdateQuantity(cartItem.menuItem.id, cartItem.quantity - 1)
                    }
                ) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "Decrease quantity"
                    )
                }

                Text(
                    cartItem.quantity.toString(),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )

                IconButton(
                    onClick = {
                        onUpdateQuantity(cartItem.menuItem.id, cartItem.quantity + 1)
                    }
                ) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = "Increase quantity"
                    )
                }
            }
        }
    }
}

@Composable
fun ProfileMenuItem(
    icon: ImageVector,
    title: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                icon,
                contentDescription = null,
                tint = Color(0xFF00704A),
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.weight(1f)
            )

            Icon(
                Icons.Default.KeyboardArrowDown,
                contentDescription = null,
                tint = Color.Gray
            )
        }
    }
}

// Sample Data
fun getSampleMenuItems(): List<MenuItem> {
    return listOf(
        MenuItem(1, "Americano", "Rich espresso with hot water", 3.45, 0, "Coffee"),
        MenuItem(2, "Cappuccino", "Espresso with steamed milk foam", 4.25, 0, "Coffee"),
        MenuItem(3, "Latte", "Espresso with steamed milk", 4.45, 0, "Coffee"),
        MenuItem(4, "Mocha", "Espresso with chocolate and steamed milk", 4.95, 0, "Coffee"),
        MenuItem(5, "Green Tea", "Premium green tea blend", 2.95, 0, "Tea"),
        MenuItem(6, "Earl Grey", "Classic black tea with bergamot", 2.95, 0, "Tea"),
        MenuItem(7, "Caramel Frappuccino", "Blended coffee with caramel", 5.25, 0, "Frappuccino"),
        MenuItem(8, "Vanilla Frappuccino", "Blended coffee with vanilla", 5.25, 0, "Frappuccino"),
        MenuItem(9, "Croissant", "Buttery flaky pastry", 2.95, 0, "Food"),
        MenuItem(10, "Blueberry Muffin", "Fresh baked muffin with blueberries", 3.25, 0, "Food")
    )
}

@Preview(showBackground = true)
@Composable
fun StarbucksAppPreview() {
    StarbucksCloneTheme {
        StarbucksApp()
    }
}
