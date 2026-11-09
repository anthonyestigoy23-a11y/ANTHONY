package com.example.data.model

data class Motorcycle(
  val id: String,
  val model: String,
  val plateNumber: String,
  val type: String
)

data class ServiceProblem(
  val id: String,
  val title: String,
  val subtitle: String = ""
)

data class Provider(
  val id: String,
  val name: String,
  val rating: Double,
  val distanceEta: String,
  val isPriority: Boolean,
  val phone: String,
  val specialty: String
)

enum class RequestStatus(val label: String, val badgeCode: String) {
  PROCESSING("Processing", "P"),
  ACCEPTED("Accepted", "A"),
  COMPLETED("Completed", "C"),
  CANCELLED("Cancelled", "X")
}

data class AssistanceRequest(
  val id: Int,
  val title: String,
  val motorcycle: String,
  val requestTime: String,
  val location: String,
  val providerName: String,
  val providerPhone: String,
  val price: String,
  var status: RequestStatus,
  val isPriorityBoost: Boolean = false,
  val pickupDetails: String? = null,
  var rating: Int? = null,
  var feedback: String? = null
)

data class ChatMessage(
  val id: String,
  val isFromMe: Boolean,
  val text: String,
  val timeText: String
)

data class PmsPackage(
  val id: String,
  val title: String,
  val description: String,
  val price: String
)

data class PlusPlan(
  val id: String,
  val name: String,
  val price: String,
  val badge: String? = null,
  val benefits: List<String>
)
