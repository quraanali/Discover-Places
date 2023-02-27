package com.quraanali.discoverPlaces.domain.cart.mustnavigatetovariant

import com.quraanali.discoverPlaces.data.sync.SyncDataSource
import com.quraanali.discoverPlaces.data.sync.models.Modifier
import com.quraanali.discoverPlaces.data.sync.models.ProductDto
import com.quraanali.discoverPlaces.data.sync.models.Variant
import com.quraanali.discoverPlaces.domain.currentorder.MustNavigateToVariantUseCase
import io.mockk.coEvery
import io.mockk.mockk
import io.realm.kotlin.ext.realmListOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test


class MustNavigateToSelectedVariantUseCaseUnitTestOption {


    @Test
    fun `product has variant`() {
        runBlocking {
            val useCase = MustNavigateToVariantUseCase(provideProductDtoWithVariant())
            assertTrue(useCase(1))
        }
    }

    @Test
    fun `product has modifier`() {
        runBlocking {
            val useCase = MustNavigateToVariantUseCase(provideProductDtoWithWithModifier())
            assertTrue(useCase(1))
        }
    }

    @Test
    fun `product has variant and modifier`() {
        runBlocking {
            val useCase =
                MustNavigateToVariantUseCase(provideProductDtoWithWithVariantAndModifier())
            assertTrue(useCase(1))
        }
    }

    @Test
    fun `product has no variant or modifiers`() {
        runBlocking {
            val useCase =
                MustNavigateToVariantUseCase(provideProductDtoWithWithoutModifiersOrVariant())
            assertFalse(useCase(1))
        }
    }


    private fun provideProductDtoWithVariant(): SyncDataSource {
        val mockedSyncDataSource = mockk<SyncDataSource>()
        coEvery { mockedSyncDataSource.getProductById(any()) } coAnswers {
            ProductDto().apply {
                this.variant = Variant()
            }
        }
        return mockedSyncDataSource
    }

    private fun provideProductDtoWithWithModifier(): SyncDataSource {
        val mockedSyncDataSource = mockk<SyncDataSource>()
        coEvery { mockedSyncDataSource.getProductById(any()) } coAnswers {
            ProductDto().apply {
                this.modifiers = realmListOf(Modifier())
            }
        }
        return mockedSyncDataSource
    }

    private fun provideProductDtoWithWithoutModifiersOrVariant(): SyncDataSource {
        val mockedSyncDataSource = mockk<SyncDataSource>()
        coEvery { mockedSyncDataSource.getProductById(any()) } coAnswers {
            ProductDto().apply {
                this.modifiers = realmListOf()
                this.variant = null
            }
        }
        return mockedSyncDataSource
    }

    private fun provideProductDtoWithWithVariantAndModifier(): SyncDataSource {
        val mockedSyncDataSource = mockk<SyncDataSource>()
        coEvery { mockedSyncDataSource.getProductById(any()) } coAnswers {
            ProductDto().apply {
                this.modifiers = realmListOf(Modifier())
                this.variant = Variant()
            }
        }
        return mockedSyncDataSource
    }
}