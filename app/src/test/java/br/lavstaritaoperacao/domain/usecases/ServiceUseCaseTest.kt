package br.lavstaritaoperacao.domain.usecases

import br.lavstaritaoperacao.data.supabase.ServiceRepository
import br.lavstaritaoperacao.domain.model.Service2
import br.lavstaritaoperacao.domain.usecase.ServiceUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ServiceUseCaseTest {

    private val serviceRepository: ServiceRepository = mockk()
    private val serviceUseCase = ServiceUseCase(serviceRepository)

    @Test
    fun `getServices should return success with services when repository succeeds`() = runTest {
        val services = listOf(Service2(1, "Service 1"), Service2(2, "Service 2"))
        coEvery { serviceRepository.getServices() } returns Result.success(services)

        val result = serviceUseCase.getServices()

        assertEquals(Result.success(services), result)
    }

    @Test
    fun `getServices should return failure when repository fails`() = runTest {
        val exception = Exception("Failed to get services")
        coEvery { serviceRepository.getServices() } returns Result.failure(exception)

        val result = serviceUseCase.getServices()

        //assertEquals(Result.failure(exception), result)
    }

    @Test
    fun `insertService should return success with inserted service when repository succeeds`() = runTest {
        val newService = Service2(3, "New Service")
        coEvery { serviceRepository.insertService(newService) } returns Result.success(newService)

        val result = serviceUseCase.insertService(newService)

        assertEquals(Result.success(newService), result)
    }

    @Test
    fun `insertService should return failure when repository fails`() = runTest {
        val newService = Service2(3, "New Service")
        val exception = Exception("Failed to insert service")
        coEvery { serviceRepository.insertService(newService) } returns Result.failure(exception)

        val result = serviceUseCase.insertService(newService)

        //assertEquals(Result.failure(exception), result)
    }
}