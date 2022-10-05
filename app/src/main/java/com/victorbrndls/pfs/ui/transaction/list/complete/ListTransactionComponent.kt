package com.victorbrndls.pfs.ui.transaction.list.complete

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.victorbrndls.pfs.core.category.entity.CategoryType
import com.victorbrndls.pfs.ui.designsystem.component.progress.PfsHorizontalProgressIndicator
import com.victorbrndls.pfs.ui.designsystem.theme.Black10
import com.victorbrndls.pfs.ui.designsystem.theme.Green40
import com.victorbrndls.pfs.ui.designsystem.theme.Red40
import com.victorbrndls.pfs.ui.designsystem.theme.Transparent
import com.victorbrndls.pfs.ui.ktx.stringRes
import com.victorbrndls.pfs.ui.transaction.filter.CategoryTypeFilter

@Composable
fun ListTransactionComponent(
    modifier: Modifier = Modifier,
    viewModel: ListTransactionViewModel = hiltViewModel(),
) {
    ListTransactionUI(
        transactions = viewModel.transactions.value,
        filteredCategoryType = viewModel.categoryType,
        onCategoryTypeSelected = { viewModel.updateCategoryType(it) },
        isLoading = viewModel.isLoading,
        modifier = modifier
    )
}

@Composable
private fun ListTransactionUI(
    transactions: List<TransactionListItem>,
    filteredCategoryType: CategoryType?,
    onCategoryTypeSelected: (CategoryType) -> Unit,
    isLoading: Boolean,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        if (isLoading) {
            PfsHorizontalProgressIndicator()
        }
        Column {
            CategoryTypeFilter(
                selected = filteredCategoryType,
                onSelected = onCategoryTypeSelected
            )
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(
                    items = transactions,
                    key = { it.id },
                    contentType = { it::class }
                ) { item ->
                    when (item) {
                        is TransactionDate -> DateHeader(item.date)
                        is TransactionExpenseModel -> ExpenseItem(item)
                        is TransactionIncomeModel -> IncomeItem(item)
                    }
                }
            }
        }
    }
}

@Composable
private fun DateHeader(date: String) {
    Surface(
        color = Black10,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = date,
            fontSize = 14.sp,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
        )
    }
}

@Composable
private fun IncomeItem(
    income: TransactionIncomeModel,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.transactionDefault()
    ) {
        Text(
            text = income.description,
            fontSize = 16.sp
        )
        Text(
            text = income.amount,
            color = Green40
        )
    }
}

@Composable
private fun ExpenseItem(
    expense: TransactionExpenseModel,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.transactionDefault()
    ) {
        Text(
            text = expense.description,
            fontSize = 16.sp
        )
        Text(
            text = expense.amount,
            color = Red40
        )
    }
}

private fun Modifier.transactionDefault() =
    fillMaxWidth()
        .padding(vertical = 6.dp, horizontal = 12.dp)