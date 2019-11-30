package service;

import dao.ListElementDao;
import models.ListElement;

import java.util.List;

public class ListElementService {

    private ListElementDao listElementDao= new ListElementDao();

    public ListElementService() {
    }

    public ListElement findListElement(int id) {
        return listElementDao.findById(id);
    }

    public void saveListElement (ListElement listElement) {
        listElementDao.save(listElement);
    }

    public void updateListElement (ListElement listElement) {
        listElementDao.update(listElement);
    }

    public void deleteListElement (ListElement listElement) {
        listElementDao.delete(listElement);
    }

    public List<ListElement> findAllListElements() {
        return listElementDao.findAll();
    }
}
