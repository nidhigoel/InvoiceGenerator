package com.core.applications.invoiceGenerator;


import java.util.Map;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GenerateInvoiceController {

  @RequestMapping(value = "/generate_invoice")
  public String getInvoice(@RequestParam Map<String, String> requestParams) {
    return "Generated Invoice";
  }
}
