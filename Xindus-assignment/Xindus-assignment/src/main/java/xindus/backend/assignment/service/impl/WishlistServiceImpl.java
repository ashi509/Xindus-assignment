package xindus.backend.assignment.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import xindus.backend.assignment.entity.WishlistEntity;
import xindus.backend.assignment.exception.GenericException;
import xindus.backend.assignment.repository.WishlistRepository;
import xindus.backend.assignment.service.WishlistService;

import java.util.Date;
@Service
public class WishlistServiceImpl implements WishlistService {
    @Autowired
    private WishlistRepository wishlistRepository;

    /**
     * Retrieve wishlist
     * @return
     * @throws GenericException
     */
    @Override
    public ResponseEntity<?> getWishlist() throws GenericException {
        var wish = wishlistRepository.findAll();
        if (wish == null)
            new GenericException(HttpStatus.NOT_FOUND.value(), "wishlist not found");
        return ResponseEntity.status(HttpStatus.OK).body(wish);
    }

    /**
     *
     * @param wishlistEntity
     * @return
     * @throws GenericException
     *  For Add any wishlist
     */
    @Override
    public ResponseEntity<?> addWishlist(WishlistEntity wishlistEntity) throws GenericException {
        wishlistEntity.setId(0);
        wishlistEntity.setWishlistAddedDate(new Date());
        var wish = wishlistRepository.save(wishlistEntity);
        if (wish == null)
            new GenericException(HttpStatus.BAD_REQUEST.value(), "Something went wrong wishlist not save");
        return ResponseEntity.status(HttpStatus.CREATED).body(wish);
    }

    /**
     *
     * @param id
     * @return
     * @throws GenericException
     * For deleting  any wishlist
     */
    @Override
    public ResponseEntity<?> deleteWishlist(long id) throws GenericException {
        var wish = wishlistRepository.findById(id).orElseThrow(() ->
                new GenericException(HttpStatus.NOT_FOUND.value(), "wishlist not found with given id " + id));
        wishlistRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(wish);
    }
}
