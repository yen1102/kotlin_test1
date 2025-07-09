package com.example.kotlin_test1

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.runtime.*

@Composable
fun FavoritesScreen(navController: NavHostController) {
    var pageIndex by remember { mutableStateOf(0) }//初始值設為0

    var pageData = listOf(
        listOf("地標A", "地標B", "地標C", "地標D"),
        listOf("地標E", "地標F", "地標G", "地標H"),
        listOf("地標I", "地標J", "地標K", "地標L")
    )

    val totalPages = pageData.size

    val buttonColors = ButtonDefaults.buttonColors(//按鈕顏色
        containerColor = Color(0xFFbc8f8f),
        contentColor = Color.White
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF3DCDC))
            .padding(horizontal = 16.dp)
    ) {
        // 回首頁
        IconButton(
            onClick = { navController.navigate("main") },
            modifier = Modifier.padding(top = 25.dp, bottom = 4.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_home),
                contentDescription = "回首頁",
                modifier = Modifier.size(40.dp)
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        Box( //灰底框
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .width(370.dp)
                    .fillMaxHeight(0.8f)
                    .background(Color(0xFFDADADA))
                    .padding(15.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)//控制垂直排列元素之間的間距
            ){ // 2X2顯示
                val items = pageData[pageIndex % pageData.size]
                for(row in 0..1){
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ){
                        for(col in 0..1){
                            val index = row*2 + col
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .aspectRatio(1f)
                                    .background(Color.White),
                                contentAlignment = Alignment.Center
                            ){
                                Text(text = items[index])
                            }
                        }
                    }
                }

            }
        }
        //翻頁按鈕
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            Button(
                onClick = {
                    if (pageIndex > 0) {
                        pageIndex--
                    }
                },
                colors = buttonColors,
                enabled = pageIndex > 0
            ) {
                Text("<")
            }

            // 下一頁
            Button(
                onClick = {
                    if (pageIndex < totalPages - 1) {
                        pageIndex++
                    }
                },
                colors = buttonColors,
                enabled = pageIndex < totalPages - 1
            ) {
                Text(">")
            }
        }
    }
}
