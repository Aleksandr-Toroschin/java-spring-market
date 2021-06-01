package ru.toroschin.spring.market.soap.endpoints;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import ru.toroschin.spring.market.soap.services.ProductSoapService;
import ru.toroschin.spring.market.soap.soap.categories.GetCategoryByTitleResponse;
import ru.toroschin.spring.market.soap.soap.products.GetAllProductsRequest;
import ru.toroschin.spring.market.soap.soap.products.GetAllProductsResponse;
import ru.toroschin.spring.market.soap.soap.products.GetProductByIdRequest;
import ru.toroschin.spring.market.soap.soap.products.GetProductByIdResponse;

@Endpoint
@RequiredArgsConstructor
@Slf4j
public class ProductEndpoint {
    private static final String NAMESPACE_URI = "http://www.toroschin.ru/spring/market/products";
    private final ProductSoapService productSoapService;

    /*
    Пример запроса: POST http://localhost:8189/market/ws

    <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:f="http://www.toroschin.ru/spring/market/products">
        <soapenv:Header/>
        <soapenv:Body>
            <f:getAllProductsRequest/>
                <f:id>1</f:id>
            </f:getProductByIdRequest>
        </soapenv:Body>
    </soapenv:Envelope>
     */

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getProductByIdRequest")
    @ResponsePayload
    @Transactional
    public GetProductByIdResponse getProductById(@RequestPayload GetProductByIdRequest request) {
        GetProductByIdResponse response = new GetProductByIdResponse();
        response.setProduct(productSoapService.getById(request.getId()));
        return response;
    }

    /*
    <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:f="http://www.toroschin.ru/spring/market/products">
        <soapenv:Header/>
        <soapenv:Body>
            <f:getAllProductsRequest/>
        </soapenv:Body>
    </soapenv:Envelope>
    */

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllProductsRequest")
    @ResponsePayload
    @Transactional
    public GetAllProductsResponse getAllProducts(@RequestPayload GetAllProductsRequest request) {
        GetAllProductsResponse response = new GetAllProductsResponse();
        response.getProducts().addAll(productSoapService.getAllProducts());
        return response;
    }

}
