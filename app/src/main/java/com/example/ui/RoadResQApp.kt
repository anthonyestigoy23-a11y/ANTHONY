package com.example.ui

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.model.RequestStatus
import com.example.ui.components.CancelDialog
import com.example.ui.components.RoadResQBottomBar
import com.example.ui.components.SosDialog
import com.example.ui.screens.AuthScreen
import com.example.ui.screens.ChatScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.LocationSelectScreen
import com.example.ui.screens.LoginScreen
import com.example.ui.screens.MotorcycleSelectScreen
import com.example.ui.screens.PlusPlansScreen
import com.example.ui.screens.PmsBookingScreen
import com.example.ui.screens.ProblemSelectScreen
import com.example.ui.screens.ProfileScreen
import com.example.ui.screens.ProviderSelectScreen
import com.example.ui.screens.RatingScreen
import com.example.ui.screens.RequestsScreen
import com.example.ui.screens.ReviewRequestScreen
import com.example.ui.screens.SignupScreen
import com.example.ui.screens.SplashScreen
import com.example.ui.screens.TrackingScreen
import kotlinx.coroutines.flow.collectLatest

@Composable
fun RoadResQApp(
  viewModel: RoadResQViewModel = viewModel()
) {
  val currentScreen by viewModel.currentScreen.collectAsState()
  val userName by viewModel.userName.collectAsState()
  val userPhone by viewModel.userPhone.collectAsState()
  val motorcycles by viewModel.motorcycles.collectAsState()
  val selectedMotoIndex by viewModel.selectedMotorcycleIndex.collectAsState()
  val currentMoto by viewModel.currentMotorcycle.collectAsState()
  val userLocation by viewModel.userLocation.collectAsState()
  val selectedProblem by viewModel.selectedProblem.collectAsState()
  val otherDescription by viewModel.otherProblemDescription.collectAsState()
  val selectedProviderIndex by viewModel.selectedProviderIndex.collectAsState()
  val priorityBoostActive by viewModel.priorityBoostActive.collectAsState()
  val requests by viewModel.requests.collectAsState()
  val activeRequestId by viewModel.activeRequestId.collectAsState()
  val chatMessages by viewModel.chatMessages.collectAsState()
  val plusPlans = viewModel.plusPlans
  val activePlanIndex by viewModel.activePlanIndex.collectAsState()
  val pmsPackages = viewModel.pmsPackages
  val providers = viewModel.providers

  val showSosDialog by viewModel.showSosDialog.collectAsState()
  val requestToCancel by viewModel.requestToCancel.collectAsState()

  val snackbarHostState = remember { SnackbarHostState() }

  LaunchedEffect(Unit) {
    viewModel.toastEvent.collectLatest { msg ->
      snackbarHostState.showSnackbar(
        message = msg,
        duration = SnackbarDuration.Short
      )
    }
  }

  // Handle system back button for sub-screens
  if (currentScreen != Screen.SPLASH && currentScreen != Screen.HOME) {
    BackHandler {
      if (currentScreen == Screen.REQUESTS || currentScreen == Screen.CHAT || currentScreen == Screen.PROFILE) {
        viewModel.selectTab(Screen.HOME)
      } else {
        viewModel.navigateBack()
      }
    }
  }

  val isBottomBarVisible = currentScreen == Screen.HOME ||
    currentScreen == Screen.REQUESTS ||
    currentScreen == Screen.CHAT ||
    currentScreen == Screen.PROFILE

  Scaffold(
    snackbarHost = { SnackbarHost(snackbarHostState) },
    bottomBar = {
      if (isBottomBarVisible) {
        RoadResQBottomBar(
          currentScreen = currentScreen,
          onSelectTab = { target ->
            viewModel.selectTab(target)
          }
        )
      }
    }
  ) { innerPadding ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
    ) {
      when (currentScreen) {
        Screen.SPLASH -> {
          SplashScreen(
            onGetStarted = {
              viewModel.navigateTo(Screen.AUTH)
            }
          )
        }

        Screen.AUTH -> {
          AuthScreen(
            onBack = { viewModel.navigateBack() },
            onCreateAccount = { viewModel.navigateTo(Screen.SIGNUP) },
            onLogin = { viewModel.navigateTo(Screen.LOGIN) },
            onSosClick = { viewModel.showSos(true) }
          )
        }

        Screen.LOGIN -> {
          LoginScreen(
            onBack = { viewModel.navigateBack() },
            onLoginSuccess = { phone ->
              viewModel.repository.setLoggedIn(true)
              viewModel.motoSelectReturnScreen = Screen.HOME
              viewModel.navigateTo(Screen.MOTO_SELECT)
            },
            onGoToSignup = { viewModel.navigateTo(Screen.SIGNUP) },
            onSosClick = { viewModel.showSos(true) }
          )
        }

        Screen.SIGNUP -> {
          SignupScreen(
            onBack = { viewModel.navigateBack() },
            onSignupSuccess = { name, phone ->
              viewModel.repository.setLoggedIn(true)
              viewModel.repository.updateProfile(name, phone)
              viewModel.motoSelectReturnScreen = Screen.HOME
              viewModel.navigateTo(Screen.MOTO_SELECT)
            },
            onSosClick = { viewModel.showSos(true) }
          )
        }

        Screen.MOTO_SELECT -> {
          MotorcycleSelectScreen(
            motorcycles = motorcycles,
            selectedIndex = selectedMotoIndex,
            onSelectIndex = { viewModel.repository.selectMotorcycle(it) },
            onAddMotorcycle = { model, plate, type ->
              viewModel.repository.addMotorcycle(model, plate, type)
            },
            onContinue = {
              viewModel.navigateTo(viewModel.motoSelectReturnScreen, clearStack = true)
            },
            onBack = { viewModel.navigateBack() },
            onSosClick = { viewModel.showSos(true) }
          )
        }

        Screen.HOME -> {
          HomeScreen(
            motorcycle = currentMoto,
            location = userLocation,
            onEditLocation = {
              viewModel.isLocationFromHome = true
              viewModel.navigateTo(Screen.LOCATION_SELECT)
            },
            onChangeMotorcycle = {
              viewModel.motoSelectReturnScreen = Screen.HOME
              viewModel.navigateTo(Screen.MOTO_SELECT)
            },
            onRequestAssistance = {
              viewModel.navigateTo(Screen.PROBLEM_SELECT)
            },
            onQuickService = { problem ->
              viewModel.setProblem(problem)
              viewModel.isLocationFromHome = false
              viewModel.navigateTo(Screen.LOCATION_SELECT)
            },
            onOpenPms = {
              viewModel.navigateTo(Screen.PMS_BOOKING)
            },
            onOpenPlus = {
              viewModel.navigateTo(Screen.PLUS_PLANS)
            },
            onSosClick = { viewModel.showSos(true) }
          )
        }

        Screen.PROBLEM_SELECT -> {
          ProblemSelectScreen(
            selectedProblem = selectedProblem,
            otherDescription = otherDescription,
            onSelectProblem = { viewModel.setProblem(it) },
            onChangeOtherDescription = { viewModel.setOtherProblemDescription(it) },
            onNext = {
              viewModel.isLocationFromHome = false
              viewModel.navigateTo(Screen.LOCATION_SELECT)
            },
            onBack = { viewModel.navigateBack() },
            onSosClick = { viewModel.showSos(true) }
          )
        }

        Screen.LOCATION_SELECT -> {
          LocationSelectScreen(
            currentLocation = userLocation,
            isFromHome = viewModel.isLocationFromHome,
            onSaveLocation = { loc ->
              viewModel.repository.setLocation(loc)
              if (viewModel.isLocationFromHome) {
                viewModel.navigateBack()
              } else {
                viewModel.navigateTo(Screen.PROVIDER_SELECT)
              }
            },
            onBack = { viewModel.navigateBack() },
            onSosClick = { viewModel.showSos(true) }
          )
        }

        Screen.PROVIDER_SELECT -> {
          ProviderSelectScreen(
            providers = providers,
            selectedProviderIndex = selectedProviderIndex,
            userLocation = userLocation,
            onSelectProvider = { viewModel.selectProvider(it) },
            onReviewRequest = {
              viewModel.navigateTo(Screen.REVIEW_REQUEST)
            },
            onBack = { viewModel.navigateBack() },
            onSosClick = { viewModel.showSos(true) }
          )
        }

        Screen.REVIEW_REQUEST -> {
          val activeProv = providers.getOrElse(selectedProviderIndex) { providers.first() }
          val problemDisplay = if (selectedProblem == "Other") {
            if (otherDescription.isNotBlank()) "Other: $otherDescription" else "Other issue"
          } else {
            selectedProblem
          }

          ReviewRequestScreen(
            motorcycle = currentMoto,
            problemText = problemDisplay,
            location = userLocation,
            provider = activeProv,
            priorityBoostActive = priorityBoostActive,
            onTogglePriorityBoost = { viewModel.togglePriorityBoost() },
            onEditMotorcycle = {
              viewModel.motoSelectReturnScreen = Screen.REVIEW_REQUEST
              viewModel.navigateTo(Screen.MOTO_SELECT)
            },
            onEditProblem = { viewModel.navigateTo(Screen.PROBLEM_SELECT) },
            onEditLocation = {
              viewModel.isLocationFromHome = false
              viewModel.navigateTo(Screen.LOCATION_SELECT)
            },
            onEditProvider = { viewModel.navigateTo(Screen.PROVIDER_SELECT) },
            onOpenPlus = { viewModel.navigateTo(Screen.PLUS_PLANS) },
            onConfirmRequest = {
              viewModel.submitAssistanceRequest()
            },
            onBack = { viewModel.navigateBack() },
            onSosClick = { viewModel.showSos(true) }
          )
        }

        Screen.TRACKING -> {
          val currentReq = requests.firstOrNull { it.id == activeRequestId } ?: requests.firstOrNull()
          val prov = providers.firstOrNull { it.name == currentReq?.providerName } ?: providers.first()

          TrackingScreen(
            request = currentReq,
            provider = prov,
            onOpenChat = {
              viewModel.openChatForProvider()
            },
            onRequestCancel = { req ->
              viewModel.showCancelDialog(req)
            },
            onServiceCompleted = {
              viewModel.navigateTo(Screen.RATING)
            },
            onBack = { viewModel.navigateTo(Screen.HOME) },
            onSosClick = { viewModel.showSos(true) }
          )
        }

        Screen.RATING -> {
          RatingScreen(
            onBack = { viewModel.navigateBack() },
            onSubmitRating = { stars, notes ->
              viewModel.completeCurrentService(stars, notes)
            },
            onSosClick = { viewModel.showSos(true) }
          )
        }

        Screen.PMS_BOOKING -> {
          PmsBookingScreen(
            packages = pmsPackages,
            onConfirmBooking = { pkg, date, time, puEnabled, puAddr, puTime ->
              viewModel.bookPms(pkg, date, time, puEnabled, puAddr, puTime)
            },
            onBack = { viewModel.navigateBack() },
            onSosClick = { viewModel.showSos(true) }
          )
        }

        Screen.PLUS_PLANS -> {
          PlusPlansScreen(
            plans = plusPlans,
            selectedPlanIndex = activePlanIndex,
            onSelectPlan = { viewModel.repository.selectPlan(it) },
            onConfirmPlan = {
              viewModel.repository.selectPlan(it)
              viewModel.navigateTo(Screen.HOME)
            },
            onBack = { viewModel.navigateBack() },
            onSosClick = { viewModel.showSos(true) }
          )
        }

        Screen.REQUESTS -> {
          RequestsScreen(
            requests = requests,
            onViewTracking = { reqId ->
              viewModel.repository.setActiveRequest(reqId)
              viewModel.navigateTo(Screen.TRACKING)
            },
            onRequestCancel = { req ->
              viewModel.showCancelDialog(req)
            },
            onBack = { viewModel.selectTab(Screen.HOME) },
            onSosClick = { viewModel.showSos(true) }
          )
        }

        Screen.CHAT -> {
          val currentReq = requests.firstOrNull { it.id == activeRequestId }
          val provName = currentReq?.providerName ?: providers.first().name

          ChatScreen(
            providerName = provName,
            messages = chatMessages,
            onSendMessage = { text ->
              viewModel.sendChat(text)
            },
            onBack = {
              if (activeRequestId != null) {
                viewModel.navigateTo(Screen.TRACKING)
              } else {
                viewModel.selectTab(Screen.HOME)
              }
            },
            onSosClick = { viewModel.showSos(true) }
          )
        }

        Screen.PROFILE -> {
          ProfileScreen(
            userName = userName,
            userPhone = userPhone,
            motorcycles = motorcycles,
            onUpdateProfile = { name, phone ->
              viewModel.repository.updateProfile(name, phone)
            },
            onAddMotorcycleClick = {
              viewModel.motoSelectReturnScreen = Screen.PROFILE
              viewModel.navigateTo(Screen.MOTO_SELECT)
            },
            onLogout = {
              viewModel.repository.setLoggedIn(false)
              viewModel.navigateTo(Screen.AUTH, clearStack = true)
            },
            onBack = { viewModel.selectTab(Screen.HOME) },
            onSosClick = { viewModel.showSos(true) }
          )
        }
      }
    }
  }

  // SOS Emergency Dialog
  if (showSosDialog) {
    SosDialog(
      currentLocation = userLocation,
      onDismiss = { viewModel.showSos(false) }
    )
  }

  // Cancel Request Dialog
  if (requestToCancel != null) {
    CancelDialog(
      onKeepRequest = { viewModel.showCancelDialog(null) },
      onConfirmCancel = { viewModel.confirmCancelRequest() }
    )
  }
}
