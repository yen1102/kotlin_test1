package com.example.kotlin_test1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

data class Item(
    val itemid: Int,
    val itemFunc: String,
    val itemName: String,
    val itemType: Int,
    val itemEffect: String,
    val count: Int,
    val itemMethod: String,
    val itemRarity: String,
    val imageResId: Int
)

class BagActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "bag") {
                composable("bag") { BagScreen(navController) }
            }
        }
    }
}

@Composable
fun BagScreen(navController: NavHostController) {
    val allItems = remember {
        listOf(
            Item(1, "用於開啟寶箱", "金鑰匙", 1, "開啟寶箱", 2 , "由銀鑰匙合成","UR", R.drawable.item1),
            Item(2, "讓我充數一下", "史萊姆", 0, "就很可愛讓你觀賞", 4 , "路邊撿到的","S", R.drawable.item2),
        )
    }

    var selectedItem by remember { mutableStateOf<Item?>(null) }
    var filterState by remember { mutableStateOf(0) }

    val filteredItems = when (filterState) {
        1 -> allItems.filter { it.itemType == 0 } // 碎片
        2 -> allItems.filter { it.itemType == 1 } // 道具
        else -> allItems
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF3DCDC))
            .padding(horizontal = 16.dp)
    ) {
        // 回首頁
        IconButton(
            onClick = { navController.navigate("main") },
            modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_home),
                contentDescription = "回首頁",
                modifier = Modifier.size(48.dp)
            )
        }

        // Spacer 讓分類按鈕往下移
        Spacer(modifier = Modifier.height(4.dp))

        // 分類按鈕列
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFEFEFEF))
                .padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                "(全部)",
                modifier = Modifier.clickable { filterState = 0 },
                color = if (filterState == 0) Color.Black else Color.Gray
            )
            Text(
                "(碎片)",
                modifier = Modifier.clickable { filterState = 1 },
                color = if (filterState == 1) Color.Black else Color.Gray
            )
            Text(
                "(道具)",
                modifier = Modifier.clickable { filterState = 2 },
                color = if (filterState == 2) Color.Black else Color.Gray
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // 灰色背包框居中顯示
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .width(320.dp)
                    .fillMaxHeight(0.75f)
                    .background(Color(0xFFDADADA))
                    .padding(16.dp)
            ) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(filteredItems) { item ->
                        Box(
                            modifier = Modifier
                                .size(80.dp)
                                .background(Color.White)
                                .clickable { selectedItem = item },
                            contentAlignment = Alignment.BottomEnd
                        ) {
                            Image(
                                painter = painterResource(id = item.imageResId),
                                contentDescription = item.itemName,
                                modifier = Modifier.fillMaxSize()
                            )
                            Text(
                                text = "${item.count}",
                                color = Color.Black,
                                modifier = Modifier.padding(4.dp),
                                fontSize = 14.sp
                            )
                        }
                    }
                }

                // 詳細資料 Dialog
                selectedItem?.let { item ->
                    AlertDialog(
                        onDismissRequest = { selectedItem = null },
                        confirmButton = {},
                        title = {
                            Box(Modifier.fillMaxWidth()) {
                                Text(" ")
                                Text(
                                    text = "✕",
                                    modifier = Modifier
                                        .align(Alignment.TopEnd)
                                        .clickable { selectedItem = null },
                                    fontSize = 24.sp
                                )
                            }
                        },
                        text = {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 8.dp), // 讓左右也稍有留白
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Image(
                                    painter = painterResource(id = item.imageResId),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(200.dp)
                                        .padding(bottom = 8.dp)
                                )

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 8.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text("稀有度：${item.itemRarity}")
                                    Text("擁有 ${item.count} 件")
                                }

                                Spacer(modifier = Modifier.height(12.dp))

                                Column(
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text("物品介紹：", fontSize = 16.sp)
                                    Text(item.itemEffect)
                                }
                            }
                        },
                        shape = RoundedCornerShape(16.dp)
                    )
                }

            }
        }
    }
}
