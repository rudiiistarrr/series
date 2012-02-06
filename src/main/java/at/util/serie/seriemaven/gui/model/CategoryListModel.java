/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package at.util.serie.seriemaven.gui.model;

import at.util.serie.seriemaven.Category;
import java.util.List;
import javax.swing.AbstractListModel;

/**
 *
 * @author RUDI
 */
public class CategoryListModel extends AbstractListModel  {
    
    private List<Category> categories;

    public CategoryListModel(List<Category> categories) {
        this.categories = categories;
    }

    public int getSize() {
        return categories.size();
    }

    public Object getElementAt(int index) {
        return categories.get(index);
    }
    
}
