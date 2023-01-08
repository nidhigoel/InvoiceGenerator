package com.core.applications.invoiceGenerator.report.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class InvoiceTableModel {

  private String invoice_table_header_1;
  private String invoice_table_header_2;
  private String invoice_table_data_col_1;
  private String invoice_table_data_col_2;
  private String total_text_col;
  private String total_value_col;
}
