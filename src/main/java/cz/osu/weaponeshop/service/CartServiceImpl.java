package cz.osu.weaponeshop.service;

import cz.osu.weaponeshop.exception.NotFoundException;
import cz.osu.weaponeshop.model.Cart;
import cz.osu.weaponeshop.model.Weapon;
import cz.osu.weaponeshop.model.WeaponOrderLine;
import cz.osu.weaponeshop.model.dto.*;
import cz.osu.weaponeshop.repository.CartRepository;
import cz.osu.weaponeshop.repository.UserRepository;
import cz.osu.weaponeshop.repository.WeaponOrderLineRepository;
import cz.osu.weaponeshop.repository.WeaponRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartServiceImpl {
    private final CartRepository cartRepo;
    private final WeaponRepository weaponRepo;
    private final UserRepository userRepo;
    private final WeaponOrderLineRepository weaponOrderLineRepos;

    public void createCart(CreateOrderRequest createOrderRequest) {
        Cart newCart = mapCreateOrderRequestToCart(createOrderRequest);
        newCart.setUser(userRepo.findByUserName(createOrderRequest.getUserName()).orElseThrow(() ->
                new NotFoundException("User by that name does not exist:" + createOrderRequest.getUserName())));
        cartRepo.save(newCart);
    }

    public CartDTO getCartDTO(Long cartId) {
        Cart cart = cartRepo.findById(cartId).orElseThrow(() -> new NotFoundException("Cart was not found with ID: " + cartId));
        return mapCartToCartDTO(cart);


    }

    private CartDTO mapCartToCartDTO(Cart cart) {
        List<WeaponOrderLineDTO> weaponOrderLineDTOS = new ArrayList<>();
        for (WeaponOrderLine weaponOrderLine : cart.getOrderedWeapons()) {
            weaponOrderLineDTOS.add(
                    WeaponOrderLineDTO.builder()
                            .weaponName(weaponOrderLine.getWeapon().getName())
                            .count(weaponOrderLine.getCount())
                            .build()
            );
        }
        return CartDTO.builder()
                .userName(cart.getUser().getUsername())
                .weaponOrderLineDTO(weaponOrderLineDTOS)
                .build();
    }

    private Cart getCart(Long cartId) {
        return cartRepo.findById(cartId).orElseThrow(() -> new NotFoundException("Cart was not found with ID: " + cartId));
    }
    @Transactional
    public void updateCart(Long cartId, OrderRequest orderRequest) {
        Cart cart = cartRepo.findById(cartId).orElseThrow(() -> new NotFoundException("Cart not found with ID: " + cartId));
        cart.getOrderedWeapons().clear();
        cartRepo.save(cart);
        Cart diffCart = mapOrderRequestToCart(cartId, orderRequest);
        cart.getOrderedWeapons().addAll(diffCart.getOrderedWeapons());
        cartRepo.save(cart);
    }

    public void deleteCart(Long cartId) {
        cartRepo.delete(getCart(cartId));
    }

    private Cart mapOrderRequestToCart(Long cartId, OrderRequest orderRequest) {
        List<WeaponOrderLine> weaponOrderLines = new ArrayList<>();
        for (ItemRequest itemRequest : orderRequest.getRequestedItems()) {
            weaponOrderLines.add(mapItemRequestToWeapon(cartId, itemRequest));
        }
        return Cart.builder()
                .orderedWeapons(weaponOrderLines)
                .build();
    }

    private Cart mapCreateOrderRequestToCart(CreateOrderRequest orderRequest) {
        List<WeaponOrderLine> weaponOrderLines = new ArrayList<>();
        for (ItemRequest itemRequest : orderRequest.getRequestedItems()) {
            weaponOrderLines.add(mapItemRequestToWeapon(null, itemRequest));
        }
        return Cart.builder()
                .orderedWeapons(weaponOrderLines)
                .build();
    }

    private WeaponOrderLine mapItemRequestToWeapon(Long cartId, ItemRequest itemRequest) {
        Cart cart = null;
        Weapon weapon = weaponRepo.findById(itemRequest.getWeaponId()).orElseThrow(() ->
                new NotFoundException("Requested weapon was not found with Id"
                        + itemRequest.getWeaponId()));
        if (cartId != null) {
            cart = getCart(cartId);
        }
        return WeaponOrderLine.builder()
                .weapon(weapon)
                .count(itemRequest.getCount())
                .build();
    }
}
