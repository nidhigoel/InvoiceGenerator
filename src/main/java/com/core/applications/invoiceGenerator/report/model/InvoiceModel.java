package com.core.applications.invoiceGenerator.report.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class InvoiceModel {

  private String header_logo_url;
  private String header_text;
  private String invoice_from;
  private String invoice_number;
  private String invoice_date;
  private String invoice_to;
//  private String invoice_table_header_1;
//  private String invoice_table_header_2;
//  private String invoice_table_data_col_1;
//  private String invoice_table_data_col_2;
//  private String total_text_col;
//  private String total_value_col;
  private String amount_in_words;
  private String pay_to;
  private String footer_logo_url;
  private String footer_text;

}
