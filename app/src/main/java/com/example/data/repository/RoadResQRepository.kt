package com.example.data.repository

import com.example.data.model.AssistanceRequest
import com.example.data.model.ChatMessage
import com.example.data.model.Motorcycle
import com.example.data.model.PlusPlan
import com.example.data.model.PmsPackage
import com.example.data.model.Provider
import com.example.data.model.RequestStatus
import com.example.data.model.ServiceProblem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class RoadResQRepository(private val coroutineScope: CoroutineScope) {

  private val _userName = MutableStateFlow("Joel Dela Cruz")
  val userName: StateFlow<String> = _userName.asStateFlow()

  private val _userPhone = MutableStateFlow("0917 123 4567")
  val userPhone: StateFlow<String> = _userPhone.asStateFlow()

  private val _isLoggedIn = MutableStateFlow(false)
  val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()

  private val _motorcycles = MutableStateFlow(
    listOf(
      Motorcycle("1", "Honda Click 125", "TLC 4821", "Motorcycle"),
      Motorcycle("2", "Honda TMX 125 + sidecar", "TRY 7733", "Tricycle (sidecar)"),
      Motorcycle("3", "Yamaha Mio", "TAB 1290", "Motorcycle")
    )
  )
  val motorcycles: StateFlow<List<Motorcycle>> = _motorcycles.asStateFlow()

  private val _selectedMotorcycleIndex = MutableStateFlow(0)
  val selectedMotorcycleIndex: StateFlow<Int> = _selectedMotorcycleIndex.asStateFlow()

  private val _userLocation = MutableStateFlow("McArthur Highway, Tarlac City")
  val userLocation: StateFlow<String> = _userLocation.asStateFlow()

  val providers: List<Provider> = listOf(
    Provider("1", "John Moto Care", 4.6, "0.8 km · 6 min", true, "0917 123 4567", "Mechanic"),
    Provider("2", "Joel Garage", 4.0, "1.5 km · 10 min", false, "0918 234 5678", "Mechanic"),
    Provider("3", "Tarlac Vulcanizing", 4.4, "2.1 km · 14 min", false, "0920 345 6789", "Vulcanizing")
  )

  val problems: List<ServiceProblem> = listOf(
    ServiceProblem("1", "Flat tire"),
    ServiceProblem("2", "Dead battery"),
    ServiceProblem("3", "Out of fuel"),
    ServiceProblem("4", "Won't start"),
    ServiceProblem("5", "Towing"),
    ServiceProblem("6", "Other")
  )

  val pmsPackages: List<PmsPackage> = listOf(
    PmsPackage("1", "PMS Basic", "Oil, filter, brake check", "₱350"),
    PmsPackage("2", "Full PMS", "+ CVT clean, spark plug, chain", "₱750"),
    PmsPackage("3", "Condition Check", "Health report with photos", "₱200")
  )

  val plusPlans: List<PlusPlan> = listOf(
    PlusPlan(
      id = "1",
      name = "Free",
      price = "₱0",
      benefits = listOf(
        "Standard matching",
        "SOS and accident help",
        "Request history and receipts"
      )
    ),
    PlusPlan(
      id = "2",
      name = "Priority Boost",
      price = "₱30–50 per request",
      benefits = listOf(
        "Nearest providers are alerted first",
        "Shorter wait for a provider to accept",
        "Pay only when you need it",
        "Safety help stays free for everyone"
      )
    ),
    PlusPlan(
      id = "3",
      name = "PLUS",
      price = "₱99 per month",
      badge = "BEST VALUE",
      benefits = listOf(
        "2 free Priority Boosts a month",
        "10% off PMS and home service",
        "Free motorcycle health check every quarter",
        "Free towing up to 3 km a month",
        "Priority customer support"
      )
    )
  )

  private val _activePlanIndex = MutableStateFlow(0)
  val activePlanIndex: StateFlow<Int> = _activePlanIndex.asStateFlow()

  private val _priorityBoostActive = MutableStateFlow(false)
  val priorityBoostActive: StateFlow<Boolean> = _priorityBoostActive.asStateFlow()

  private var nextRequestId = 2

  private val _requests = MutableStateFlow(
    listOf(
      AssistanceRequest(
        id = 1,
        title = "Full PMS",
        motorcycle = "Honda Click 125",
        requestTime = "Oct 02, 10:00 AM",
        location = "Tarlac City Center",
        providerName = "Joel Garage",
        providerPhone = "0918 234 5678",
        price = "₱750",
        status = RequestStatus.COMPLETED,
        isPriorityBoost = false
      )
    )
  )
  val requests: StateFlow<List<AssistanceRequest>> = _requests.asStateFlow()

  private val _activeRequestId = MutableStateFlow<Int?>(null)
  val activeRequestId: StateFlow<Int?> = _activeRequestId.asStateFlow()

  private val _chatMessages = MutableStateFlow(
    listOf(
      ChatMessage("1", false, "Hi! I received your request. I'm on my way.", "Just now")
    )
  )
  val chatMessages: StateFlow<List<ChatMessage>> = _chatMessages.asStateFlow()

  private val _toastEvent = MutableSharedFlow<String>()
  val toastEvent: SharedFlow<String> = _toastEvent.asSharedFlow()

  fun emitToast(message: String) {
    coroutineScope.launch {
      _toastEvent.emit(message)
    }
  }

  fun setLoggedIn(loggedIn: Boolean) {
    _isLoggedIn.value = loggedIn
  }

  fun updateProfile(name: String, phone: String) {
    _userName.value = name
    _userPhone.value = phone
    emitToast("Profile saved")
  }

  fun selectMotorcycle(index: Int) {
    if (index in _motorcycles.value.indices) {
      _selectedMotorcycleIndex.value = index
    }
  }

  fun addMotorcycle(model: String, plate: String, type: String) {
    val newId = (_motorcycles.value.size + 1).toString()
    val updated = _motorcycles.value + Motorcycle(newId, model, plate, type)
    _motorcycles.value = updated
    _selectedMotorcycleIndex.value = updated.lastIndex
    emitToast("Motorcycle added")
  }

  fun setLocation(location: String) {
    _userLocation.value = location
    emitToast("Location saved")
  }

  fun setPriorityBoost(enabled: Boolean) {
    _priorityBoostActive.value = enabled
  }

  fun selectPlan(index: Int) {
    _activePlanIndex.value = index
    if (index == 1) {
      _priorityBoostActive.value = true
    }
    val message = when (index) {
      0 -> "Free plan active"
      1 -> "Priority Boost is on for your next request"
      else -> "PLUS activated (simulated)"
    }
    emitToast(message)
  }

  fun createAssistanceRequest(
    problemTitle: String,
    provider: Provider,
    location: String,
    isPriorityBoost: Boolean
  ): AssistanceRequest {
    val reqId = nextRequestId++
    val currentMoto = _motorcycles.value.getOrNull(_selectedMotorcycleIndex.value)?.model ?: "Motorcycle"
    val basePrice = 250
    val boostPrice = if (isPriorityBoost) 40 else 0
    val finalPrice = "₱${basePrice + boostPrice}" + (if (isPriorityBoost) " · Priority Boost" else "")

    val newRequest = AssistanceRequest(
      id = reqId,
      title = problemTitle,
      motorcycle = currentMoto,
      requestTime = "Now (emergency)",
      location = location.ifBlank { _userLocation.value },
      providerName = provider.name,
      providerPhone = provider.phone,
      price = finalPrice,
      status = RequestStatus.PROCESSING,
      isPriorityBoost = isPriorityBoost || provider.isPriority
    )

    _requests.update { listOf(newRequest) + it }
    _activeRequestId.value = reqId

    // Simulate provider response progression
    coroutineScope.launch {
      delay(6000)
      _requests.update { list ->
        list.map { r ->
          if (r.id == reqId && r.status == RequestStatus.PROCESSING) {
            r.copy(status = RequestStatus.ACCEPTED)
          } else r
        }
      }
      emitToast("${provider.name} accepted your request")
    }

    return newRequest
  }

  fun bookPms(
    pmsPackage: PmsPackage,
    date: String,
    time: String,
    pickupEnabled: Boolean,
    pickupAddress: String,
    pickupTime: String
  ) {
    val reqId = nextRequestId++
    val currentMoto = _motorcycles.value.getOrNull(_selectedMotorcycleIndex.value)?.model ?: "Motorcycle"
    val provider = providers.getOrElse(1) { providers.first() }

    val pickupInfo = if (pickupEnabled) "Pick-up $pickupTime at $pickupAddress" else null

    val newRequest = AssistanceRequest(
      id = reqId,
      title = "${pmsPackage.title} (Home Service)",
      motorcycle = currentMoto,
      requestTime = "$date, $time",
      location = if (pickupEnabled) pickupAddress else _userLocation.value,
      providerName = provider.name,
      providerPhone = provider.phone,
      price = pmsPackage.price,
      status = RequestStatus.ACCEPTED,
      pickupDetails = pickupInfo
    )

    _requests.update { listOf(newRequest) + it }
    emitToast("Saved to My Requests")
  }

  fun cancelRequest(requestId: Int) {
    _requests.update { list ->
      list.map { r ->
        if (r.id == requestId && r.status == RequestStatus.PROCESSING) {
          r.copy(status = RequestStatus.CANCELLED)
        } else r
      }
    }
    emitToast("Request cancelled")
  }

  fun completeService(requestId: Int, rating: Int, feedback: String?) {
    _requests.update { list ->
      list.map { r ->
        if (r.id == requestId) {
          r.copy(status = RequestStatus.COMPLETED, rating = rating, feedback = feedback)
        } else r
      }
    }
    emitToast("Thank you! Receipt saved.")
  }

  fun setActiveRequest(requestId: Int) {
    _activeRequestId.value = requestId
  }

  fun sendChatMessage(text: String, providerName: String) {
    if (text.isBlank()) return
    val sdf = SimpleDateFormat("h:mm a", Locale.getDefault())
    val now = sdf.format(Date())

    val userMsg = ChatMessage(
      id = System.currentTimeMillis().toString(),
      isFromMe = true,
      text = text,
      timeText = now
    )
    _chatMessages.update { it + userMsg }

    // Automated provider response simulation
    coroutineScope.launch {
      delay(1200)
      val replyTexts = listOf(
        "Noted. About 5 minutes away.",
        "I have the tools and parts ready.",
        "Turning into your street now. Look for the blue service moto.",
        "Keep your phone near you. I'm arriving shortly."
      )
      val reply = replyTexts.random()
      val provMsg = ChatMessage(
        id = (System.currentTimeMillis() + 1).toString(),
        isFromMe = false,
        text = reply,
        timeText = sdf.format(Date())
      )
      _chatMessages.update { it + provMsg }
    }
  }
}
