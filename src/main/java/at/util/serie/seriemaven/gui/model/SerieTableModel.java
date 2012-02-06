/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package at.util.serie.seriemaven.gui.model;

import at.util.serie.seriemaven.Serie;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author RUDI
 */
public class SerieTableModel extends AbstractTableModel {

    private List<Serie> series;

    public SerieTableModel(List<Serie> series) {
        this.series = series;
    }

    public int getRowCount() {
        return series.size();
    }

    public int getColumnCount() {
        return 5;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return series.get(rowIndex).getTitle();
            case 1:
                return series.get(rowIndex).getSeason();
            case 2:
                return series.get(rowIndex).getEpisode();
            case 3:
                return series.get(rowIndex).getPath();
            case 4:
                return series.get(rowIndex).getRating();
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "Title";
            case 1:
                return "Season";
            case 2:
                return "Episode";
            case 3:
                return "Path";
            case 4:
                return "Rating";
            default:
                return "Error";
        }
    }
}
