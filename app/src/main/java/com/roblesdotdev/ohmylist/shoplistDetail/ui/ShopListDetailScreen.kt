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
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.roblesdotdev.ohmylist.core.domain.model.Product
import com.roblesdotdev.ohmylist.core.domain.model.ProductInput
import com.roblesdotdev.ohmylist.core.domain.model.ShopList
import com.roblesdotdev.ohmylist.core.ui.components.CustomCard
import com.roblesdotdev.ohmylist.core.ui.components.CustomTextField
import com.roblesdotdev.ohmylist.core.ui.components.CustomTopAppBar
import com.roblesdotdev.ohmylist.core.ui.theme.OhMyListTheme

@Composable
fun ShopListDetailScreen(
    state: ShopListDetailState,
    onEvent: (ShopListDetailEvent) -> Unit,
    onBack: () -> Unit,
    onEdit: (Int) -> Unit,
) {
    Scaffold(
        topBar = {
            ShopListDetailTab(onBack = onBack, onEditClick = {
                state.item?.let { onEdit(it.id) }
            })
        },
    ) { paddingValues ->
        ShopListDetailContent(paddingValues = paddingValues, state = state, onEvent = onEvent)
        if (state.showDialog) {
            ProductDialog(onEvent = onEvent, productInput = state.productInput)
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
                    CustomCard(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .clickable { onEvent(ShopListDetailEvent.EditProduct(product)) },
                    ) {
                        Row(
                            modifier = Modifier.padding(vertical = 12.dp, horizontal = 16.dp),
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(text = product.name, fontWeight = FontWeight.Bold)
                                Text(text = product.description, fontSize = 14.sp)
                            }
                            Checkbox(checked = product.isChecked, onCheckedChange = {
                                onEvent(ShopListDetailEvent.ToggleProductChecked(product))
                            })
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
    productInput: ProductInput,
    onEvent: (ShopListDetailEvent) -> Unit,
) {
    Dialog(
        onDismissRequest = { onEvent(ShopListDetailEvent.CloseModal) },
    ) {
        Surface(
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.background,
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                val title = if (productInput.isEdit) "Edit product" else "Add product"
                Text(text = title, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(32.dp))
                CustomTextField(
                    value = productInput.name,
                    onChange = { onEvent(ShopListDetailEvent.ChangeInputName(it)) },
                )
                Spacer(modifier = Modifier.height(8.dp))
                CustomTextField(
                    value = productInput.description,
                    onChange = { onEvent(ShopListDetailEvent.ChangeInputDescription(it)) },
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
                        enabled = productInput.isValid,
                    ) {
                        Text(text = "Save")
                    }
                }
            }
        }
    }
}

@Composable
fun ShopListDetailTab(
    onBack: () -> Unit,
    onEditClick: () -> Unit,
) {
    CustomTopAppBar(
        title = "List detail",
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
            }
        },
        actions = {
            TextButton(onClick = onEditClick) {
                Text(
                    text = "Edit",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground,
                )
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
            onBack = {},
            onEdit = {},
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
