package br.com.prodap.taurusmobile.OpenFile;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class FileItem
{
    FileItem(String absolutePath, boolean isDirectory)
    {
        this.absolutePath = absolutePath;
        this.isDirectory = isDirectory;
        this.name = absolutePath.substring(absolutePath.lastIndexOf(File.separator)+1);
    }

    String absolutePath;
    String name;
    boolean isDirectory;

    List<FileItem> listChildren()
    {
        List<FileItem> list = new ArrayList<>();
        File parent = new File(absolutePath);

        if(parent.listFiles()==null)return list;

        File[] fileArr =parent.listFiles();

        Arrays.sort(fileArr, new Comparator<File>()
        {
            @Override
            public int compare(File lhs, File rhs)
            {
                return lhs.getName().toLowerCase().compareTo(rhs.getName().toLowerCase());
            }
        });

        for(File file:fileArr)
        {
            if(!file.getName().startsWith("."))
            {
                list.add(new FileItem(file.getAbsolutePath(), file.isDirectory()));
            }
        }
        return list;
    }
}
