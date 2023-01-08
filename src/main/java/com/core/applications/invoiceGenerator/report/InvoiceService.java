package com.core.applications.invoiceGenerator.report;

import com.core.applications.invoiceGenerator.report.model.AddressModel;
import com.core.applications.invoiceGenerator.report.model.InvoiceModel;
import com.core.applications.invoiceGenerator.report.model.InvoiceTableModel;
import com.core.applications.invoiceGenerator.report.model.OrderEntryModel;
import com.core.applications.invoiceGenerator.report.model.OrderModel;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.log4j.Log4j2;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import org.springframework.ui.jasperreports.JasperReportsUtils;

@Log4j2
public class InvoiceService {

  private final String invoice_template_path = "/jasper/invoice_report.jrxml";
  private static final String logo_path = "/jasper/ic_logo_oneattendance2.png";


  public void generateInvoice(InvoiceModel model) throws IOException {
    log.info("Inside generate invoice");
    File pdfFile = File.createTempFile("my-invoice", ".pdf");

    // Initiate a FileOutputStream
    try (FileOutputStream pos = new FileOutputStream(pdfFile)) {
      // We will generate PDF here
      // Load the invoice jrxml template.
      final JasperReport report = loadTemplate();

      List<InvoiceTableModel> invoiceTableEntries = new ArrayList<>();
      InvoiceTableModel entry = InvoiceTableModel.builder()
          .invoice_table_header_1("DESCRIPTION of Service")
          .invoice_table_header_2("AMOUNT\n(Rs.)")
          .invoice_table_data_col_1(
              "Services towards market research and data analysis for the Month\n of December 2022")
          .invoice_table_data_col_2("32000.00")
          .total_text_col("TOTAL(Rs.)")
          .total_value_col("32000.00")
          .build();
      invoiceTableEntries.add(entry);
      InvoiceModel invoiceModel = InvoiceModel.builder()
          .header_text("Tax Services Test")
          .header_logo_url(
              "https://firebasestorage.googleapis.com/v0/b/smmconeattendance.appspot.com/o/temp%2Fic_logo_oneattendance2.png?alt=media&token=b2d816b7-a617-45be-968a-6cb9c90a8ade")
          .header_text("Tax invoice")
          .invoice_from(
              "From,\nTanuka Banerjee\nMajheraut, Jagadishpur\nHowrah 711104\nPAN: XXXXXXXX\n")
          .invoice_number("007")
          .invoice_date("06/01/2023")
          .invoice_to(
              "To,\nSAURAV MAJUMDER MANAGEMENT\nCONSULTING\nUnnayan,Bengal Ambuja Commercial Complex\nXXXXXXXXX,Survey Park,\nKolkata,700075\nGSTN: XXXXXXXXXXXXX\nPAN: XXXXXXXXX\nEmail: saurav @smmc.net.in\n")
          .invoice_table_header_1("DESCRIPTION of Service")
          .invoice_table_header_2("AMOUNT\n(Rs.)")
          .invoice_table_data_col_1(
              "Services towards market research and data analysis for the Month\n of December 2022")
          .invoice_table_data_col_2("32000.00 ")
          .total_text_col("TOTAL(Rs.)")
          .total_value_col("32000.00")
          .amount_in_words("Thirty Two Thousand Only")
          .pay_to(
              "Name: Central Bank of India\nBank/Branch: Uttarpara,Hoogly\nA/c.No.: XXXXXXXXX\nIFSC Code: XXXXXXXXX\n")
          .footer_logo_url(
              "https://firebasestorage.googleapis.com/v0/b/smmconeattendance.appspot.com/o/temp%2Fic_logo_oneattendance2.png?alt=media&token=b2d816b7-a617-45be-968a-6cb9c90a8ade")
          .footer_text("Thank You")
          .build();
      // Create an empty datasource.
      final JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(
          invoiceTableEntries);
      final JRBeanCollectionDataSource dataSource1 = new JRBeanCollectionDataSource(
          Collections.singletonList(invoiceModel));

      // Create parameters map.
      final Map<String, Object> parameters = new HashMap<>();
      parameters.put("InvoiceBeanParam", dataSource);

      // Render the PDF file
      JasperReportsUtils.renderAsPdf(report, parameters, dataSource1, pos);
      log.info("Rendered");
      log.info(pdfFile.getAbsolutePath());

    } catch (final Exception e) {
      log.error(String.format("An error occured during PDF creation: %s", e));
    }
  }

  // Load invoice jrxml template
  private JasperReport loadTemplate() throws JRException {

    log.info(String.format("Invoice template path : %s", invoice_template_path));

    final InputStream reportInputStream = getClass().getResourceAsStream(invoice_template_path);
    final JasperDesign jasperDesign = JRXmlLoader.load(reportInputStream);

    return JasperCompileManager.compileReport(jasperDesign);
  }

  private Map<String, Object> parameters(OrderModel order) {
    Map<String, Object> parameters = new HashMap<>();
    order = OrderModel.builder().code("tempCode").address(AddressModel.builder()
            .firstName("Aryan")
            .town("Hyderabad")
            .country("India")
            .postalCode("500032")
            .streetName("Local street")
            .lastName("Raj").build())
        .entries(Arrays.asList(OrderEntryModel.builder()
            .price(100.00)
            .quantity(2)
            .productName("Alpha gel")
            .build())).build();
    parameters.put("logo", getClass().getResourceAsStream(logo_path));
    parameters.put("order", order);
    return parameters;
  }
}
