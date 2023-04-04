package sk.aconsulting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sk.aconsulting.models.Item;

import java.util.List;

@Repository
public interface IItemRepository extends JpaRepository<Item, Long> {

    List<Item> findAll();


}
