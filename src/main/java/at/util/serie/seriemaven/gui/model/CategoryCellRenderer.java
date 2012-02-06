/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package at.util.serie.seriemaven.gui.model;

import at.util.serie.seriemaven.Category;
import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;

/**
 *
 * @author RUDI
 */
public class CategoryCellRenderer extends DefaultListCellRenderer {

    Category category = null;

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

        if (value != null) {
            if (!(value instanceof Category)) {
                throw new IllegalArgumentException("Cell Renderer Error");
            }
        }
        JLabel cell = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        
        category = (Category) value;
        
        if (category != null) {
            cell.setText(category.getName() + "(" + category.getSerien().size() + ")");
        } else {
            cell.setText("");
        }

        return cell;
    }
}
