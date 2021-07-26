package ru.toroschin.spring.market.repositories;

import org.hibernate.annotations.NamedNativeQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.toroschin.spring.market.models.Remark;

import java.util.List;

@Repository
public interface RemarkRepository extends JpaRepository<Remark, Long> {
    @Query(name= "rem", nativeQuery=true, value="select * from prod_remarks as r where r.product_id=?1")
    List<Remark> findAllForProduct(Long id);
}
