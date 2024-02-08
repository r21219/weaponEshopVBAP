package cz.osu.weaponeshop.service;

import cz.osu.weaponeshop.exception.BadRequestException;
import cz.osu.weaponeshop.exception.NotFoundException;
import cz.osu.weaponeshop.model.Cart;
import cz.osu.weaponeshop.model.CartStatus;
import cz.osu.weaponeshop.model.Weapon;
import cz.osu.weaponeshop.model.WeaponOrderLine;
import cz.osu.weaponeshop.model.dto.ItemRequest;
import cz.osu.weaponeshop.model.dto.WeaponOrderLineDTO;
import cz.osu.weaponeshop.model.dto.cart.CartDTO;
import cz.osu.weaponeshop.model.dto.cart.CreateCartRequest;
import cz.osu.weaponeshop.model.dto.cart.UpdateCartRequest;
import cz.osu.weaponeshop.repository.CartRepository;
import cz.osu.weaponeshop.repository.UserRepository;
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
    private final String CART_NOT_FOUND = "Cart not found with ID: ";
    private final String CART_ID_NULL = "Cart id cannot be empty";
    private final String ITEM_INVALID = "Item cannot have empty id or count less or equal than 0";

    public void createCart(CreateCartRequest createCartRequest) {
        if (createCartRequest.isEmpty()) {
            throw new BadRequestException("Cart name cannot be empty");
        }
        Cart newCart = mapCreateCartRequestToCart(createCartRequest);
        newCart.setUser(userRepo.findByUserName(createCartRequest.getUserName()).orElseThrow(() ->
                new NotFoundException("User by that name does not exist:" + createCartRequest.getUserName())));
        cartRepo.save(newCart);
    }

    public CartDTO getCartDTO(Long cartId) {
        if (cartId == null) {
            throw new BadRequestException(CART_ID_NULL);
        }
        Cart cart = cartRepo.findById(cartId).orElseThrow(() -> new NotFoundException(CART_NOT_FOUND + cartId));
        return mapCartToCartDTO(cart);


    }
    public CartDTO getCurrentCart(String userName){
        Cart cart = cartRepo.findByUser_UserNameAndCartStatus(userName,CartStatus.UNFINISHED);
        if (cart == null){
            throw new NotFoundException("Card was not found");
        }
        return mapCartToCartDTO(cart);
    }
    public CartDTO getCartDTO(String username,UpdateCartRequest updateCartRequest) {
        if (username == null) {
            throw new BadRequestException(CART_ID_NULL);
        }
        Cart cart = cartRepo.findByUser_UserNameAndCartStatus(username,CartStatus.UNFINISHED);
        if (cart == null){
            cart = Cart.builder()
                    .user(userRepo.findByUserName(username).orElseThrow(()-> new NotFoundException("User was not found")))
                    .cartStatus(CartStatus.UNFINISHED)
                    .orderedWeapons(new ArrayList<>())
                    .build();
        }else {
            cart.getOrderedWeapons().clear();
        }
        Cart diffCart = mapUpdateCartRequestToCart(updateCartRequest);
        cart.getOrderedWeapons().addAll(diffCart.getOrderedWeapons());
        cart = cartRepo.save(cart);
        return mapCartToCartDTO(cart);
    }
    private CartDTO mapCartToCartDTO(Cart cart) {
        List<WeaponOrderLineDTO> weaponOrderLineDTOS = new ArrayList<>();
        for (WeaponOrderLine weaponOrderLine : cart.getOrderedWeapons()) {
            weaponOrderLineDTOS.add(
                    WeaponOrderLineDTO.builder()
                            .weaponName(weaponOrderLine.getWeapon().getName())
                            .totalPrice(weaponOrderLine.getTotalPrice())
                            .count(weaponOrderLine.getCount())
                            .build()
            );
        }
        return CartDTO.builder()
                .id(cart.getId())
                .userName(cart.getUser().getUsername())
                .weaponOrderLineDTO(weaponOrderLineDTOS)
                .build();
    }

    private Cart getCart(Long cartId) {
        if (cartId == null) {
            throw new BadRequestException(CART_ID_NULL);
        }
        return cartRepo.findById(cartId).orElseThrow(() -> new NotFoundException(CART_NOT_FOUND + cartId));
    }

    @Transactional
    public void updateCart(Long cartId, UpdateCartRequest updateCartRequest) {
        if (cartId == null) {
            throw new BadRequestException(CART_ID_NULL);
        }
        Cart cart = cartRepo.findById(cartId).orElseThrow(() -> new NotFoundException(CART_NOT_FOUND + cartId));
        cart.getOrderedWeapons().clear();
        cartRepo.save(cart);
        Cart diffCart = mapUpdateCartRequestToCart(updateCartRequest);
        cart.getOrderedWeapons().addAll(diffCart.getOrderedWeapons());
        cartRepo.save(cart);
    }

    public void deleteCart(Long cartId) {
        if (cartId == null) {
            throw new BadRequestException(CART_ID_NULL);
        }
        cartRepo.delete(getCart(cartId));
    }

    private Cart mapUpdateCartRequestToCart(UpdateCartRequest updateCartRequest) {
        List<WeaponOrderLine> weaponOrderLines = new ArrayList<>();
        for (ItemRequest itemRequest : updateCartRequest.getRequestedItems()) {
            if (itemRequest.isInValid()) {
                throw new BadRequestException(ITEM_INVALID);
            }
            weaponOrderLines.add(mapItemRequestToWeapon(itemRequest));
        }
        return Cart.builder()
                .orderedWeapons(weaponOrderLines)
                .build();
    }

    private Cart mapCreateCartRequestToCart(CreateCartRequest orderRequest) {
        List<WeaponOrderLine> weaponOrderLines = new ArrayList<>();
        for (ItemRequest itemRequest : orderRequest.getRequestedItems()) {
            if (itemRequest.isInValid()) {
                throw new BadRequestException(ITEM_INVALID);
            }
            weaponOrderLines.add(mapItemRequestToWeapon(itemRequest));
        }
        return Cart.builder()
                .orderedWeapons(weaponOrderLines)
                .build();
    }

    private WeaponOrderLine mapItemRequestToWeapon(ItemRequest itemRequest) {
        Weapon weapon = weaponRepo.findById(itemRequest.getWeaponId()).orElseThrow(() ->
                new NotFoundException("Requested weapon was not found with Id"
                        + itemRequest.getWeaponId()));
        return WeaponOrderLine.builder()
                .weapon(weapon)
                .totalPrice(weapon.getPrice() * itemRequest.getCount())
                .count(itemRequest.getCount())
                .build();
    }
}
