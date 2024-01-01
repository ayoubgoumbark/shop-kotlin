package de.imedia24.shop.controller

import de.imedia24.shop.domain.product.ProductResponse
import de.imedia24.shop.domain.product.ProductRequest
import de.imedia24.shop.domain.product.ProductUpdateRequest
import de.imedia24.shop.service.ProductService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.websocket.server.PathParam

@RestController
class ProductController(private val productService: ProductService) {

    private val logger = LoggerFactory.getLogger(ProductController::class.java)!!



    @GetMapping("/products/{sku}", produces = ["application/json;charset=utf-8"])
    fun findProductsBySku(
        @PathVariable("sku") sku: String
    ): ResponseEntity<Any> {

        val product = productService.findProductBySku(sku)
        return if (product == null) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product Not Found for SKU: $sku")
        } else {
            ResponseEntity.ok(product)
        }
    }


    @GetMapping("/products", produces = ["application/json;charset=utf-8"])
    fun findProductsBySkus(@RequestParam("skus") skus: List<String>): ResponseEntity<List<ProductResponse>> {
        if (skus.isEmpty()) {
            return ResponseEntity.badRequest().build()
        }

        val products = productService.findProductsBySkus(skus)
        return if (products.isEmpty()) {
            ResponseEntity.notFound().build()
        } else {
            ResponseEntity.ok(products)
        }
    }


    @PostMapping("/product")
    fun addProduct(@RequestBody productRequest: ProductRequest): ResponseEntity<ProductResponse> {

        val newProduct = productService.addProduct(productRequest)
        return ResponseEntity.ok(newProduct)
    }


    @PatchMapping("/product/{sku}")
    fun partiallyUpdateProduct(
        @PathVariable sku: String,
        @RequestBody productUpdateRequest: ProductUpdateRequest
    ): ResponseEntity<ProductResponse?> {
        val updatedProduct = productService.updateProduct(sku, productUpdateRequest)

        return if (updatedProduct != null) {
            ResponseEntity.ok<ProductResponse?>(updatedProduct)
        } else {
            ResponseEntity.notFound().build<ProductResponse?>()
        }
    }
}
