package com.project.controller;

import com.project.exception.BookAlreadyExistsException;
import com.project.exception.BookNotFoundException;
import com.project.exception.OutOfStockException;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Interface for managing inventory operations.
 */
@RequestMapping("/dbs/inventory")
public interface InventoryController {


    @GetMapping("pages")
    public int pages();
    /**
     * Displays the entire inventory with pagination.
     *
     * @param page the page number to retrieve (starting from 0).
     * @param size the number of items per page.
     * @return ResponseEntity containing the inventory details.
     */
    @GetMapping("")
    ResponseEntity<Object> displayInventory( @Min(value = 0, message = "Page number must be zero or greater")int page, @Min(value = 1, message = "Page size must be at least 1") int size);

    /**
     * Retrieves inventory details for a specific book by its ID.
     * @param bookID the ID of the book.
     * @return ResponseEntity containing the book's inventory details.
     * @throws BookNotFoundException if the book is not found.
     */
    @GetMapping("/{bookID}")
    ResponseEntity<?> getInventoryByBookID(@PathVariable @NotBlank(message = "Book ID cannot be blank") String bookID) throws BookNotFoundException;

    /**
     * Retrieves the quantity of a specific book by its ID.
     * @param bookID the ID of the book.
     * @return ResponseEntity containing the quantity of the book.
     * @throws BookNotFoundException if the book is not found.
     */
    @GetMapping("/quantity/{bookID}")
    ResponseEntity<?> getNoOfBooks(@PathVariable @NotBlank(message = "Book ID cannot be blank") String bookID) throws BookNotFoundException;

    /**
     * Adds a specified quantity of a book to the inventory.
     * @param bookID the ID of the book.
     * @param quantity the quantity to add.
     * @return ResponseEntity indicating the result of the operation.
     * @throws BookNotFoundException if the book is not found.
     */
    @PutMapping("/update/add")
    ResponseEntity<?> updateAddInventory(@RequestParam @NotBlank(message = "Book ID cannot be blank") String bookID,
                                         @RequestParam @Min(value = 1, message = "Quantity must be at least 1") int quantity) throws BookNotFoundException;
    /**
     * Removes a specified quantity of a book from the inventory.
     * @param bookID the ID of the book.
     * @param quantity the quantity to remove.
     * @return ResponseEntity indicating the result of the operation.
     * @throws BookNotFoundException if the book is not found.
     */
    @PutMapping("/update/remove")
    ResponseEntity<?> updateRemoveInventory(@RequestParam @NotBlank(message = "Book ID cannot be blank") String bookID,
                                            @RequestParam @Min(value = 1, message = "Quantity must be at least 1") int quantity) throws BookNotFoundException;
    /**
     * Checks if an order can be placed for a specified quantity of a book.
     * @param bookID the ID of the book.
     * @param quantity the quantity to order.
     * @return ResponseEntity indicating the result of the operation.
     * @throws BookNotFoundException if the book is not found.
     * @throws OutOfStockException if the book is out of stock.
     */
    @PostMapping("/order")
    ResponseEntity<?> canPlaceOrder(@RequestParam @NotBlank(message = "Book ID cannot be blank") String bookID,
                                 @RequestParam @Min(value = 1, message = "Quantity must be at least 1") int quantity) throws BookNotFoundException, OutOfStockException;
    /**
     * Updates the inventory after an order is placed.
     * @param bookIDs the list of book IDs.
     * @param quantities the list of quantities.
     * @return ResponseEntity indicating the result of the operation.
     */
    @PutMapping("/updateAfterOrder")
    ResponseEntity<?> updateInventoryAfterOrder(@RequestParam @NotNull @Size(min = 1, message = "Book IDs list cannot be empty") List<@NotBlank String> bookIDs,
                                                @RequestParam @NotNull @Size(min = 1, message = "Quantities list cannot be empty") List<@Min(value = 0, message = "Quantity cannot be negative") Integer> quantities);
    /**
     * Adds a new book to the inventory.
     * @param bookID the ID of the book.
     * @param quantity the quantity to add.
     * @return ResponseEntity indicating the result of the operation.
     * @throws BookAlreadyExistsException if the book already exists in the inventory.
     */
    @PostMapping("/add")
    ResponseEntity<?> addBookToInventory(@RequestParam @NotBlank(message = "Book ID cannot be blank") String bookID,
                                         @RequestParam @Min(value = 1, message = "Quantity must be at least 1") int quantity) throws BookAlreadyExistsException;
    /**
     * Deletes a book from the inventory.
     * @param bookID the ID of the book.
     * @return ResponseEntity indicating the result of the operation.
     * @throws BookNotFoundException if the book is not found.
     */
    @DeleteMapping("/{bookID}")
    ResponseEntity<?> deleteBookFromInventory(@PathVariable @NotBlank(message = "Book ID cannot be blank") String bookID) throws BookNotFoundException;
}
