package com.roblesdotdev.ohmylist.shoplistDetail.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.roblesdotdev.ohmylist.core.domain.model.Product
import com.roblesdotdev.ohmylist.core.domain.model.ShopList
import com.roblesdotdev.ohmylist.core.ui.theme.OhMyListTheme

@Composable
fun ShopListDetailScreen(
    state: ShopListDetailState,
    onEvent: (ShopListDetailEvent) -> Unit,
) {
    Scaffold(
        topBar = {
            ShopListDetailTab()
        },
    ) { paddingValues ->
        ShopListDetailContent(paddingValues = paddingValues, state = state, onEvent = onEvent)
        if (state.showDialog) {
            ProductDialog(onEvent = onEvent, input = state.input)
        }
    }
}

@Composable
fun ShopListDetailContent(
    paddingValues: PaddingValues,
    state: ShopListDetailState,
    onEvent: (ShopListDetailEvent) -> Unit,
) {
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .padding(bottom = 24.dp),
    ) {
        LazyColumn(
            modifier =
                Modifier
                    .padding(bottom = 16.dp)
                    .weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            item {
                Text(
                    text = state.item?.title ?: "",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 16.dp),
                )
            }

            if (state.item?.products?.isNotEmpty() == true) {
                items(state.item.products) { product ->
                    Card(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .clickable { },
                        shape = RoundedCornerShape(8.dp),
                    ) {
                        Row(
                            modifier = Modifier.padding(vertical = 12.dp, horizontal = 16.dp),
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(text = product.name, fontWeight = FontWeight.Bold)
                                Text(text = product.description, fontSize = 14.sp)
                            }
                            IconButton(onClick = { /*TODO*/ }) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = null,
                                )
                            }
                        }
                    }
                }
            }
        }
        Button(
            onClick = { onEvent(ShopListDetailEvent.OpenModal) },
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(56.dp),
            shape = RoundedCornerShape(8.dp),
        ) {
            Text(text = "Add product to list")
        }
    }
}

@Composable
fun ProductDialog(
    input: ProductInput,
    onEvent: (ShopListDetailEvent) -> Unit,
) {
    Dialog(
        onDismissRequest = { onEvent(ShopListDetailEvent.CloseModal) },
    ) {
        Surface(
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth(),
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Add product", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(32.dp))
                TextField(
                    value = input.name,
                    onValueChange = { onEvent(ShopListDetailEvent.ChangeInputName(it)) },
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = input.description,
                    onValueChange = { onEvent(ShopListDetailEvent.ChangeInputDescription(it)) },
                )
                Spacer(modifier = Modifier.height(32.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                ) {
                    TextButton(onClick = { onEvent(ShopListDetailEvent.CloseModal) }) {
                        Text(text = "Cancel")
                    }
                    Button(
                        onClick = { onEvent(ShopListDetailEvent.AddProduct) },
                        shape = RoundedCornerShape(8.dp),
                        enabled = input.isValid,
                    ) {
                        Text(text = "Save")
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShopListDetailTab() {
    TopAppBar(
        title = {
            Text(text = "List Detail", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        },
        navigationIcon = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
            }
        },
        actions = {
            TextButton(onClick = { /*TODO*/ }) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "Edit",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground,
                    )
                }
            }
        },
    )
}

@Preview
@Composable
private fun ShopListDetailScreenPreview() {
    OhMyListTheme {
        ShopListDetailScreen(
            onEvent = {},
            state =
                ShopListDetailState(
                    showDialog = true,
                    item =
                        ShopList(
                            id = 1,
                            title = "Foo",
                            products =
                                listOf(
                                    Product(id = 1, name = "Foo product", description = "foo description"),
                                ),
                        ),
                ),
        )
    }
}
