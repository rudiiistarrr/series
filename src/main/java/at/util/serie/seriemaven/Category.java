/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package at.util.serie.seriemaven;

import java.util.List;

/**
 *
 * @author RUDI
 */
public class Category {
    
    private String name;
    private List<Serie> serien;

    public Category() {
    }

    public Category(String name, List<Serie> serien) {
        this.name = name;
        this.serien = serien;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Serie> getSerien() {
        return serien;
    }

    public void setSerien(List<Serie> serien) {
        this.serien = serien;
    }
    
    
    
}
