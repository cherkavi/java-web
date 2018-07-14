package com.cherkashyn.vitalii.export.documentscanner.persistent.fs;

import com.cherkashyn.vitalii.export.documentscanner.persistent.nosql.Order;
import com.cherkashyn.vitalii.export.documentscanner.persistent.nosql.OrderFileItem;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class OrderFS{

    String rootFolder;

    public OrderFS(String rootFolder){
        this.rootFolder = FSUtils.correctFolderPath(rootFolder);
    }

    public File getRealFile(Order order, OrderFileItem fileItem){
        return new File(FSUtils.getOrderFolderPath(this.rootFolder, order)+ fileItem.name);
    }

    public void cleanUp(Order order) {
        FileSystemUtils.deleteRecursively(FSUtils.getOrderFolder(this.rootFolder, order));
    }
}

class FSUtils {

    final static String SUFFIX = "/";

    public static String correctFolderPath(String rootFolder) {
        String path = StringUtils.cleanPath(rootFolder);
        if(!path.endsWith(SUFFIX)){
            path = path+SUFFIX;
        }
        return path;
    }

    public static String getOrderFolderPath(String rootFolder, Order order) {
        return rootFolder+SUFFIX+order.orderId+SUFFIX;
    }

    public static File getOrderFolder(String rootFolder, Order order) {
        return new  File(getOrderFolderPath(rootFolder, order));
    }
}
