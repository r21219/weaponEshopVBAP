package cz.osu.weaponeshop.controller;

import cz.osu.weaponeshop.model.dto.CartDTO;
import cz.osu.weaponeshop.model.dto.CreateOrderRequest;
import cz.osu.weaponeshop.model.dto.OrderRequest;
import cz.osu.weaponeshop.service.CartServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {
    private final CartServiceImpl service;

    @PostMapping
    @Operation(summary = "Creates a cart", description = "Creates a cart")
    public ResponseEntity<String> createCart(@RequestBody CreateOrderRequest createOrderRequest) {
        service.createCart(createOrderRequest);
        return new ResponseEntity<>("Successfully create cart", HttpStatus.OK);
    }

    @GetMapping("/{cartId}")
    @Operation(summary = "Get a cart ", description = "Gets the specific cart")
    public ResponseEntity<CartDTO> getCart(@PathVariable Long cartId) {
        return ResponseEntity.ok(service.getCartDTO(cartId));
    }

    @PutMapping("/{cartId}")
    @Operation(summary = "Updates a cart", description = "Updates a cart")
    public ResponseEntity<String> putCart(Long cartId, @RequestBody OrderRequest orderRequest) {
        service.updateCart(cartId, orderRequest);
        return new ResponseEntity<>("Successfully update the cart", HttpStatus.OK);
    }

    @DeleteMapping("/{cartId}")
    @Operation(summary = "Deletes a cart", description = "Deletes a cart based on id")
    public ResponseEntity<String> deleteCart(@PathVariable Long cartId) {
        service.deleteCart(cartId);
        return new ResponseEntity<>("Successfully deleted the cart", HttpStatus.OK);
    }
}