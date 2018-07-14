package com.cherkashyn.vitalii.export.documentscanner.persistent.fs;

import com.cherkashyn.vitalii.export.documentscanner.persistent.nosql.Order;
import com.cherkashyn.vitalii.export.documentscanner.persistent.nosql.OrderFileItem;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OrderFSProcessorStorage extends OrderFSStorage{


    private final Optional<List<SetPostProcessor>> postProcessor;

    public OrderFSProcessorStorage(String rootFolder,
                                   SetPostProcessor ... postProcessor) {
        super(rootFolder);
        if(postProcessor!=null && postProcessor.length>0){
            this.postProcessor = Optional.of(Stream.of(postProcessor).collect(Collectors.toList()));
        }else{
            this.postProcessor = Optional.empty();
        }

    }

    @Override
    protected void moveFile(Order order, OrderFileItem fileItem, File source, File destination) {
        destination.getParentFile().mkdirs();
        super.moveFile(order, fileItem, source, destination);
        this.postProcessor.ifPresent(ppl->ppl.forEach(
                pp->pp.process(order, fileItem, destination)));
    }

    public void copyFileItem(OrderFSStorage sourceStorage, Order order, OrderFileItem fileItem) {
        File destination = this.getRealFile(order, fileItem);
        destination.getParentFile().mkdirs();

        File source = sourceStorage.getRealFile(order, fileItem);

        copyFile(order, fileItem, source, destination);
        this.postProcessor.ifPresent(ppl->ppl.forEach(
                pp->pp.process(order, fileItem, destination)));
    }
}
