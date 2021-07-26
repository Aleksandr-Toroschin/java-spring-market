package ru.toroschin.spring.market.dtos;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import ru.toroschin.spring.market.models.Product;
import ru.toroschin.spring.market.models.Remark;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Data
public class RemarkDto {
    private Long id;
    private String content;
    private Long product_id;
    private LocalDateTime createdAt;

    public RemarkDto(Remark remark) {
        this.id = remark.getId();
        this.content = remark.getContent();
        this.product_id = remark.getProduct().getId();
        this.createdAt = remark.getCreatedAt();
    }
}
