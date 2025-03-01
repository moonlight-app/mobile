package ru.moonlight.feature_catalog_product.impl.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import ru.moonlight.api.screen.ErrorScreen
import ru.moonlight.api.theme.MoonlightTheme
import ru.moonlight.api.widget.progressbar.ProgressBarWidget
import ru.moonlight.api.widget.text.DescriptionTextWidget
import ru.moonlight.common.base.BaseUIState
import ru.moonlight.feature_catalog_product.R
import ru.moonlight.feature_catalog_product.impl.presentation.ProductDetailsAction
import ru.moonlight.feature_catalog_product.impl.presentation.ProductDetailsSideEffect
import ru.moonlight.feature_catalog_product.impl.presentation.ProductDetailsViewModel
import ru.moonlight.feature_catalog_product.impl.ui.component.AuthorizeButton
import ru.moonlight.feature_catalog_product.impl.ui.component.CartButton
import ru.moonlight.feature_catalog_product.impl.ui.component.DescriptionCard
import ru.moonlight.feature_catalog_product.impl.ui.component.FavouritesButton
import ru.moonlight.feature_catalog_product.impl.ui.component.ParametrsCard
import ru.moonlight.feature_catalog_product.impl.ui.component.PriceText
import ru.moonlight.feature_catalog_product.impl.ui.component.ProductImage
import ru.moonlight.feature_catalog_product.impl.ui.component.SizesCard
import ru.moonlight.feature_catalog_product.impl.ui.component.TitleText
import ru.moonlight.feature_catalog_product.impl.ui.component.TopAppBar

@Composable
internal fun ProductDetailsRoute(
    onBackClick: () -> Unit,
    onCartClick: () -> Unit,
    onAuthClick: () -> Unit,
    isUserAuthorized: Boolean,
    productId: Long,
    modifier: Modifier = Modifier
) {
    val viewModel: ProductDetailsViewModel = hiltViewModel()

    val state by viewModel.collectAsState()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.dispatch(ProductDetailsAction.LoadDetails(productId))
    }

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is ProductDetailsSideEffect.NavigateToCart -> onCartClick()
            is ProductDetailsSideEffect.NavigateToAuthorize -> onAuthClick()
        }
    }

    when (uiState) {
        BaseUIState.Idle -> {}
        is BaseUIState.Error -> {
            ErrorScreen(
                onRepeatAttemptClick = {},
                errorMsg = (uiState as BaseUIState.Error).msg ?: ""
            )
        }
        BaseUIState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                ProgressBarWidget()
            }
        }
        BaseUIState.Success -> {
            ProductDetailsScreen(
                onBackClick = onBackClick,
                onSizeClick = { viewModel.dispatch(ProductDetailsAction.UpdateChosenSize(it)) },
                onFavouritesClick = { viewModel.dispatch(ProductDetailsAction.ChangeFavouritesStatus()) },
                onCartClick = { viewModel.dispatch(ProductDetailsAction.AddToCart()) },
                goToCartClick = {
                    viewModel.dispatch(ProductDetailsAction.GoToCartClick())
                },
                onAuthorizeClick = onAuthClick,
                isUserAuthorized = isUserAuthorized,
                imageUrl = state.imageUrl,
                title = state.title,
                price = state.price,
                sizes = state.sizes,
                material = state.material,
                standard = state.standard,
                typeOfStandard = state.typeOfStandard,
                insertion = state.insertion,
                weight = state.weight,
                article = state.article,
                description = state.description,
                choosenSize = state.choosenSize,
                isFavourite = state.isFavourite,
                modifier = modifier,
            )
        }
    }

}

