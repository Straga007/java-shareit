package ru.practicum.item;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.item.object.Item;
import ru.practicum.user.object.User;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByOwner(User owner);

    Page<Item> findByOwner(User owner, Pageable pageable);

    @Query("SELECT i FROM Item i WHERE UPPER(i.name) LIKE UPPER(CONCAT('%', ?1, '%')) OR UPPER(i.description) LIKE UPPER(CONCAT('%', ?1, '%'))")
    List<Item> searchItems(String text);

    @Query("SELECT i FROM Item i WHERE UPPER(i.name) LIKE UPPER(CONCAT('%', ?1, '%')) OR UPPER(i.description) LIKE UPPER(CONCAT('%', ?1, '%'))")
    Page<Item> searchItems(String text, Pageable pageable);

    List<Item> findAllByRequestId(Long requestId);
}
