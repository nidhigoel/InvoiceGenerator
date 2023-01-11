package com.core.applications.invoiceGenerator;


import com.core.applications.invoiceGenerator.report.InvoiceService;
import com.core.applications.invoiceGenerator.report.model.InvoiceModel;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import javax.validation.Valid;
import lombok.extern.log4j.Log4j2;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
public class GenerateInvoiceController {

  @Autowired
  InvoiceService invoiceService;

  @GetMapping(value = "/generate_invoice")
  public ResponseEntity<byte[]> getInvoice(@Valid InvoiceModel invoiceModel)
      throws IOException, JRException {
    log.info(invoiceModel.toString());
    File file = invoiceService.generateInvoice(invoiceModel);

    byte[] contents = Files.readAllBytes(file.toPath());

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_PDF);
    // Here you have to set the actual filename of your pdf
    String filename = "output.pdf";
    headers.setContentDispositionFormData(filename, filename);
    headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
    ResponseEntity<byte[]> response = new ResponseEntity<>(contents, headers, HttpStatus.OK);
    return response;
  }
}
