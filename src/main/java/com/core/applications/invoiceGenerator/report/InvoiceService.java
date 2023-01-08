package com.core.applications.invoiceGenerator.report;

import com.core.applications.invoiceGenerator.report.model.InvoiceModel;
import com.core.applications.invoiceGenerator.report.model.InvoiceTableModel;
import com.google.gson.Gson;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.ui.jasperreports.JasperReportsUtils;

@Log4j2
@Service
public class InvoiceService {
  @Autowired
  private Gson gson;
  private final String invoice_template_path = "/jasper/invoice_report.jrxml";
  private static final String logo_path = "/jasper/ic_logo_oneattendance2.png";

  public File generateInvoice(Map<String,String> invoiceDetails) throws IOException, JRException {
    log.info("Inside generate invoice");
    File pdfFile = File.createTempFile("my-invoice", ".pdf");

    String invoiceJson = gson.toJson(invoiceDetails);
    InvoiceModel invoiceModel = gson.fromJson(invoiceJson, InvoiceModel.class);
    InvoiceTableModel invoiceTableEntry = gson.fromJson(invoiceJson, InvoiceTableModel.class);

    List<InvoiceTableModel> invoiceTableEntries = new ArrayList<>();
    invoiceTableEntries.add(invoiceTableEntry);
    // Initiate a FileOutputStream
    try (FileOutputStream pos = new FileOutputStream(pdfFile)) {
      // We will generate PDF here
      // Load the invoice jrxml template.
      final JasperReport report = loadTemplate();

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
      return pdfFile;
    } catch (final Exception e) {
      log.error(String.format("An error occured during PDF creation: %s", e));
      throw e;
    }
  }

  // Load invoice jrxml template
  private JasperReport loadTemplate() throws JRException {
    log.info(String.format("Invoice template path : %s", invoice_template_path));
    final InputStream reportInputStream = getClass().getResourceAsStream(invoice_template_path);
    final JasperDesign jasperDesign = JRXmlLoader.load(reportInputStream);
    return JasperCompileManager.compileReport(jasperDesign);
  }
}
