package models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table (name = "\"wishLists\"", schema = "public")
public class WishList {
    @Id
    private int id;

    @Column (name = "\"listName\"")
    private String listName;

    @Column (name = "\"listForWho\"")
    private String listForWho;

    @OneToMany(mappedBy = "wishList", orphanRemoval = true)
    private List<ListElement> listElements;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_login")
    private User user;

    public WishList(Integer id, String listName, String listForWho) {
        this.id = id;
        this.listName = listName;
        this.listForWho = listForWho;
        listElements = new ArrayList<>();
    }

    public WishList() {
    }

    public int getId() {
        return id;
    }

    public String getListName() {
        return listName;
    }

    public void addElement(ListElement listElement) {
        listElement.setWishList(this);
        listElements.add(listElement);
    }

    public void removeElement(ListElement listElement) {
        listElements.remove(listElement);
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public String getListForWho() {
        return listForWho;
    }

    public void setListForWho(String listForWho) {
        this.listForWho = listForWho;
    }

    public List<ListElement> getListElements() {
        return listElements;
    }

    public void setListElements(List<ListElement> listElements) {
        this.listElements = listElements;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "WishList " +
                "id=" + id +
                ", listName='" + listName +
                ", listForWho='" + listForWho +
                ", listElements=" + listElements;
    }

    public String toStringResponse() {
        return listName + "," + listForWho;
    }
}
