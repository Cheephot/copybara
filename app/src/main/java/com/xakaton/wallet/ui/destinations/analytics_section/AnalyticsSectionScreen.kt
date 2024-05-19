package com.xakaton.wallet.ui.destinations.analytics_section

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.TabRowDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.result.NavResult
import com.ramcosta.composedestinations.result.ResultRecipient
import com.xakaton.budget.ui.destinations.CalendarScreenAnalyticsSectionDestination
import com.xakaton.wallet.R
import com.xakaton.wallet.domain.models.CategoryType
import com.xakaton.wallet.ui.destinations.analytics_section.components.IncomeChart
import com.xakaton.wallet.ui.destinations.analytics_section.components.SpendingChart
import com.xakaton.wallet.ui.destinations.calendar.DateSelection
import com.xakaton.wallet.ui.destinations.main_section.components.formatDateRange
import com.xakaton.wallet.ui.nav_graphs.AnalyticsSectionNavGraph
import com.xakaton.wallet.ui.nav_graphs.SectionsNavigator
import com.xakaton.wallet.ui.utils.indicationClickable
import com.xakaton.wallet.ui.utils.launchSingleTopNavigate
import kotlinx.coroutines.launch
import java.time.LocalDate

@Composable
@Destination
@AnalyticsSectionNavGraph(start = true)
fun AnalyticsSectionScreen(
    sectionsNavigator: SectionsNavigator,
    viewModel: AnalyticsViewModel = hiltViewModel(),
    resultRecipientDate: ResultRecipient<CalendarScreenAnalyticsSectionDestination, DateSelection>,
) {
    resultRecipientDate.onNavResult { result ->
        when (result) {
            is NavResult.Canceled -> {}
            is NavResult.Value -> {
                viewModel.onStartDateChange(result.value.startDate ?: viewModel.startDate)
                viewModel.onEndDateChange(result.value.endDate ?: viewModel.endDate)

                viewModel.getTransaction()
            }
        }
    }

    AnalyticsSectionScreen(
        viewModel = viewModel,
        navigateToCalendarScreen = { dateSelection: DateSelection ->
            sectionsNavigator.launchSingleTopNavigate(
                CalendarScreenAnalyticsSectionDestination(dateSelection)
            )
        }
    )
}

@Composable
private fun AnalyticsSectionScreen(
    viewModel: AnalyticsViewModel,
    navigateToCalendarScreen: (DateSelection) -> Unit,
) {
    LifecycleAwareComponent(getTransactions = viewModel::getTransaction)

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF4F4F4)),
        backgroundColor = Color(0xFFF4F4F4),
        topBar = {
            TopAppBar()
        }
    ) { paddingValues ->
        AnalyticsSectionContent(
            modifier = Modifier.padding(paddingValues),
            viewModel = viewModel,
            navigateToCalendarScreen = navigateToCalendarScreen
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun AnalyticsSectionContent(
    modifier: Modifier = Modifier,
    viewModel: AnalyticsViewModel,
    navigateToCalendarScreen: (DateSelection) -> Unit,
) {
    val spendingTransactions by viewModel.spendingTransactions.collectAsState()

    val incomeTransactions by viewModel.incomeTransactions.collectAsState()

    val pagerState = rememberPagerState(pageCount = { CategoryType.entries.size })

    Column(modifier = modifier.fillMaxWidth()) {
        Header(
            navigateToCalendarScreen = navigateToCalendarScreen,
            startDate = viewModel.startDate,
            endDate = viewModel.endDate
        )

        Box(
            Modifier
                .fillMaxWidth()
                .height(24.dp)
                .background(Color.White)
        )

        TabRow(pagerState = pagerState)

        HorizontalPager(
            state = pagerState,
        ) { page ->
            when (page) {
                0 -> {
                    SpendingChart(transactions = spendingTransactions)
                }

                1 -> {
                    IncomeChart(transactions = incomeTransactions)
                }
            }
        }
    }
}

@Composable
private fun Header(
    modifier: Modifier = Modifier,
    navigateToCalendarScreen: (DateSelection) -> Unit,
    startDate: LocalDate,
    endDate: LocalDate,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = Color.White)
            .padding(horizontal = 20.dp)
    ) {
        Text(
            text = stringResource(id = R.string.statistics),
            color = Color(0xFF262626),
            fontSize = 30.sp,
            lineHeight = 36.sp,
            fontWeight = FontWeight.W700,
            modifier = Modifier.weight(1f, true)
        )

        Spacer(modifier = Modifier.width(16.dp))

        DateButton(
            navigateToCalendarScreen = navigateToCalendarScreen,
            startDate = startDate,
            endDate = endDate
        )
    }
}

@Composable
private fun DateButton(
    modifier: Modifier = Modifier,
    navigateToCalendarScreen: (DateSelection) -> Unit,
    startDate: LocalDate,
    endDate: LocalDate,
) {
    Row(
        modifier = modifier
            .offset(y = 2.dp)
            .defaultMinSize(minHeight = 36.dp)
            .clip(shape = RoundedCornerShape(20.dp))
            .background(Color(0xFFF4F4F4))
            .indicationClickable {
                navigateToCalendarScreen(
                    DateSelection(
                        startDate = startDate,
                        endDate = endDate
                    )
                )
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        androidx.compose.material.Text(
            text = formatDateRange(startDate, endDate),
            color = Color(0xFF8589AF),
            fontSize = 16.sp,
            lineHeight = 24.sp,
            fontWeight = FontWeight.W400,
            modifier = Modifier
                .padding(start = 12.dp, top = 6.dp, bottom = 6.dp)
                .offset(y = (-1).dp),
        )

        Spacer(modifier = Modifier.width(6.dp))

        Icon(
            painter = painterResource(id = R.drawable.ic_arrow_down),
            contentDescription = null,
            tint = Color.Unspecified,
            modifier = Modifier
                .padding(end = 10.dp)
                .offset(y = 1.dp)
        )
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

@Composable
private fun LifecycleAwareComponent(
    getTransactions: () -> Unit,
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    val observer = remember {
        LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_START -> getTransactions()
                else -> {}
            }
        }
    }

    DisposableEffect(lifecycleOwner) {
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}