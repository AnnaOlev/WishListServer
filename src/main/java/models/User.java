package models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table (name = "users")
public class User {

    @Id
    @Column (name = "login")
    private String login;

    @Column (name = "password")
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WishList> wishLists;

    public User() {
    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
        wishLists = new ArrayList<>();
    }

    public void addList(WishList wishList) {
        wishList.setUser(this);
        wishLists.add(wishList);
    }

    public void removeList(WishList wishList) {
        wishLists.remove(wishList);
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<WishList> getWishLists() {
        return wishLists;
    }

    public void setWishLists(List<WishList> wishLists) {
        this.wishLists = wishLists;
    }

    @Override
    public String toString() {
        return "User " +
                ", login=" + login +
                ", password=" + password +
                ", wishLists=" + wishLists;
    }
}
