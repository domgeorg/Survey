package com.georgiopoulos.core.domain.model.error.mapper

import com.georgiopoulos.core.domain.model.error.ErrorModel
import com.georgiopoulos.core.domain.model.error.NetworkErrorModel
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Test
import retrofit2.HttpException
import java.io.IOException
import java.net.HttpURLConnection

class ErrorMapperImplTest {

    private val errorMapper = ErrorMapperImpl()

    @Test
    fun `Given IOException, When mapping the error, Then it should map to NetworkErrorModel Network`() {
        val result = errorMapper.mapError(IOException())
        assertEquals(NetworkErrorModel.Network, result)
    }

    @Test
    fun `Given HttpException with 404, When mapping the error, Then it should map to NetworkErrorModel NotFound`() {
        val exception = createHttpException(HttpURLConnection.HTTP_NOT_FOUND)
        val result = errorMapper.mapError(exception)
        assertEquals(NetworkErrorModel.NotFound, result)
    }

    @Test
    fun `Given HttpException with 401, When mapping the error, Then it should map to NetworkErrorModel NotAuthorized`() {
        val exception = createHttpException(HttpURLConnection.HTTP_UNAUTHORIZED)
        val result = errorMapper.mapError(exception)
        assertEquals(NetworkErrorModel.NotAuthorized, result)
    }

    @Test
    fun `Given HttpException with 503, When mapping the error, Then it should map to NetworkErrorModel ServiceUnavailable`() {
        val exception = createHttpException(HttpURLConnection.HTTP_UNAVAILABLE)
        val result = errorMapper.mapError(exception)
        assertEquals(NetworkErrorModel.ServiceUnavailable, result)
    }

    @Test
    fun `Given HttpException with 500, When mapping the error, Then it should map to NetworkErrorModel ServiceNotWorking`() {
        val exception = createHttpException(HttpURLConnection.HTTP_INTERNAL_ERROR)
        val result = errorMapper.mapError(exception)
        assertEquals(NetworkErrorModel.ServiceNotWorking, result)
    }

    @Test
    fun `Given unknown exception, When mapping the error, Then it should map to ErrorDomainModel UnknownErrorModel`() {
        val exception = RuntimeException()
        val result = errorMapper.mapError(exception)
        assertEquals(ErrorModel.UnknownErrorModel, result)
    }

    private fun createHttpException(code: Int): HttpException {
        val exception: HttpException = mockk()
        every { exception.code() } returns code
        every { exception.message() } returns "Mocked exception message"
        return exception
    }
}