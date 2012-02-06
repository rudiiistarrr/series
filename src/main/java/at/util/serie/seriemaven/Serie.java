/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package at.util.serie.seriemaven;

import java.io.File;

/**
 *
 * @author Rudolf Kubicz <r.kubicz@chello.at>
 */
public class Serie{

    private File file;
    private String id;
    private String path;
    private String title;
    private String season;
    private String episode;
    private String rating;
    private boolean imbd_flag;
    private boolean hasCategory = false;

    public Serie() {
    }

    public Serie(String id, String title, String rating, boolean flag) {
        this.id = id;
        this.title = title;
        this.rating = rating;
        this.imbd_flag = flag;
    }
    
    public Serie(Serie sub) {
        this.id = sub.getId();
        this.title = sub.getTitle();
        this.season = sub.getSeason();
        this.episode = sub.getEpisode();
        this.file = sub.getFile();
        this.hasCategory = sub.hasCategory();
        this.path = sub.getPath();
        this.rating = sub.getRating();
        this.imbd_flag = sub.isImbd_flag();
    }

    public String getEpisode() {
        return episode;
    }

    public void setEpisode(String episode) {
        this.episode = episode;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title.substring(0, 1).toUpperCase() + title.substring(1);
    }

    public boolean isImbd_flag() {
        return imbd_flag;
    }

    public void setImbd_flag(boolean imbd_flag) {
        this.imbd_flag = imbd_flag;
    }

    public boolean hasCategory() {
        return hasCategory;
    }

    public void setHasCategory(boolean hasCategory) {
        this.hasCategory = hasCategory;
    }

}
