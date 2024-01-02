package de.imedia24.shop.service

import de.imedia24.shop.db.entity.ProductEntity
import de.imedia24.shop.db.repository.ProductRepository
import de.imedia24.shop.domain.product.ProductRequest
import de.imedia24.shop.domain.product.ProductResponse
import de.imedia24.shop.domain.product.ProductResponse.Companion.toProductResponse
import de.imedia24.shop.domain.product.ProductUpdateRequest
import org.springframework.stereotype.Service
import java.time.ZonedDateTime

@Service
class ProductService(private val productRepository: ProductRepository) {

    fun findProductBySku(sku: String): ProductResponse? {

        val productEntity: ProductEntity? = productRepository.findBySku(sku)
        return productEntity?.toProductResponse()
    }

    fun findProductsBySkus(skus: List<String>): List<ProductResponse> {
        return skus.mapNotNull { sku ->
            val productEntity = productRepository.findBySku(sku)
            productEntity?.toProductResponse()
        }
    }


    fun addProduct(productRequest: ProductRequest): ProductResponse {
        val newProductEntity = ProductEntity(
            sku = productRequest.sku,
            name = productRequest.name,
            description = productRequest.description,
            stock = productRequest.stock,
            price = productRequest.price,

        )

        val savedProductEntity = productRepository.save(newProductEntity)
        return  savedProductEntity.toProductResponse()
    }


    fun updateProduct(sku: String, productUpdateRequest: ProductUpdateRequest): ProductResponse? {
         val existingProduct : ProductEntity? = productRepository.findBySku(sku)


        if (existingProduct != null) {

            val updatedUser = existingProduct.copy(name = productUpdateRequest.name,
                price = productUpdateRequest.price, description =productUpdateRequest.description ,
                updatedAt = ZonedDateTime.now()

            )
            val updatedProductEntity = productRepository.save(updatedUser)
            return updatedProductEntity.toProductResponse()
        }

        return null
    }


}
