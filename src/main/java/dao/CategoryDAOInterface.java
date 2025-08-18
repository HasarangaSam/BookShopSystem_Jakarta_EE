package dao;

import model.Category;
import java.util.List;

public interface CategoryDAOInterface {

    void addCategory(Category category);

    List<Category> getAllCategories();

    Category getCategoryById(int id);

    void updateCategory(Category category);

    void deleteCategory(int id);
}
