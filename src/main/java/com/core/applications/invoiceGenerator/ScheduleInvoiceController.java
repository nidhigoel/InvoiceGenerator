package com.core.applications.invoiceGenerator;


import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ScheduleInvoiceController {

  @GetMapping(value = "/register_automated_invoice")
  public String register_invoice(@RequestParam Map<String, String> requestParams) {
    return "Registered Invoice";
  }

  @GetMapping(value = "/unregister_automated_invoice")
  public String unregister_invoice(@RequestParam Map<String, String> requestParams) {
    return "Unregistered Invoice";
  }
}
