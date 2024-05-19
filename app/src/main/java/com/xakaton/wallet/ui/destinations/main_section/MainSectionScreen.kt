package com.xakaton.wallet.ui.destinations.main_section

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Scaffold
import androidx.compose.material.TabRowDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.result.NavResult
import com.ramcosta.composedestinations.result.ResultRecipient
import com.xakaton.budget.ui.destinations.AddTransactionBottomSheetDestination
import com.xakaton.budget.ui.destinations.CalendarScreenMainSectionDestination
import com.xakaton.wallet.R
import com.xakaton.wallet.domain.models.CategoryType
import com.xakaton.wallet.ui.destinations.add_transaction.AddTransactionResult
import com.xakaton.wallet.ui.destinations.calendar.DateSelection
import com.xakaton.wallet.ui.destinations.main_section.components.IncomeContent
import com.xakaton.wallet.ui.destinations.main_section.components.SpendingContent
import com.xakaton.wallet.ui.nav_graphs.MainSectionNavGraph
import com.xakaton.wallet.ui.nav_graphs.SectionsNavigator
import com.xakaton.wallet.ui.utils.launchSingleTopNavigate
import kotlinx.coroutines.launch

@Composable
@Destination
@MainSectionNavGraph(start = true)
fun MainSectionScreen(
    sectionsNavigator: SectionsNavigator,
    viewModel: MainSectionViewModel = hiltViewModel(),
    resultRecipientAddTransaction: ResultRecipient<AddTransactionBottomSheetDestination, AddTransactionResult>,
    resultRecipientDate: ResultRecipient<CalendarScreenMainSectionDestination, DateSelection>,
) {
    resultRecipientAddTransaction.onNavResult { result ->
        when (result) {
            is NavResult.Canceled -> {}
            is NavResult.Value -> {
                when {
                    result.value.incomeType != null -> viewModel.addIncomeTransaction(
                        sum = result.value.sum.toBigDecimal(),
                        incomeType = result.value.incomeType!!
                    )

                    result.value.spendingType != null -> viewModel.addSpendingTransaction(
                        sum = result.value.sum.toBigDecimal(),
                        spendingType = result.value.spendingType!!
                    )
                }
            }
        }
    }

    resultRecipientDate.onNavResult { result ->
        when (result) {
            is NavResult.Canceled -> {}
            is NavResult.Value -> {
                viewModel.onStartDateChange(result.value.startDate ?: viewModel.startDate)
                viewModel.onEndDateChange(result.value.endDate ?: viewModel.endDate)
            }
        }
    }

    MainSectionScreen(
        viewModel = viewModel,
        navigateToCalendarScreen = { dateSelection: DateSelection ->
            sectionsNavigator.launchSingleTopNavigate(
                CalendarScreenMainSectionDestination(dateSelection)
            )
        }
    )
}

@Composable
private fun MainSectionScreen(
    viewModel: MainSectionViewModel,
    navigateToCalendarScreen: (DateSelection) -> Unit,
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF4F4F4)),
        backgroundColor = Color(0xFFF4F4F4),
        topBar = {
            TopAppBar()
        }
    ) { paddingValues ->
        MainSectionContent(
            modifier = Modifier.padding(paddingValues),
            viewModel = viewModel,
            navigateToCalendarScreen = navigateToCalendarScreen
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopAppBar(
    modifier: Modifier = Modifier,
) {
    androidx.compose.material3.TopAppBar(
        modifier = modifier,
        title = {},
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White,
            scrolledContainerColor = Color.White
        )
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun MainSectionContent(
    modifier: Modifier = Modifier,
    viewModel: MainSectionViewModel,
    navigateToCalendarScreen: (DateSelection) -> Unit,
) {
    val spendingTransactions = viewModel.spendingTransactions.collectAsState().value
        ?.filter { it.createdAt in viewModel.startDate..viewModel.endDate }


    val spendingLimit by viewModel.spendingLimit.collectAsState()

    val incomeTransactions = viewModel.incomeTransactions.collectAsState().value
        ?.filter { it.createdAt in viewModel.startDate..viewModel.endDate }

    val incomeGoals by viewModel.incomeGoals.collectAsState()

    val pagerState = rememberPagerState(pageCount = { CategoryType.entries.size })

    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        TabRow(pagerState = pagerState)

        HorizontalPager(
            state = pagerState,
        ) { page ->
            when (page) {
                0 -> {
                    SpendingContent(
                        transactions = spendingTransactions,
                        limit = spendingLimit,
                        navigateToCalendarScreen = navigateToCalendarScreen,
                        startDate = viewModel.startDate,
                        endDate = viewModel.endDate
                    )
                }

                1 -> {
                    IncomeContent(
                        transactions = incomeTransactions,
                        navigateToCalendarScreen = navigateToCalendarScreen,
                        startDate = viewModel.startDate,
                        endDate = viewModel.endDate
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun TabRow(
    pagerState: PagerState,
) {
    val coroutineScope = rememberCoroutineScope()

    androidx.compose.material.TabRow(
        selectedTabIndex = pagerState.currentPage,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                modifier = Modifier
                    .pagerTabIndicatorOffset(pagerState, tabPositions)
                    .padding(horizontal = 24.dp),
                color = Color(0xFFDCF42C)
            )
        },
        divider = { TabRowDefaults.Divider(thickness = 1.dp, color = Color(0xFFF4F4F4)) },
        backgroundColor = Color.White
    ) {
        CategoryType.entries.forEachIndexed { index, categoryType ->
            CategoryItem(
                text = if (categoryType == CategoryType.SPENDING)
                    stringResource(R.string.spendings) else stringResource(R.string.incomes),
                categoryIsActive = pagerState.currentPage == index,
                categoryChange = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                }
            )
        }
    }
}

@Composable
private fun CategoryItem(
    text: String,
    categoryChange: () -> Unit,
    categoryIsActive: Boolean,
) {
    val (color, fontWeightValue) = remember(categoryIsActive) {
        if (categoryIsActive) Color(0xFF262626) to 600 else Color(0xFF262626).copy(alpha = 0.40f) to 400
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable(
                interactionSource = MutableInteractionSource(),
                indication = null
            ) {
                categoryChange()
            }
    ) {
        Text(
            text = text,
            color = color,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            fontWeight = FontWeight(fontWeightValue),
        )

        Spacer(modifier = Modifier.height(8.dp))
    }
}