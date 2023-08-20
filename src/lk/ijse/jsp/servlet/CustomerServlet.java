package lk.ijse.jsp.servlet;


import javax.json.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;


@WebServlet(urlPatterns = {"/pages/customer"})
public class CustomerServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/company", "root", "1234");
            PreparedStatement pstm = connection.prepareStatement("select * from Customer");
            ResultSet rst = pstm.executeQuery();
             resp.addHeader("Content-Type","application/json");

            JsonArrayBuilder allCustomers = Json.createArrayBuilder();//create array
            while (rst.next()) {
                String id = rst.getString(1);
                String name = rst.getString(2);
                String address = rst.getString(3);
                String salary = rst.getString(4);

                JsonObjectBuilder customerObject = Json.createObjectBuilder();//create object
                customerObject.add("id", id);
                customerObject.add("name", name);
                customerObject.add("address", address);
                customerObject.add("salary", salary);
                allCustomers.add(customerObject.build());
            }
               resp.getWriter().print(allCustomers.build());

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String cusID = req.getParameter("cusID");
        String cusName = req.getParameter("cusName");
        String cusAddress = req.getParameter("cusAddress");
        String cusSalary = req.getParameter("cusSalary");

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/company", "root", "1234");

            PreparedStatement pstm = connection.prepareStatement("insert into Customer values(?,?,?,?)");
                    pstm.setObject(1, cusID);
                    pstm.setObject(2, cusName);
                    pstm.setObject(3, cusAddress);
                    pstm.setObject(4, cusSalary);
                    resp.addHeader("Content-Type","application/json");
                    if (pstm.executeUpdate() > 0) {
                            JsonObjectBuilder response = Json.createObjectBuilder();//create object
                            response.add("state", "OK");
                            response.add("message", "Successfully Added....!");
                            response.add("data", "");
                            resp.getWriter().print(response.build());
                        }

        } catch (ClassNotFoundException | SQLException e) {
            JsonObjectBuilder response = Json.createObjectBuilder();//create object
            response.add("state", "Error");
            response.add("message", e.getMessage());
            response.add("data", "");
            resp.setStatus(400);
            resp.getWriter().print(response.build());
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String cusID = req.getParameter("cusID");
        String cusName = req.getParameter("cusName");
        String cusAddress = req.getParameter("cusAddress");
        String cusSalary = req.getParameter("cusSalary");

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/company", "root", "1234");

            PreparedStatement pstm3 = connection.prepareStatement("update Customer set name=?,address=?,salary=? where id=?");
                    pstm3.setObject(4, cusID);
                    pstm3.setObject(1, cusName);
                    pstm3.setObject(2, cusAddress);
                    pstm3.setObject(3, cusSalary);
                    resp.addHeader("Content-Type","application/json");
                    if (pstm3.executeUpdate() > 0) {
                        JsonObjectBuilder response = Json.createObjectBuilder();//create object
                        response.add("state", "OK");
                        response.add("message", "Successfully Updated....!");
                        response.add("data", "");
                        resp.getWriter().print(response.build());
                    }

        } catch (ClassNotFoundException | SQLException e) {
//            e.printStackTrace();
                        JsonObjectBuilder response = Json.createObjectBuilder();//create object
                        response.add("state", "Error");
                        response.add("message", e.getMessage());
                        response.add("data", "");
                        resp.setStatus(400);
                        resp.getWriter().print(response.build());

        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String cusID = req.getParameter("cusID");

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/company", "root", "1234");

            PreparedStatement pstm2 = connection.prepareStatement("delete from Customer where id=?");
                    pstm2.setObject(1, cusID);
                    resp.addHeader("Content-Type","application/json");
                    if (pstm2.executeUpdate() > 0) {
                        JsonObjectBuilder response = Json.createObjectBuilder();//create object
                        response.add("state", "OK");
                        response.add("message", "Successfully Deleted....!");
                        response.add("data", "");
                        resp.getWriter().print(response.build());
                    }

        } catch (ClassNotFoundException | SQLException e) {
//            e.printStackTrace();
            JsonObjectBuilder response = Json.createObjectBuilder();//create object
            response.add("state", "Error");
            response.add("message", e.getMessage());
            response.add("data", "");
            resp.setStatus(400);
            resp.getWriter().print(response.build());
        }
    }

}