@Composable
private fun ProductDetailsScreen(
    onBackClick: () -> Unit,
    onSizeClick: (String) -> Unit,
    onFavouritesClick: () -> Unit,
    onCartClick: () -> Unit,
    goToCartClick: () -> Unit,
    onAuthorizeClick: () -> Unit,
    imageUrl: String,
    title: String,
    price: String,
    sizes: List<String>,
    material: String,
    standard: String,
    typeOfStandard: String,
    insertion: String,
    weight: String,
    article: String,
    description: String,
    choosenSize: String?,
    isFavourite: Boolean,
    isUserAuthorized: Boolean,
    modifier: Modifier = Modifier,
) {
    val scrollState = rememberScrollState()
    var favouritesBtnVisible by remember { mutableStateOf(true) }
    val favouritesBtnWidth by animateFloatAsState(targetValue = if (favouritesBtnVisible) 0.5f else 0f, label = "favouritesSizeAnimation")
    val favouritesBtnAlpha by animateFloatAsState(targetValue = if (favouritesBtnVisible) 1f else 0f, label = "favouritesAlphaAnimation")


    val density = LocalDensity.current
    var heightForScroll by remember { mutableStateOf(0.dp) }

    Scaffold(
        modifier = modifier.statusBarsPadding(),
        containerColor = MoonlightTheme.colors.background,
        topBar = {
            TopAppBar(
                onBackClick = onBackClick,
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = MoonlightTheme.dimens.paddingBetweenComponentsHorizontal)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(
                MoonlightTheme.dimens.paddingBetweenComponentsMediumVertical,
            ),
        ) {
            val imageScrollOffset = scrollState.value.toFloat()
            val imageAlpha = 1f - (imageScrollOffset / 300).coerceIn(0f, 1f)

            ProductImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(175.dp, 225.dp)
                    .alpha(imageAlpha)
                    .clip(shape = MoonlightTheme.shapes.buttonShape)
                    .background(color = MoonlightTheme.colors.text),
                imageUrl = imageUrl,
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(
                    MoonlightTheme.dimens.paddingBetweenComponentsSmallVertical,
                ),
            ) {
                TitleText(text = title)
                PriceText(text = price)
            }

            SizesCard(
                modifier = Modifier,
                onSizeClick = onSizeClick,
                sizes = sizes,
                chosenSize = choosenSize ?: "",
            )

            ParametrsCard(
                modifier = Modifier,
                material = material,
                standard = standard,
                typeOfStandard = typeOfStandard,
                insertion = insertion,
                weight = weight,
                article = article,
            )

            DescriptionCard(description = description)

            Spacer(
                Modifier
                    .fillMaxWidth()
                    .height(heightForScroll + MoonlightTheme.dimens.paddingBetweenComponentsSmallVertical)
            )
        }
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = MoonlightTheme.colors.background.copy(alpha = 0.7f))
                    .padding(horizontal = MoonlightTheme.dimens.paddingBetweenComponentsHorizontal)
                    .padding(vertical = MoonlightTheme.dimens.paddingBetweenComponentsSmallVertical)
                    .navigationBarsPadding()
                    .onGloballyPositioned { layoutCoordinates ->
                        with(density) {
                            heightForScroll = layoutCoordinates.size.height.toDp()
                        }
                    },
                horizontalArrangement = Arrangement.spacedBy(
                    if (favouritesBtnVisible) MoonlightTheme.dimens.paddingBetweenComponentsHorizontal else 0.dp,
                    Alignment.CenterHorizontally,
                ),
            ) {

                if (isUserAuthorized) {
                    FavouritesButton(
                        modifier = Modifier
                            .animateContentSize(animationSpec = tween(250))
                            .alpha(favouritesBtnAlpha)
                            .fillMaxWidth(favouritesBtnWidth),
                        onFavouritesClick = onFavouritesClick,
                        isFavourite = isFavourite,
                    )

                    CartButton(
                        modifier = Modifier
                            .fillMaxWidth(),
                        onCartClick = {
                            onCartClick()
                            favouritesBtnVisible = false
                        },
                        goToCartClick = goToCartClick,
                        favouritesBtnVisible = favouritesBtnVisible,
                        enable = choosenSize != null || !favouritesBtnVisible || sizes.isEmpty(),
                    )
                } else {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(
                            space = MoonlightTheme.dimens.paddingBetweenComponentsSmallVertical
                        ),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        DescriptionTextWidget(
                            text = stringResource(R.string.toAddProductsInCartAuthrorize),
                            textAlign = TextAlign.Center,
                        )
                        AuthorizeButton(
                            modifier = Modifier
                                .fillMaxWidth(),
                            onAuthClick = onAuthorizeClick,
                        )
                    }

                }

            }
        }
    }
}