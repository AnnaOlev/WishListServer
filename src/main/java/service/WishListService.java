package service;

import dao.WishListDao;
import models.ListElement;
import models.WishList;

import java.util.List;

public class WishListService {

    private WishListDao wishListDao= new WishListDao();

    public WishListService() {
    }

    public WishList findWishList(int id) {
        return wishListDao.findById(id);
    }

    public void saveWishList (WishList wishList) {
        wishListDao.save(wishList);
    }

    public void updateWishList (WishList wishList) {
        wishListDao.update(wishList);
    }

    public void deleteWishList (WishList wishList) {
        wishListDao.delete(wishList);
    }

    public List<WishList> findAllWishLists() {
        return wishListDao.findAll();
    }

    public ListElement listElement (int id) {
        return wishListDao.findElementById(id);
    }
}
