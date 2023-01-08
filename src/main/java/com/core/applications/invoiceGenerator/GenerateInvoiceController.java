package com.core.applications.invoiceGenerator;


import com.core.applications.invoiceGenerator.report.InvoiceService;
import com.core.applications.invoiceGenerator.report.model.InvoiceModel;
import java.io.IOException;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GenerateInvoiceController {

  @GetMapping(value = "/generate_invoice")
  public String getInvoice(@RequestParam Map<String, String> requestParams) throws IOException {

    InvoiceService invoiceService = new InvoiceService();
    invoiceService.generateInvoice(InvoiceModel.builder()
        .header_text("Header Value")
        .invoice_from("invoice_from_value")
        .invoice_date("invoice_date_value")
        .invoice_number("invoice_number_value")
        .build());

    return "Generated Invoice";
  }
}
