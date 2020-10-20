import com.opencsv.CSVReader;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/servlet")
@MultipartConfig
public class Servlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException { ;

        LocalDateTime fromDate = LocalDateTime.parse(request.getParameter("fromDate"));
        LocalDateTime toDate = LocalDateTime.parse(request.getParameter("toDate"));
        String merchant = request.getParameter("merchant");
        Part part = request.getPart("file");
        PrintWriter printWriter = response.getWriter();
        float averageAmount = 0;

        CSVReader reader = new CSVReader(new InputStreamReader(part.getInputStream()), ',');
        List<String[]> strings = reader.readAll();
        List<String[]> listHelper = new ArrayList<>();
        if (strings.get(0)[0].toLowerCase().equals("id"))
            strings.remove(0);

        for (String[] data : strings){
            if (!data[5].equals("")) {
                listHelper.add(data);
                for (String[] dataRemove : strings) {
                    if (dataRemove[0].equals(data[5]))
                        listHelper.add(dataRemove);
                }
            }
        }
        for (String[] data : listHelper)
            strings.remove(data);
        listHelper.clear();
        for (String[] data : strings) {
                LocalDateTime dateTime = LocalDateTime.parse(data[1], DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));
                if (data[3].equals(merchant) && dateTime.isBefore(toDate) && dateTime.isAfter(fromDate))
                    listHelper.add(data);
        }
        for (String[] data : listHelper)
            averageAmount += Float.parseFloat(data[2]);

        printWriter.println("Number of transactions = "+listHelper.size());
        printWriter.println("Average Transaction Value = "+averageAmount/listHelper.size());
        printWriter.close();
        reader.close();
    }
}
