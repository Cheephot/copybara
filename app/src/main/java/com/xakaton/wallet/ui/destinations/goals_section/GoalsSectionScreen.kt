package com.xakaton.wallet.ui.destinations.goals_section

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.result.NavResult
import com.ramcosta.composedestinations.result.ResultRecipient
import com.xakaton.budget.ui.destinations.AddGoalsBottomSheetDestination
import com.xakaton.wallet.R
import com.xakaton.wallet.domain.models.Goal
import com.xakaton.wallet.ui.destinations.add_goals.AddGoalsResult
import com.xakaton.wallet.ui.nav_graphs.GoalsSectionNavGraph
import com.xakaton.wallet.ui.utils.currencyFormatter
import com.xakaton.wallet.ui.utils.indicationClickable

@Composable
@Destination
@GoalsSectionNavGraph(start = true)
fun GoalsSectionScreen(
    resultRecipient: ResultRecipient<AddGoalsBottomSheetDestination, AddGoalsResult>,
    viewModel: GoalsSectionViewModel = hiltViewModel(),
) {
    resultRecipient.onNavResult { result ->
        when (result) {
            is NavResult.Canceled -> {}
            is NavResult.Value -> {
                viewModel.addGoals(result.value.name, result.value.sum.toBigDecimal())
            }
        }
    }

    GoalsSectionScreen(
        viewModel = viewModel
    )
}

@Composable
private fun GoalsSectionScreen(
    viewModel: GoalsSectionViewModel,
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        backgroundColor = Color.White,
        topBar = {
            TopAppBar()
        }
    ) { paddingValues ->
        Content(
            modifier = Modifier.padding(paddingValues),
            viewModel = viewModel
        )
    }
}

@Composable
private fun Content(
    modifier: Modifier = Modifier,
    viewModel: GoalsSectionViewModel,
) {
    val goals by viewModel.goals.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        androidx.compose.material3.Text(
            text = stringResource(id = R.string.goals),
            color = Color(0xFF262626),
            fontSize = 30.sp,
            lineHeight = 36.sp,
            fontWeight = FontWeight.W700,
            modifier = Modifier.padding(horizontal = 20.dp)
        )

        when {
            goals == null -> {}

            goals!!.isEmpty() -> {
                Spacer(modifier = Modifier.height(80.dp))

                EmptyContent()
            }

            else -> {
                Spacer(modifier = Modifier.height(16.dp))

                NonEmptyContent(goals = goals ?: listOf())
            }
        }
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
private fun EmptyContent(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = R.drawable.img_statistics,
            contentDescription = null,
            modifier = Modifier.size(280.dp)
        )

        Text(
            text = stringResource(id = R.string.not_goals),
            color = Color(0xFF262626),
            fontSize = 18.sp,
            lineHeight = 26.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.W600
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(id = R.string.add_first_goals),
            color = Color(0xFF262626),
            fontSize = 16.sp,
            lineHeight = 22.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.W400
        )
    }
}

@Composable
private fun NonEmptyContent(
    modifier: Modifier = Modifier,
    goals: List<Goal>,
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(goals) {
            Goal(goal = it)

            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
private fun Goal(
    modifier: Modifier = Modifier,
    goal: Goal,
) {
    Box(
        modifier = modifier
            .padding(horizontal = 20.dp)
            .fillMaxWidth()
            .defaultMinSize(minHeight = 92.dp)
            .clip(RoundedCornerShape(32.dp))
            .border(1.dp, Color(0xFFF4F4F4), RoundedCornerShape(32.dp))
            .indicationClickable { },
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 11.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "${
                    goal.currentAmount.toBigDecimal().currencyFormatter()
                } из ${goal.expectedAmount.toBigDecimal().currencyFormatter()}",
                color = Color(0xFF262626),
                fontSize = 17.sp,
                lineHeight = 25.sp,
                fontWeight = FontWeight.W700
            )

            Text(
                text = goal.name,
                color = Color(0xFF8589AF),
                fontSize = 15.sp,
                lineHeight = 20.sp,
                fontWeight = FontWeight.W400
            )
        }
    }
}