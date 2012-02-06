/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package at.util.serie.seriemaven;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Rudolf Kubicz <r.kubicz@chello.at>
 */
@Repository
public class IMDB {

    private Serie serie = null;
    private String name = null;
    private String title = null;
    private String season = null;
    private String episode = null;
    private final String CONFIG = "series.conf";
    private String id;
    private String rating;
    private List<Serie> series;
    private List<Serie> configSeries;
    private Pattern pattern = Pattern.compile("S[0-9]+E[0-9]+", Pattern.CASE_INSENSITIVE);

    public IMDB() {
    }

    @PostConstruct
    public void init() {
        series = new ArrayList<Serie>();
        configSeries = readSeriesFromConfig();
    }

    public List<Serie> getSeries(List<File> files) {
        Serie sserie = null;
        for (File file : files) {
            sserie = getSerieFromFile(file, true);
            if (sserie != null) {
                series.add(new Serie(sserie));
            }
        }
        return series;
    }

    public Serie getSerieFromFile(File file, boolean flag) {

        serie = new Serie();

        if (flag == false) {
            name = file.getParentFile().getName().replaceAll("[.]", " ");
        } else {
            name = file.getName().replaceAll("[.]", " ");
        }

        Matcher matcher = pattern.matcher(name);

        if (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();
            int split = name.indexOf("E", start);

            if (split == -1) {
                split = name.indexOf("e", start);
            }
            try {
                title = name.substring(0, start - 1);
                season = name.substring(start + 1, split);
                episode = name.substring(split + 1, end);
            } catch (StringIndexOutOfBoundsException ex) {
                return null;
            }

            if ((serie = checkSerieFromConfig(title)) == null) {
                if ((serie = checkSerieFromIMDB(title)) == null) {
                    return null;
                }
            }
            serie.setTitle(title);
            serie.setSeason(season);
            serie.setEpisode(episode);
            serie.setFile(file);
            serie.setPath(file.getAbsolutePath());

            return serie;
        } else {
            if (flag == true) {
                if ((serie = getSerieFromFile(file, false)) != null) {
                    return serie;
                }
            }

            Pattern otherPattern = Pattern.compile("[0-9]+");
            matcher = otherPattern.matcher(name);

            if (matcher.find()) {
                title = name.substring(0, matcher.start());
                if ((serie = checkSerieFromIMDB(title)) != null) {
                    return serie;
                }
            }
        }
        return null;

    }

    private Serie checkSerieFromIMDB(String moviename) {

        URL url = null;
        Scanner sc = null;
        String apiurl = "http://www.deanclatworthy.com/imdb/";
        String dataurl = null;
        String retdata = null;
        InputStream is = null;
        DataInputStream dis = null;
        String title = moviename;
        String details[] = null;

        try {

            //Check if user has inputted nothing or blank
            if (moviename == null || moviename.equals("")) {
                System.out.println("No movie found");
                return null;
            }

            //Remove unwanted space from moviename yb trimming it
            moviename = moviename.trim();

            //Replacing white spaces with + sign as white spaces are not allowed in IMDB api
            moviename = moviename.replace(" ", "+");

            //Forming a complete url ready to send (type parameter can be JSON also)
            dataurl = apiurl + "?q=" + moviename + "&type=text";

            url = new URL(dataurl);

            is = url.openStream();
            dis = new DataInputStream(is);


            BufferedReader reader = new BufferedReader(new InputStreamReader(dis));

            //Reading data from url
            while ((retdata = reader.readLine()) != null) {
                //Indicates that movie does not exist in IMDB databse
                if (retdata.equals("error|Film not found")) {
                    System.out.println("No such movie found: " + moviename);
                    return null;
                }

                if (retdata.contains("code")) {
                    return null;
                }

                //Replacing | character with # character for spliting
                retdata = retdata.replace("|", "#");

                //Splitting up string by # character and storing output in details array
                details = retdata.split("#");

                if (details[1].equalsIgnoreCase("error")) {
                    return null;
                }

                //details[0] contains name of detail. e.g title,genre etc
                if (details[0].equalsIgnoreCase("rating")) {
                    rating = details[1];

                } else if (details[0].equalsIgnoreCase("imdbid")) {
                    id = details[1];
                }
            }
            return new Serie(id, title, rating, true);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        } finally {
            try {

                if (dis != null) {
                    dis.close();
                }

                if (is != null) {
                    is.close();
                }

                if (sc != null) {
                    sc.close();
                }
            } catch (Exception e2) {
                ;
            }
        }
    }

    private Serie checkSerieFromConfig(String title) {
        for (Serie serie : configSeries) {

            String serieTitle = serie.getTitle();

            if (serieTitle.equalsIgnoreCase(title)) {
                return serie;
            }

        }
        return null;
    }

    public boolean writeSeriesInConfig(List<Serie> series) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(CONFIG));
            for (Serie s : series) {
                writer.append(s.getTitle());
                writer.newLine();
                writer.flush();
            }
        } catch (IOException ex) {
            Logger.getLogger(IMDB.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            try {
                writer.close();
                return true;
            } catch (IOException ex) {
                Logger.getLogger(IMDB.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        }
    }

    public List<Serie> readSeriesFromConfig() {
        ArrayList<Serie> series = new ArrayList<Serie>();
        BufferedReader reader = null;
        String title;
        Serie serie;
        try {
            reader = new BufferedReader(new FileReader(CONFIG));
            try {
                while ((title = reader.readLine()) != null) {
                    serie = new Serie();
                    serie.setTitle(title);
                    series.add(serie);
                }
            } catch (EOFException ex) {
                System.out.println("ENDE");
            } catch (IOException ex) {
                Logger.getLogger(IMDB.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(IMDB.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                reader.close();
            } catch (IOException ex) {
                Logger.getLogger(IMDB.class.getName()).log(Level.SEVERE, null, ex);
            }

            return series;
        }
    }

    /*
     * Getter for Files which are no Series
     */
    public List<Category> listSeriesInCategories(List<Serie> series) {
        List<Category> categories = new ArrayList<Category>();
        Category category = null;

        for (Serie serie : series) {
            category = new Category();
            category.setSerien(new ArrayList<Serie>());          
            for (Serie sub : series) {
                if (serie.getTitle().toLowerCase().contentEquals(sub.getTitle().toLowerCase()) && !sub.hasCategory()) {
                    sub.setHasCategory(true);
                    category.getSerien().add(sub);
                }
            }
            if (category.getSerien().size() > 0) {
                category.setName(serie.getTitle());
                categories.add(category);
            }
        }

        return categories;
    }
}
