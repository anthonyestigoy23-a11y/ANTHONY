package com.example

import com.example.data.model.PmsPackage
import com.example.data.model.Provider
import com.example.data.model.RequestStatus
import com.example.data.repository.RoadResQRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RoadResQRepositoryTest {

  private val testDispatcher = StandardTestDispatcher()
  private val testScope = TestScope(testDispatcher)

  @Test
  fun testCreateAssistanceRequest_withPriorityBoost() = runTest {
    val repository = RoadResQRepository(testScope)
    val provider = Provider("1", "John Moto Care", 4.6, "0.8 km · 6 min", true, "0917 123 4567", "Mechanic")

    val request = repository.createAssistanceRequest(
      problemTitle = "Flat tire",
      provider = provider,
      location = "McArthur Highway",
      isPriorityBoost = true
    )

    assertEquals("Flat tire", request.title)
    assertEquals(RequestStatus.PROCESSING, request.status)
    assertTrue(request.isPriorityBoost)
    assertTrue(request.price.contains("290"))
    assertTrue(request.price.contains("Priority Boost"))
  }

  @Test
  fun testAddMotorcycle() = runTest {
    val repository = RoadResQRepository(testScope)
    val initialSize = repository.motorcycles.value.size

    repository.addMotorcycle("Kawasaki Barako", "XYZ 987", "Motorcycle")

    val updatedList = repository.motorcycles.value
    assertEquals(initialSize + 1, updatedList.size)
    assertEquals("Kawasaki Barako", updatedList.last().model)
    assertEquals("XYZ 987", updatedList.last().plateNumber)
  }

  @Test
  fun testBookPmsService() = runTest {
    val repository = RoadResQRepository(testScope)
    val pkg = PmsPackage("1", "PMS Basic", "Oil, filter, brake check", "₱350")

    repository.bookPms(
      pmsPackage = pkg,
      date = "2026-11-10",
      time = "10:00 AM",
      pickupEnabled = true,
      pickupAddress = "Tarlac City Proper",
      pickupTime = "08:30 AM"
    )

    val requests = repository.requests.value
    val booked = requests.firstOrNull { it.title.contains("PMS Basic") }
    assertNotNull(booked)
    assertEquals(RequestStatus.ACCEPTED, booked?.status)
    assertTrue(booked?.pickupDetails?.contains("Tarlac City Proper") == true)
  }
}
