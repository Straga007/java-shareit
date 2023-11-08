
package ru.practicum.shareit.request.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.request.object.ItemRequest;

public interface RequestRepository extends JpaRepository<ItemRequest, Long> {
}

