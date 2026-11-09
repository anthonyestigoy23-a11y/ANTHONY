package com.example.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.model.AssistanceRequest
import com.example.data.model.ChatMessage
import com.example.data.model.Motorcycle
import com.example.data.model.PlusPlan
import com.example.data.model.PmsPackage
import com.example.data.model.Provider
import com.example.data.model.ServiceProblem
import com.example.data.repository.RoadResQRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class Screen {
  SPLASH,
  AUTH,
  LOGIN,
  SIGNUP,
  MOTO_SELECT,
  HOME,
  PROBLEM_SELECT,
  LOCATION_SELECT,
  PROVIDER_SELECT,
  REVIEW_REQUEST,
  TRACKING,
  RATING,
  PMS_BOOKING,
  PLUS_PLANS,
  REQUESTS,
  CHAT,
  PROFILE
}

class RoadResQViewModel : ViewModel() {

  val repository = RoadResQRepository(viewModelScope)

  private val _screenStack = MutableStateFlow(listOf(Screen.SPLASH))
  val currentScreen: StateFlow<Screen> = MutableStateFlow(Screen.SPLASH).apply {
    viewModelScope.launch {
      _screenStack.collect { stack ->
        value = stack.lastOrNull() ?: Screen.SPLASH
      }
    }
  }

  // Back destination after selecting motorcycle
  var motoSelectReturnScreen: Screen = Screen.HOME

  // Whether location editing originated from Home or Assistance Flow
  var isLocationFromHome: Boolean = false

  // Selected Problem in flow
  private val _selectedProblem = MutableStateFlow("Flat tire")
  val selectedProblem: StateFlow<String> = _selectedProblem.asStateFlow()

  private val _otherProblemDescription = MutableStateFlow("")
  val otherProblemDescription: StateFlow<String> = _otherProblemDescription.asStateFlow()

  // Selected Provider index in flow
  private val _selectedProviderIndex = MutableStateFlow(0)
  val selectedProviderIndex: StateFlow<Int> = _selectedProviderIndex.asStateFlow()

  // Emergency SOS Dialog state
  private val _showSosDialog = MutableStateFlow(false)
  val showSosDialog: StateFlow<Boolean> = _showSosDialog.asStateFlow()

  // Cancel Request Dialog state
  private val _requestToCancel = MutableStateFlow<AssistanceRequest?>(null)
  val requestToCancel: StateFlow<AssistanceRequest?> = _requestToCancel.asStateFlow()

  // Toast message
  val toastEvent = repository.toastEvent

  val userName = repository.userName
  val userPhone = repository.userPhone
  val isLoggedIn = repository.isLoggedIn
  val motorcycles = repository.motorcycles
  val selectedMotorcycleIndex = repository.selectedMotorcycleIndex
  val userLocation = repository.userLocation
  val providers = repository.providers
  val problems = repository.problems
  val pmsPackages = repository.pmsPackages
  val plusPlans = repository.plusPlans
  val activePlanIndex = repository.activePlanIndex
  val priorityBoostActive = repository.priorityBoostActive
  val requests = repository.requests
  val activeRequestId = repository.activeRequestId
  val chatMessages = repository.chatMessages

  val currentMotorcycle: StateFlow<Motorcycle?> = MutableStateFlow<Motorcycle?>(null).apply {
    viewModelScope.launch {
      motorcycles.collect { list ->
        val idx = selectedMotorcycleIndex.value
        value = list.getOrNull(idx) ?: list.firstOrNull()
      }
    }
  }

  fun navigateTo(screen: Screen, clearStack: Boolean = false) {
    if (clearStack) {
      _screenStack.value = listOf(screen)
    } else {
      _screenStack.value = _screenStack.value + screen
    }
  }

  fun navigateBack(): Boolean {
    val current = _screenStack.value
    return if (current.size > 1) {
      _screenStack.value = current.dropLast(1)
      true
    } else {
      false
    }
  }

  fun selectTab(screen: Screen) {
    _screenStack.value = listOf(screen)
  }

  fun setProblem(problem: String) {
    _selectedProblem.value = problem
  }

  fun setOtherProblemDescription(text: String) {
    _otherProblemDescription.value = text
  }

  fun selectProvider(index: Int) {
    _selectedProviderIndex.value = index
  }

  fun togglePriorityBoost() {
    repository.setPriorityBoost(!priorityBoostActive.value)
  }

  fun showSos(show: Boolean) {
    _showSosDialog.value = show
  }

  fun showCancelDialog(request: AssistanceRequest?) {
    _requestToCancel.value = request
  }

  fun confirmCancelRequest() {
    val req = _requestToCancel.value ?: return
    repository.cancelRequest(req.id)
    _requestToCancel.value = null
  }

  fun submitAssistanceRequest() {
    val problemTitle = if (_selectedProblem.value == "Other") {
      val desc = _otherProblemDescription.value.trim()
      if (desc.isNotEmpty()) "Other: $desc" else "Other issue"
    } else {
      _selectedProblem.value
    }
    val provider = providers.getOrElse(_selectedProviderIndex.value) { providers.first() }
    val loc = userLocation.value
    val isBoost = priorityBoostActive.value

    val newReq = repository.createAssistanceRequest(
      problemTitle = problemTitle,
      provider = provider,
      location = loc,
      isPriorityBoost = isBoost
    )

    // Navigate to Tracking
    navigateTo(Screen.TRACKING)
  }

  fun openChatForProvider() {
    navigateTo(Screen.CHAT)
  }

  fun sendChat(text: String) {
    val activeReq = requests.value.firstOrNull { it.id == activeRequestId.value }
    val provName = activeReq?.providerName ?: providers.first().name
    repository.sendChatMessage(text, provName)
  }

  fun completeCurrentService(rating: Int, feedback: String?) {
    val reqId = activeRequestId.value ?: return
    repository.completeService(reqId, rating, feedback)
    navigateTo(Screen.HOME, clearStack = true)
  }

  fun bookPms(
    pmsPackage: PmsPackage,
    date: String,
    time: String,
    pickupEnabled: Boolean,
    pickupAddress: String,
    pickupTime: String
  ) {
    repository.bookPms(
      pmsPackage = pmsPackage,
      date = date,
      time = time,
      pickupEnabled = pickupEnabled,
      pickupAddress = pickupAddress,
      pickupTime = pickupTime
    )
    navigateTo(Screen.REQUESTS)
  }
}
