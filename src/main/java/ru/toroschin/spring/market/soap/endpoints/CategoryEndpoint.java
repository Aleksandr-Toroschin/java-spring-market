package ru.toroschin.spring.market.soap.endpoints;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import ru.toroschin.spring.market.categories.GetCategoryByTitleRequest;
import ru.toroschin.spring.market.soap.services.CategorySoapService;
import ru.toroschin.spring.market.soap.soap.categories.GetCategoryByTitleResponse;

@Endpoint
@RequiredArgsConstructor
@Slf4j
public class CategoryEndpoint {
    private static final String NAMESPACE_URI = "http://www.toroschin.ru/spring/market/categories";
    private final CategorySoapService categorySoapService;

    /*
        Пример запроса: POST http://localhost:8189/market

        <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
          xmlns:f="http://www.toroschin.ru/spring/market/categories">
            <soapenv:Header/>
            <soapenv:Body>
                <f:getCategoryByTitleRequest>
                    <f:title>Food</f:title>
                </f:getCategoryByTitleRequest>
            </soapenv:Body>
        </soapenv:Envelope>
     */

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getCategoryByTitleRequest")
    @ResponsePayload
    @Transactional
    public GetCategoryByTitleResponse getCategoryByTitle(@RequestPayload GetCategoryByTitleRequest request) {
        log.info("Запрос категории");
        GetCategoryByTitleResponse response = new GetCategoryByTitleResponse();
        response.setCategory(categorySoapService.getByTitle(request.getTitle()));
        return response;
    }
}
