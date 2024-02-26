package xindus.backend.assignment.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import xindus.backend.assignment.entity.WishlistEntity;

@Repository
public interface WishlistRepository extends CrudRepository<WishlistEntity,Long> {
}
