package ru.toroschin.spring.market.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.toroschin.spring.market.models.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
}
