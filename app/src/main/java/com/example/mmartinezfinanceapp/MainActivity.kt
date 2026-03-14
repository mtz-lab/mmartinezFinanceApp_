package com.example.mmartinezfinanceapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mmartinezfinanceapp.ui.theme.MMartinezFinanceAppTheme

data class User(val name: String, val balance: Double)

data class Transaction(
    val merchantName: String,
    val category: String,
    val amount: Double,
    val time: String,
    @DrawableRes val iconRes: Int
)


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MMartinezFinanceAppTheme {
                val user = User("Juan", 1200.0)
                val transactions = listOf(
                    Transaction("Supermarket", "Groceries", 45.99, "10:30 AM", R.drawable.ic_groceries),
                    Transaction("Gas station", "Fuel", -30.5, "12:15 PM", R.drawable.ic_gas_station),
                    Transaction("Coffee Shop", "Food & Drinks", 5.75, "8:00 AM", R.drawable.ic_coffee),
                    Transaction("Electronics Store", "Electronics", 120.0, "3:45 PM", R.drawable.ic_electric),
                    Transaction("Bookstore", "Books", 25.99, "2:00 PM", R.drawable.ic_book),
                    Transaction("Restaurant", "Dining", 60.0, "7:30 PM", R.drawable.ic_diningg)
                )

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    HomeScreen(user, transactions, Modifier.padding(innerPadding))
                }
            }
        }
    }
}

// homescreen
@Composable
fun HomeScreen(
    user: User,
    transactions: List<Transaction>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(16.dp)) {

        //Seccion de header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .background(Color(0xFFF5E6DA), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_activity_faceee),
                        contentDescription = "Profile Picture",
                        modifier = Modifier.size(32.dp),
                        tint = Color.Black
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        text = "Hola ${user.name}",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Bienvenido",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                }
            }

            IconButton(onClick = { /* abrir menú */ }) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Menu",
                    modifier = Modifier.size(32.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        //Seccion tarjetas
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Tarjeta de Actividad
            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFFE0F2F1)),
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier.weight(1f).fillMaxHeight()
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_face_kid),
                        contentDescription = null,
                        modifier = Modifier.size(35.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Actividad", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                    Text("de la Semana", color = Color.Gray, style = MaterialTheme.typography.bodySmall)
                }
            }

            // Columnas de Ventas y Ganancias
            Column(
                modifier = Modifier.weight(1f).fillMaxHeight(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                SmallSummaryCard("Ventas", "$280.99", Color(0xFFF5E6DA), Modifier.weight(1f))
                SmallSummaryCard("Ganancias", "$280.99", Color(0xFFEDE7F6), Modifier.weight(1f))
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        //Seccion transacciones
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Transactions", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            Text(
                text = "See All",
                color = Color.Gray,
                modifier = Modifier.clickable { }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(transactions) { transaction ->
                TransactionItem(transaction)
            }
        }
    }
}


@Composable
fun SmallSummaryCard(title: String, amount: String, color: Color, modifier: Modifier) {
    Card(
        colors = CardDefaults.cardColors(containerColor = color),
        shape = RoundedCornerShape(24.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = title, color = Color.Gray, style = MaterialTheme.typography.bodyMedium)
            Text(text = amount, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleLarge)
        }
    }
}

@Composable
fun TransactionItem(transaction: Transaction) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .size(50.dp)
                .background(Color.Black, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = transaction.iconRes),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(text = transaction.merchantName, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyLarge)
            Text(text = transaction.category, color = Color.Gray, style = MaterialTheme.typography.bodyMedium)
        }

        Column(horizontalAlignment = Alignment.End) {
            val isNegative = transaction.amount < 0
            Text(
                text = if (isNegative) "$-${Math.abs(transaction.amount)}" else "$${transaction.amount}",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.bodyLarge
            )
            Text(text = transaction.time, color = Color.Gray, style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    MMartinezFinanceAppTheme {
        val user = User("Juan", 1200.0)
        val transactions = listOf(
            Transaction("Supermarket", "Groceries", 45.99, "10:30 AM", R.drawable.ic_groceries)
        )
        HomeScreen(user, transactions)
    }
}