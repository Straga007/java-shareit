package ru.practicum.item.object;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.practicum.request.object.ItemRequest;
import ru.practicum.user.object.User;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * TODO Sprint add-controllers.
 */
@Entity
@Table(name = "items")
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    @NotBlank(message = "Item cant be blank")
    @Size(min = 1, max = 50, message = "Name must be between 1 and 50 characters")
    String name;

    @Column(nullable = false)
    @NotBlank(message = "Item cant be blank")
    @Size(min = 1, max = 50, message = "description must be between 1 and 50 characters")
    String description;
    @Column(name = "is_available", nullable = false)
    @NotNull(message = "Item cant be null")
    Boolean available;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    User owner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "request_id")
    ItemRequest request;

    public boolean isAvailable() {
        return available;
    }
}
