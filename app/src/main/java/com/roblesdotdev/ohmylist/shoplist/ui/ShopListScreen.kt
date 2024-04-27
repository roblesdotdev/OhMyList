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
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.roblesdotdev.ohmylist.core.domain.model.ShopList
import com.roblesdotdev.ohmylist.core.ui.components.CustomCard
import com.roblesdotdev.ohmylist.core.ui.components.CustomTopAppBar
import com.roblesdotdev.ohmylist.core.ui.theme.OhMyListTheme

@Composable
fun ShopListScreen(
    state: ShopListState,
    onListClick: (Int) -> Unit,
    onAddClick: () -> Unit,
) {
    Scaffold(
        topBar = {
            CustomTopAppBar(title = "Oh My List")
        },
        floatingActionButton = {
            ShopListFab(onClick = onAddClick)
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
                ShopListItem(item, onClick = { onListClick(item.id) })
            }
        }
    }
}

@Composable
fun ShopListItem(
    item: ShopList,
    onClick: () -> Unit,
) {
    CustomCard(
        modifier =
            Modifier
                .fillMaxWidth()
                .clickable { onClick() },
    ) {
        Row(
            modifier = Modifier.padding(vertical = 12.dp, horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            val detailText =
                "${item.products.filter { it.isChecked }.size} of ${item.products.size} product(s)"
            Column(modifier = Modifier.weight(1f)) {
                Text(text = item.title, fontWeight = FontWeight.Bold)
                Text(text = detailText, fontSize = 12.sp)
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

@Composable
fun ShopListFab(onClick: () -> Unit) {
    FloatingActionButton(
        onClick = onClick,
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
    ) {
        Icon(imageVector = Icons.Default.Add, contentDescription = null)
    }
}

@Preview(showBackground = true)
@Composable
private fun ShopListScreenPreview() {
    OhMyListTheme {
        ShopListScreen(
            state =
                ShopListState(
                    items =
                        listOf(
                            ShopList(title = "Foo", group = "General"),
                        ),
                ),
            onListClick = {},
            onAddClick = {},
        )
    }
}
