package xindus.backend.assignment.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import xindus.backend.assignment.entity.WishlistEntity;
import xindus.backend.assignment.exception.GenericException;


public interface WishlistService {
    ResponseEntity<?> getWishlist()throws GenericException;
    ResponseEntity<?> addWishlist(WishlistEntity wishlistEntity)throws GenericException;
    ResponseEntity<?> deleteWishlist(long id) throws GenericException;

}
