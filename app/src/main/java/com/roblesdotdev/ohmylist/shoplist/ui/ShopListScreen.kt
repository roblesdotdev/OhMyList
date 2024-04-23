package com.roblesdotdev.ohmylist.shoplist.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.roblesdotdev.ohmylist.core.domain.model.ShopList
import com.roblesdotdev.ohmylist.core.ui.theme.OhMyListTheme

@Composable
fun ShopListScreen(state: ShopListState) {
    Scaffold(
        topBar = {
            ShopListTab()
        },
        floatingActionButton = {
            ShopListFab(onClick = {})
        },
    ) { paddingValues ->
        LazyColumn(
            contentPadding = paddingValues,
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            item {
                Text(text = "My lists", fontSize = 14.sp, fontWeight = FontWeight.Medium)
            }

            items(state.items, key = { it.id }) { item ->
                ShopListItem(item)
            }
        }
    }
}

@Composable
fun ShopListItem(item: ShopList) {
    Card(
        modifier =
            Modifier
                .fillMaxWidth()
                .clickable { },
        shape = RoundedCornerShape(8.dp),
    ) {
        Row(
            modifier = Modifier.padding(vertical = 12.dp, horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = item.title, fontWeight = FontWeight.Bold)
                Text(text = "5 product(s)", fontSize = 12.sp)
                Spacer(modifier = Modifier.height(12.dp))
                Surface(
                    shape = RoundedCornerShape(8.dp),
                ) {
                    Text(
                        text = item.group,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                    )
                }
            }
            Icon(imageVector = Icons.Default.MoreVert, contentDescription = null)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShopListTab() {
    TopAppBar(
        title = {
            Text(text = "Oh My List", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        },
    )
}

@Composable
fun ShopListFab(onClick: () -> Unit) {
    FloatingActionButton(onClick = onClick) {
        Icon(imageVector = Icons.Default.Add, contentDescription = null)
    }
}

@Preview(showBackground = true)
@Composable
private fun ShopListScreenPreview() {
    OhMyListTheme {
        ShopListScreen(state = ShopListState())
    }
}
