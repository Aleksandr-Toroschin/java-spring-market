package ru.toroschin.spring.market.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.toroschin.spring.market.models.Product;
import ru.toroschin.spring.market.models.Remark;
import ru.toroschin.spring.market.repositories.RemarkRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class RemarkService {
    private final RemarkRepository remarkRepository;

    public List<Remark> findAllForProduct(Long id) {
        return remarkRepository.findAllForProduct(id);
    }

    public void addRemark(Product product, String content) {
        Remark remark = new Remark();
        remark.setContent(content);
        remark.setProduct(product);
        remarkRepository.save(remark);
    }
}
