package com.cherkashyn.vitalii.export.documentscanner.persistent.fs;

import com.cherkashyn.vitalii.export.documentscanner.persistent.nosql.Order;
import com.cherkashyn.vitalii.export.documentscanner.persistent.nosql.OrderFileItem;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileSystemUtils;

import java.io.File;
import java.io.IOException;

public class OrderFSStorage extends OrderFS{

    private static final Logger LOG = LoggerFactory.getLogger(OrderFSStorage.class);

    public OrderFSStorage(String rootFolder){
        super(rootFolder);
    }

    /**
     * setFileItemAll data info file system, remove from source
     * @param order - meta-information for saving
     * @param sourceStorage - where data will be removed
     */
    public void setFileItemAll(Order order, OrderFSStorage sourceStorage){
        // TODO need to archive all previous data
        FileSystemUtils.deleteRecursively(FSUtils.getOrderFolder(this.rootFolder, order));

        // setFileItemAll new
        order.files.forEach(fileItem->
                            moveFile(order, fileItem,
                                sourceStorage.getRealFile(order, fileItem),
                                getRealFile(order, fileItem))
        );
    }

    public void setFileItem(Order order, OrderFileItem fileItem, File sourceFileToBeMoved) {
        File destination = this.getRealFile(order, fileItem);
        moveFile(order, fileItem, sourceFileToBeMoved, destination);
    }


    protected void moveFile(Order order, OrderFileItem fileItem, File source, File destination) {
        try {
            FileUtils.moveFile(source, destination);
        } catch (IOException e) {
            new RuntimeException(String.format("can't move data from source %s to destination %s", source, destination), e);
        }
    }

    protected void copyFile(Order order, OrderFileItem fileItem, File source, File destination) {
        try {
            FileUtils.copyFile(source, destination);
        } catch (IOException e) {
            new RuntimeException(String.format("can't copy file from source %s to destination %s", source, destination), e);
        }
    }

}
