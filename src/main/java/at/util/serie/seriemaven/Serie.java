/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package at.util.serie.seriemaven;

import java.io.File;
import java.io.Serializable;

/**
 *
 * @author Rudolf Kubicz <r.kubicz@chello.at>
 */
public class Serie implements Serializable {

    private File file;
    private String id;
    private String path;
    private String title;
    private String season;
    private String episode;
    private String rating;
    private boolean imbd_flag;

    public Serie() {
    }

    public Serie(String id, String title, String rating, boolean flag) {
        this.id = id;
        this.title = title;
        this.rating = rating;
        this.imbd_flag = flag;
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
        this.title = title;
    }

    public boolean isImbd_flag() {
        return imbd_flag;
    }

    public void setImbd_flag(boolean imbd_flag) {
        this.imbd_flag = imbd_flag;
    }
}
