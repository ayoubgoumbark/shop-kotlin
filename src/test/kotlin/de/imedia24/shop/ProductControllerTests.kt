package de.imedia24.shop
import com.fasterxml.jackson.databind.ObjectMapper
import de.imedia24.shop.controller.ProductController
import de.imedia24.shop.domain.product.ProductResponse
import de.imedia24.shop.domain.product.ProductUpdateRequest
import de.imedia24.shop.service.ProductService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.mockito.Mockito.`when`
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch
import java.math.BigDecimal



@WebMvcTest
class ProductControllerTests {

	@Autowired
	private lateinit var mockMvc: MockMvc

	@MockBean
	private lateinit var productService: ProductService




	@Test
	fun `should return product by SKU`() {
		val sku = "test"

		val productResponse = ProductResponse(
			sku = sku,
			name = "laptop",
			description = "Desc1",
			price = BigDecimal.valueOf(99.99),
			stock=1
		)

		`when`(productService.findProductBySku(sku)).thenReturn(productResponse)

		mockMvc.perform(get("/products/{sku}", sku)
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk)


	}

        @Test
        fun `should return 404 if product not found`() {
            val sku = "NonExistentSKU"


            `when`(productService.findProductBySku(sku)).thenReturn(null)

            mockMvc.perform(get("/products/{sku}", sku))
                .andExpect(status().isNotFound)

        }





            @Test
            fun `should partially update product by SKU`() {
                val sku = "test"
                val productUpdateRequest = ProductUpdateRequest(name = "New Product Name",description = "updated Description",price = BigDecimal.valueOf(99.99))
                val updatedProduct = ProductResponse(
                    sku = sku,
                    name = "New Product Name",
                    description = "updated Description",
                    price = BigDecimal.valueOf(99.99),
                    stock=1
                )


                val objectMapper = ObjectMapper()
                val jsonRequest = objectMapper.writeValueAsString(productUpdateRequest)

                `when`(productService.updateProduct(sku, productUpdateRequest)).thenReturn(updatedProduct)

                mockMvc.perform(
                    patch("/product/{sku}", sku)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonRequest))
                    .andExpect(status().isOk)

            }

                @Test
                fun `should return 404 if product not found for update`() {
                    val sku = "NonExistentSKU"
                    val productUpdateRequest = ProductUpdateRequest(name = "New Product Name",description = "updated Description",price = BigDecimal.valueOf(99.99))
                    val objectMapper = ObjectMapper()
                    val jsonRequest = objectMapper.writeValueAsString(productUpdateRequest)

                    `when`(productService.updateProduct(sku, productUpdateRequest)).thenReturn(null)

                    mockMvc.perform(patch("/product/{sku}", sku)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                        .andExpect(status().isNotFound)

                }


}
