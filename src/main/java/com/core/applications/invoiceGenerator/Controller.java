package com.core.applications.invoiceGenerator;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

  @RequestMapping("/")
  public String get(){
    return "Nidhi";
  }

}
