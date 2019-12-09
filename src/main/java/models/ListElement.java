package models;

import javax.persistence.*;

@Entity
@Table (name = "\"listElements\"")
public class ListElement {

    @Id
    private int id;

    @Column (name = "\"elementName\"")
    private String elementName;

    @Column (name = "\"elementInfo\"")
    private String elementInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "\"wishList_id\"")
    private WishList wishList;

    public ListElement() {
    }

    public ListElement(int id, String elementName, String elementInfo) {
        this.id = id;
        this.elementName = elementName;
        this.elementInfo = elementInfo;
    }

    public String getElementName() {
        return elementName;
    }

    public void setElementName(String elementName) {
        this.elementName = elementName;
    }

    public String getElementInfo() {
        return elementInfo;
    }

    public void setElementInfo(String elementInfo) {
        this.elementInfo = elementInfo;
    }

    public WishList getWishList() {
        return wishList;
    }

    public void setWishList(WishList wishList) {
        this.wishList = wishList;
    }

    @Override
    public String toString() {
        return "ListElement " +
                "id=" + id +
                ", elementName='" + elementName +
                ", elementInfo='" + elementInfo +
                ", wishList=" + wishList;
    }

    public String toStringResponseElem() {
        return elementName + "," + elementInfo + "," + wishList.getId();
    }
}
