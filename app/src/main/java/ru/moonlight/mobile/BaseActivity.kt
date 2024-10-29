package ru.moonlight.mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import dagger.hilt.android.AndroidEntryPoint
import ru.moonlight.feature_auth.sign_in.navigation.navigateToSignIn
import ru.moonlight.feature_cart.navigation.navigateToCartScreen
import ru.moonlight.feature_catalog.navigation.navigateToCatalogScreen
import ru.moonlight.theme.MoonlightTheme

@AndroidEntryPoint
class BaseActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()

            MoonlightTheme {
                // Just a test, I'll delete it in the future update
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        TabView(
                            tabBarItems = listOf(
                                TabBarItem(
                                    onClick = {
                                        val navOpt = navOptions {
                                            popUpTo(navController.graph.findStartDestination().id) {
                                                saveState = true
                                            }
                                            // Avoid multiple copies of the same destination when
                                            // reselecting the same item
                                            launchSingleTop = true
                                            // Restore state when reselecting a previously selected item
                                            restoreState = true
                                        }
                                        navController.navigateToCatalogScreen(navOpt)
                                    },
                                    title = "Catalog",
                                    selectedIcon = painterResource(id = R.drawable.baseline_apps_outage_24),
                                    unselectedIcon = painterResource(id = R.drawable.baseline_apps_24),
                                ),
                                TabBarItem(
                                    onClick = {
                                        val navOpt = navOptions {
                                            popUpTo(navController.graph.findStartDestination().id) {
                                                saveState = true
                                            }
                                            // Avoid multiple copies of the same destination when
                                            // reselecting the same item
                                            launchSingleTop = true
                                            // Restore state when reselecting a previously selected item
                                            restoreState = true
                                        }
                                        navController.navigateToCartScreen(navOpt)
                                    },
                                    title = "Cart",
                                    selectedIcon = painterResource(id = R.drawable.baseline_apps_outage_24),
                                    unselectedIcon = painterResource(id = R.drawable.baseline_apps_24),
                                ),
                                TabBarItem(
                                    onClick = { navController.navigateToSignIn() },
                                    title = "Profile",
                                    selectedIcon = painterResource(id = R.drawable.baseline_apps_outage_24),
                                    unselectedIcon = painterResource(id = R.drawable.baseline_apps_24),
                                ),
                            ),
                        )
                    }
                ) { innerPadding ->
                    MoonlightNavHost(
                        modifier = Modifier.padding(innerPadding),
                        navController = navController
                    )
                }
            }
        }
    }

    private data class TabBarItem(
        val onClick: () -> Unit,
        val title: String,
        val selectedIcon: Painter,
        val unselectedIcon: Painter,
        val badgeAmount: Int? = null
    )

    @Composable
    private fun TabView(
        tabBarItems: List<TabBarItem>,
    ) {
        var selectedTabIndex by rememberSaveable {
            mutableStateOf(0)
        }

        NavigationBar {
            // looping over each tab to generate the views and navigation for each item
            tabBarItems.forEachIndexed { index, tabBarItem ->
                NavigationBarItem(
                    selected = selectedTabIndex == index,
                    onClick = {
                        selectedTabIndex = index
                        tabBarItem.onClick()
                    },
                    icon = {
                        TabBarIconView(
                            isSelected = selectedTabIndex == index,
                            selectedIcon = tabBarItem.selectedIcon,
                            unselectedIcon = tabBarItem.unselectedIcon,
                            title = tabBarItem.title,
                            badgeAmount = tabBarItem.badgeAmount
                        )
                    },
                    label = { Text(tabBarItem.title) })
            }
        }
    }

    @Composable
    private fun TabBarIconView(
        isSelected: Boolean,
        selectedIcon: Painter,
        unselectedIcon: Painter,
        title: String,
        badgeAmount: Int? = null
    ) {
        BadgedBox(badge = { TabBarBadgeView(badgeAmount) }) {
            Icon(
                painter = if (isSelected) selectedIcon else unselectedIcon,
                contentDescription = title
            )
        }
    }

    @Composable
    private fun TabBarBadgeView(count: Int? = null) {
        if (count != null) {
            Badge {
                Text(count.toString())
            }
        }
    }
}