package com.hustcaid.myshoppingmanagement.webview;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.servletunit.ServletRunner;
import com.meterware.servletunit.ServletUnitClient;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/******************************************************************************
 *  @author Caid Wang
 *  @date 2020/3/21   
 *
 ******************************************************************************/
class CashPageTest {

    @Test
    void doGet() throws IOException, SAXException {
        File config = new File("web/WEB-INF/web.xml");
        ServletRunner sr = new ServletRunner(config);

        sr.registerServlet("cash", CashPage.class.getName());
        ServletUnitClient sc = sr.newClient();
        WebRequest request = new GetMethodWebRequest("http://localhost/cash?session-id=123");
        WebResponse response = sc.getResponse(request);
        System.out.println(response.getText());
    }

    @Test
    void doPost() {
    }
}