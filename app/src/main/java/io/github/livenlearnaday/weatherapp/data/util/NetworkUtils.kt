package io.github.livenlearnaday.data.util

import io.github.livenlearnaday.weatherapp.data.models.ErrorResponseDto
import io.github.livenlearnaday.weatherapp.domain.CheckResult
import io.github.livenlearnaday.weatherapp.domain.DataError
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.serialization.JsonConvertException
import io.ktor.util.network.UnresolvedAddressException
import java.util.concurrent.CancellationException
import kotlinx.serialization.SerializationException
import timber.log.Timber

suspend inline fun <reified D> safeApiRequest(block: () -> HttpResponse): CheckResult<D, DataError.Network, ErrorResponseDto> {
    val httpResponse = try {
        block()
    } catch (exception: UnresolvedAddressException) {
        exception.printStackTrace()

        return CheckResult.Failure(DataError.Network.NO_INTERNET)
    } catch (exception: JsonConvertException) {
        exception.printStackTrace()

        return CheckResult.Failure(DataError.Network.SERIALIZATION)
    } catch (exception: SerializationException) {
        exception.printStackTrace()

        return CheckResult.Failure(DataError.Network.SERIALIZATION)
    } catch (exception: Exception) {
        exception.printStackTrace()

        if (exception is CancellationException) {
            Timber.e(exception)
            throw exception
        }
        return CheckResult.Failure(DataError.Network.UNKNOWN)
    }

    return responseToResult(httpResponse)
}

suspend inline fun <reified D> responseToResult(response: HttpResponse): CheckResult<D, DataError.Network, ErrorResponseDto> = when (response.status.value) {
    in 200..299 -> {
        CheckResult.Success(response.body<D>())
    }

    401 -> {
        CheckResult.Failure(DataError.Network.UNAUTHORIZED)
    }

    408 -> {
        CheckResult.Failure(DataError.Network.REQUEST_TIMEOUT)
    }

    409 -> {
        CheckResult.Failure(DataError.Network.CONFLICT)
    }

    413 -> {
        CheckResult.Failure(DataError.Network.PAYLOAD_TOO_LARGE)
    }

    429 -> {
        CheckResult.Failure(DataError.Network.TOO_MANY_REQUESTS)
    }

    in 500..599 -> {
        CheckResult.Failure(DataError.Network.SERVER_ERROR)
    }

    else -> {
        CheckResult.Failure(DataError.Network.UNKNOWN)
    }
}
