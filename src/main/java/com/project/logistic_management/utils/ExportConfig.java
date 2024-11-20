package com.project.logistic_management.utils;

import com.project.logistic_management.entity.OutboundTransaction;
import com.project.logistic_management.entity.OutboundTransactionDetail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExportConfig {

    private int sheetIndex;

    private int startRow;

    private Class dataClazz;

    private List<CellConfig> cellExportConfigList;

    public static final ExportConfig outboundTransactionExport;
    static {
        outboundTransactionExport = new ExportConfig();
        outboundTransactionExport.setSheetIndex(0);
        outboundTransactionExport.setStartRow(1);
        outboundTransactionExport.setDataClazz(OutboundTransaction.class);
        List<CellConfig> outboundTransactionCellConfig = new ArrayList<>();
        outboundTransactionCellConfig.add(new CellConfig(0, "id"));
        outboundTransactionCellConfig.add(new CellConfig(1, "userId"));
        outboundTransactionCellConfig.add(new CellConfig(2, "scheduleId"));
        outboundTransactionCellConfig.add(new CellConfig(3, "approvedTime"));
        outboundTransactionCellConfig.add(new CellConfig(4, "status"));
        outboundTransactionCellConfig.add(new CellConfig(5, "createdAt"));

        outboundTransactionExport.setCellExportConfigList(outboundTransactionCellConfig);
    }

    public static final ExportConfig outboundTransactionDetailExport;
    static {
        outboundTransactionDetailExport = new ExportConfig();
        outboundTransactionDetailExport.setSheetIndex(0);
        outboundTransactionDetailExport.setStartRow(1);
        outboundTransactionDetailExport.setDataClazz(OutboundTransactionDetail.class);
        List<CellConfig> outboundTransactionDetailCellConfig = new ArrayList<>();
        outboundTransactionDetailCellConfig.add(new CellConfig(0, "id"));
        outboundTransactionDetailCellConfig.add(new CellConfig(1, "goodsId"));
        outboundTransactionDetailCellConfig.add(new CellConfig(2, "outboundTransactionId"));
        outboundTransactionDetailCellConfig.add(new CellConfig(3, "quantity"));
        outboundTransactionDetailCellConfig.add(new CellConfig(4, "price"));
        outboundTransactionDetailCellConfig.add(new CellConfig(5, "destination"));

        outboundTransactionDetailExport.setCellExportConfigList(outboundTransactionDetailCellConfig);
    }
}
