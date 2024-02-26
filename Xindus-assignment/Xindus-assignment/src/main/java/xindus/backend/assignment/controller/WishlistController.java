package xindus.backend.assignment.controller;

import org.apache.catalina.valves.JsonErrorReportValve;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xindus.backend.assignment.entity.WishlistEntity;
import xindus.backend.assignment.exception.GenericException;
import xindus.backend.assignment.service.WishlistService;

@RestController
@RequestMapping("/wishlist")
public class WishlistController {
    @Autowired
    private WishlistService wishlistService;

    /**
     * Retrieve wishlist
     * @return
     * @throws GenericException
     */
    @GetMapping
    ResponseEntity<?> getWishlist()throws GenericException{
        return wishlistService.getWishlist();
    }

    /**
     * Add a new wishlist
     * @param wishlistEntity
     * @return
     * @throws GenericException
     */
    @PostMapping
    ResponseEntity<?> addWishlist(@RequestBody WishlistEntity wishlistEntity)throws GenericException{
        return wishlistService.addWishlist(wishlistEntity);
    }

    /**
     * Delete a wishlist by wishlist id
     * @param id
     * @return
     * @throws GenericException
     */
    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteWishlist(@PathVariable("id") long id) throws GenericException{
        return wishlistService.deleteWishlist(id);
    }

}
