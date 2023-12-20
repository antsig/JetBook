package com.dicoding.jetreward.ui.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dicoding.jetreward.di.Injection
import com.dicoding.jetreward.model.OrderBook
import com.dicoding.jetreward.ui.ViewModelFactory
import com.dicoding.jetreward.ui.common.UiState
import com.dicoding.jetreward.ui.components.BookItem
import com.dicoding.jetreward.ui.components.Search

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (Long) -> Unit,

    ) {

    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getAllBooks()
            }

            is UiState.Success -> {
                HomeContent(
                    orderReward = uiState.data,
                    modifier = modifier,
                    navigateToDetail = navigateToDetail,
                )
            }

            is UiState.Error -> {}
        }
        Search()
    }
}

@Composable
fun HomeContent(
    orderReward: List<OrderBook>,
    modifier: Modifier = Modifier,
    navigateToDetail: (Long) -> Unit,
) {

    LazyVerticalGrid(
        columns = GridCells.Adaptive(140.dp),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
        items(orderReward) { data ->
            BookItem(
                image = data.book.image,
                title = data.book.title,
                requiredPoint = data.book.requiredPoint,
                modifier = Modifier.clickable {
                    navigateToDetail(data.book.id)
                }
            )
        }

    }

}

