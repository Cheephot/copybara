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
import com.xakaton.wallet.R
import com.xakaton.wallet.ui.destinations.main_section.components.IncomeContent
import com.xakaton.wallet.ui.destinations.main_section.components.SpendingContent
import com.xakaton.wallet.ui.destinations.sections.CategoryType
import com.xakaton.wallet.ui.nav_graphs.MainSectionNavGraph
import com.xakaton.wallet.ui.nav_graphs.RootNavigator
import kotlinx.coroutines.launch

@Composable
@Destination
@MainSectionNavGraph(start = true)
fun MainSectionScreen(
    rootNavigator: RootNavigator,
    viewModel: MainSectionViewModel = hiltViewModel(),
) {
    MainSectionScreen(
        viewModel = viewModel
    )
}

@Composable
private fun MainSectionScreen(
    viewModel: MainSectionViewModel,
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
            viewModel = viewModel
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
) {
    val spendingTransactions by viewModel.spendingTransactions.collectAsState()
    val spendingLimit by viewModel.spendingLimit.collectAsState()
    val incomeTransactions by viewModel.incomeTransactions.collectAsState()
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
                        limit = spendingLimit
                    )
                }

                1 -> {
                    IncomeContent(
                        transactions = incomeTransactions,
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