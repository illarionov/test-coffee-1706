package com.example.coffe1706.data.coffee1706api.session

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import com.example.coffe1706.core.model.auth.AuthTokenId
import com.example.coffe1706.data.coffee1706api.datasource.dto.AuthTokenDto
import com.example.coffe1706.data.coffee1706api.datasource.dto.copy
import com.google.protobuf.InvalidProtocolBufferException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import java.io.InputStream
import java.io.OutputStream

public class DataStoreCoffee1706SessionDataSource(
    private val dataStore: DataStore<AuthTokenDto>
) : Coffee1706SessionDataSource {
    override val tokenFlow: Flow<AuthTokenId?> = dataStore.data.map { dto: AuthTokenDto ->
        if (dto.hasToken()) {
            AuthTokenId(dto.token)
        } else {
            null
        }
    }

    override suspend fun setToken(token: AuthTokenId?) {
        dataStore.updateData { dto: AuthTokenDto ->
            dto.copy {
                if (token != null) {
                    this.token = token.value
                } else {
                    clearToken()
                }
            }
        }
    }

    override fun getTokenBlocking(): AuthTokenId? {
        return runBlocking { tokenFlow.first() }
    }

    public object AuthTokenDtoSerializer : Serializer<AuthTokenDto> {
        override val defaultValue: AuthTokenDto = AuthTokenDto.getDefaultInstance()

        override suspend fun readFrom(input: InputStream): AuthTokenDto {
            try {
                val t = AuthTokenDto.parseFrom(input)
                return t
            } catch (exception: InvalidProtocolBufferException) {
                throw CorruptionException("Cannot read proto.", exception)
            }
        }

        override suspend fun writeTo(t: AuthTokenDto, output: OutputStream): Unit {
            t.writeTo(output)
        }
    }
}
