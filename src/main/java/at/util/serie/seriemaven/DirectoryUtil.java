/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package at.util.serie.seriemaven;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Rudolf Kubicz <r.kubicz@chello.at>
 */
public class DirectoryUtil {

    List<File> fileList;
    FileFilter directoryFilter;
    FilenameFilter fileFilter;
    int counter = 0;

    public DirectoryUtil() {
        directoryFilter = new DirectoryFilter();
        fileFilter = new _FileNameFilter();
        fileList = new ArrayList<File>();
    }

    public List<File> listDirectories(String path) {
        File file = new File(path);
        File[] directoryarray = file.listFiles(directoryFilter);
        File[] filearray = file.listFiles(fileFilter);

        if (directoryarray != null) {
            List<File> directories = Arrays.asList(directoryarray);
            for (File f : directories) {
                listSub(f);
            }
        }

        if (filearray != null) {
            List<File> files = Arrays.asList(filearray);
            for (File f : files) {
                fileList.add(f);
                System.out.println(f.getAbsolutePath());
            }
        }
        System.out.println("FileListSize: " + fileList.size());
        return fileList;
    }

    private void listSub(File file) {
        File[] subarray = file.listFiles(directoryFilter);
        File[] filearray = file.listFiles(fileFilter);

        if (subarray != null) {
            List<File> subdirectories = Arrays.asList(subarray);
            for (File f : subdirectories) {
                listSub(f);
            }
        }

        if (filearray != null) {
            List<File> files = Arrays.asList(filearray);
            for (File f : files) {
                fileList.add(f);
                System.out.println(f.getAbsolutePath());
            }
        }
    }

    public class DirectoryFilter implements FileFilter {

        @Override
        public boolean accept(File pathname) {
            return pathname.isDirectory() && !pathname.getName().startsWith("$");
        }
    };

    public class _FileFilter implements FileFilter {

        @Override
        public boolean accept(File pathname) {
            return pathname.isFile();
        }
    };

    public class _FileNameFilter implements FilenameFilter {

        @Override
        public boolean accept(File dir, String name) {
            if ((name.endsWith("avi")) || (name.endsWith("mkv"))) {
                if (!name.contains("sample")) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
    }
}
