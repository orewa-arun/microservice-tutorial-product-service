# PRODUCT SERVICE

Product service provides two key services : 1) Create Product, 2) Get All Products.

## TABLE OF CONTENTS

1. [Description](#description)
2. [What's Next](#whats-next)
3. [Appendix](#appendix)

## Description
This tutorial is built using good practices such as using DTOs, using lombok and I learnt something new : 
@Build to build objects that can then be utilised for the database.

This line is used in createProduct service :
```java
    Product product = Product.builder().name(productRequest.getName()).description(productRequest.getDescription()).category(productRequest.getCategory()).price(productRequest.getPrice()).build();
```
#### Another important line of code is the List manipulation using stream :
In the below code, I have transformed the productList into productResponseList
```java
    public List<ProductResponse> getAllProducts(){
        List<Product> productList = productRepository.findAll();
        return productList.stream().map(this::mapToProductResponse).toList();
    }

    private ProductResponse mapToProductResponse(Product product) {
        return ProductResponse.builder().id(product.getId()).name(product.getName()).description(product.getDescription()).category(product.getCategory()).price(product.getPrice()).build();
    }
```

#### What's more is I got to learn about writing tests using JUnit
The core crux of writing tests is that you run them on isolated docker containers, which contains the necessary images you need for the application 
to run, such as mongodb.
Beautiful annotations exist such as @Container which help you to configure your docker dependencies for the test.
But ultimately you have to define the dependency in pom.xml for accessing those containers in tests.

```xml
    <dependency>
        <dependency>
            <groupId>org.testcontainers</groupId>
            <artifactId>mongodb</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.testcontainers</groupId>
            <artifactId>junit-jupiter</artifactId>
            <scope>test</scope>
        </dependency>
    </dependency>
```

In test file :
```java
    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.4.2");

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry dymDynamicPropertyRegistry){
        dymDynamicPropertyRegistry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }
```

Then the tests are written with an example test case : you give an input and check if the output matches with your expected output.
```java
	@Test
	void shouldCreateProduct() throws Exception {
		ProductRequest productRequest = getProductRequest();

		String productRequestString = objectMapper.writeValueAsString(productRequest);
		mockMvc.perform(MockMvcRequestBuilders.post("/api/product/create").contentType(MediaType.APPLICATION_JSON).content(productRequestString)).andExpect(status().isCreated());

		Assertions.assertEquals(1, productRepository.findAll().size());
	}

	private ProductRequest getProductRequest() {
		return ProductRequest.builder().name("macbook air m2").description("best and glorious laptop").category("laptop").price(BigDecimal.valueOf(128000.00)).build();
	}
```

## What's next
    It's better to just stick to this tutorial so that i can first complete my commitment, rather than getting ahead of myself and end up having a slump
reacting to my short-lived enthusiasm.

## Appendix
    ### The youtube link to the tutorial is :
    [Youtube_Link](`https://www.youtube.com/watch?v=lh1oQHXVSc0&list=PLSVW22jAG8pBnhAdq9S8BpLnZ0_jVBj0c`)
    
    ### The github link to the tutorial is :
    [Github_Link](`https://www.youtube.com/redirect?event=video_description&redir_token=QUFFLUhqbHhJdWpmcFlwcU5CY1JFWkM5SVAya1BUWGxKd3xBQ3Jtc0trTURSZG1pVmpmaXdwR0s5cWlHczctb2tnTzFfWmpjT2RtcWRNOU05bEFTeng0ZzFlQXBTQ0pMNkE5Vk94a2VEZm15VVZSem5EY0tJWUJiWVEzaEE0RHl0Rk84UnpRRE9MOHFZZ01WaUdsRDItUmNKSQ&q=https%3A%2F%2Fgithub.com%2FSaiUpadhyayula%2Fspring-boot-microservices-new.git&v=lh1oQHXVSc0`)

## Always remember :
    Makes mistakes and learn fast! and be consistent and tend to your commitments by staying present, results will show on its own.