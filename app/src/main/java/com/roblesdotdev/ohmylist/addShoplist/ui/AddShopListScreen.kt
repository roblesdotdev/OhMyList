package com.roblesdotdev.ohmylist.addShoplist.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.roblesdotdev.ohmylist.core.ui.components.CustomTextField
import com.roblesdotdev.ohmylist.core.ui.components.CustomTopAppBar
import com.roblesdotdev.ohmylist.core.ui.theme.OhMyListTheme

@Composable
fun AddShopListScreen(
    state: AddShopListState,
    onEvent: (AddShopListEvent) -> Unit,
    onListSaved: (Int) -> Unit,
    onBack: () -> Unit,
) {
    LaunchedEffect(key1 = state.savedListId) {
        if (state.savedListId != null) {
            onListSaved(state.savedListId)
        }
    }

    Scaffold(
        topBar = {
            AddShopListTab(onBack = onBack)
        },
    ) { paddingValues ->
        Column(
            modifier =
                Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 32.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(text = "Title", fontSize = 14.sp, fontWeight = FontWeight.Medium)
                CustomTextField(
                    value = state.title,
                    onChange = { onEvent(AddShopListEvent.ChangeTitle(it)) },
                    placeholder = "List title",
                    keyboardOptions =
                        KeyboardOptions(
                            imeAction = ImeAction.Done,
                        ),
                    modifier = Modifier.fillMaxWidth(),
                )
            }
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(text = "Group", fontSize = 14.sp, fontWeight = FontWeight.Medium)
                Surface(
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                    shape = RoundedCornerShape(4.dp),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    Row(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp, vertical = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text(text = state.group)
                        Icon(imageVector = Icons.Default.Check, contentDescription = null)
                    }
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = { onEvent(AddShopListEvent.Save) },
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                shape = RoundedCornerShape(8.dp),
                enabled = state.isFormValid,
            ) {
                Text(text = "Save")
            }
        }
    }
}

@Composable
fun AddShopListTab(onBack: () -> Unit) {
    CustomTopAppBar(
        title = "New shopping list",
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
            }
        },
    )
}

@Preview
@Composable
private fun AddShopListScreenPreview() {
    OhMyListTheme {
        AddShopListScreen(state = AddShopListState(), onEvent = {}, onListSaved = {}, onBack = {})
    }
}
