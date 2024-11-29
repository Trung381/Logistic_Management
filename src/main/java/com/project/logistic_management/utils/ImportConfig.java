package com.project.logistic_management.utils;

import com.project.logistic_management.dto.request.InboundTransactionDTO;
import com.project.logistic_management.dto.request.InboundTransactionDetailDTO;
import com.project.logistic_management.dto.request.outbound.OutboundTransactionDTO;
import com.project.logistic_management.dto.request.outbound.OutboundTransactionDetailDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImportConfig {

    private int sheetIndex;

    private int headerIndex;

    private int startRow;

    private Class dataClazz;

    private List<CellConfig> cellImportConfigs;


    public static final ImportConfig outboundTransactionImport;
    static{
        outboundTransactionImport = new ImportConfig();
        outboundTransactionImport.setSheetIndex(0);
        outboundTransactionImport.setHeaderIndex(0);
        outboundTransactionImport.setStartRow(1);
        outboundTransactionImport.setDataClazz(OutboundTransactionDTO.class);
        List<CellConfig> outboundTransactionImportCellConfigs = new ArrayList<>();

        outboundTransactionImportCellConfigs.add(new CellConfig(0, "userId"));
        outboundTransactionImportCellConfigs.add(new CellConfig(1, "scheduleId"));

        outboundTransactionImport.setCellImportConfigs(outboundTransactionImportCellConfigs);
    }

    public static final ImportConfig outboundTransactionDetailImport;
    static{
        outboundTransactionDetailImport = new ImportConfig();
        outboundTransactionDetailImport.setSheetIndex(0);
        outboundTransactionDetailImport.setHeaderIndex(0);
        outboundTransactionDetailImport.setStartRow(1);
        outboundTransactionDetailImport.setDataClazz(OutboundTransactionDetailDTO.class);
        List<CellConfig> outboundTransactionDetailImportCellConfigs = new ArrayList<>();

        outboundTransactionDetailImportCellConfigs.add(new CellConfig(0, "goodsId"));
        outboundTransactionDetailImportCellConfigs.add(new CellConfig(1, "quantity"));
        outboundTransactionDetailImportCellConfigs.add(new CellConfig(2, "price"));
        outboundTransactionDetailImportCellConfigs.add(new CellConfig(3, "destination"));

        outboundTransactionDetailImport.setCellImportConfigs(outboundTransactionDetailImportCellConfigs);
    }

    public static final ImportConfig inboundTransactionImport;
    static{
        inboundTransactionImport = new ImportConfig();
        inboundTransactionImport.setSheetIndex(0);
        inboundTransactionImport.setHeaderIndex(0);
        inboundTransactionImport.setStartRow(1);
        inboundTransactionImport.setDataClazz(InboundTransactionDTO.class);
        List<CellConfig> inboundTransactionImportCellConfigs = new ArrayList<>();
        inboundTransactionImportCellConfigs.add(new CellConfig(0, "userId"));
        inboundTransactionImportCellConfigs.add(new CellConfig(1, "intakeTime"));

        inboundTransactionImport.setCellImportConfigs(inboundTransactionImportCellConfigs);
    }

    public static final ImportConfig inboundTransactionDetailImport;
    static{
        inboundTransactionDetailImport = new ImportConfig();
        inboundTransactionDetailImport.setSheetIndex(0);
        inboundTransactionDetailImport.setHeaderIndex(0);
        inboundTransactionDetailImport.setStartRow(1);
        inboundTransactionDetailImport.setDataClazz(InboundTransactionDetailDTO.class);
        List<CellConfig> inboundTransactionDetailImportCellConfigs = new ArrayList<>();

        inboundTransactionDetailImportCellConfigs.add(new CellConfig(0, "inboundTransactionId"));
        inboundTransactionDetailImportCellConfigs.add(new CellConfig(1, "goodsId"));
        inboundTransactionDetailImportCellConfigs.add(new CellConfig(2, "origin"));
        inboundTransactionDetailImportCellConfigs.add(new CellConfig(3, "quantity"));

        inboundTransactionDetailImport.setCellImportConfigs(inboundTransactionDetailImportCellConfigs);
    }

}
