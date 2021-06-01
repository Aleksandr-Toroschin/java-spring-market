package ru.toroschin.spring.market.soap.endpoints;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import ru.toroschin.spring.market.products.GetProductByIdRequest;
import ru.toroschin.spring.market.soap.services.ProductSoapService;
import ru.toroschin.spring.market.soap.soap.categories.GetCategoryByTitleResponse;
import ru.toroschin.spring.market.soap.soap.products.GetProductByIdResponse;

@Endpoint
@RequiredArgsConstructor
@Slf4j
public class ProductEndpoint {
    private static final String NAMESPACE_URI = "http://www.toroschin.ru/spring/market/products";
    private final ProductSoapService productSoapService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getProductByIdRequest")
    @ResponsePayload
    @Transactional
    public GetProductByIdResponse getProductById(@RequestPayload GetProductByIdRequest request) {
        log.info("Запрос категории");
        GetProductByIdResponse response = new GetProductByIdResponse();
        response.setProduct(productSoapService.getById(request.getId()));
        return response;
    }

}
