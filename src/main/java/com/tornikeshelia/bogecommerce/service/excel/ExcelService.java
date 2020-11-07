package com.tornikeshelia.bogecommerce.service.excel;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface ExcelService {

    void generateExcel(HttpServletResponse response) throws IOException;

}
