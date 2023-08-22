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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException { //QueryString Support,Formdata NotSupport,Json Support

//        JsonReader reader = Json.createReader(req.getReader());
//        JsonObject jsonObject = reader.readObject();
//        String cusID =jsonObject.getString("Id");
//        String cusName=jsonObject.getString("Name");
//        String cusAddress =jsonObject.getString("Address");
//        String cusSalary = jsonObject.getString("Salary");
//
//        System.out.println("DoGet "+cusID + ":" + cusName + ":" + cusAddress + ":"+ cusSalary);

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/company", "root", "1234");
            PreparedStatement pstm = connection.prepareStatement("select * from Customer");
            ResultSet rst = pstm.executeQuery();
             resp.addHeader("Content-Type","application/json");
               resp.addHeader("Access-Control-Allow-Origin","*");



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
//               resp.getWriter().print(allCustomers.build());

            JsonObjectBuilder response = Json.createObjectBuilder();//create object
            response.add("state", "OK");
            response.add("message", "Successfully Loaded....!");
            response.add("data", allCustomers.build());
            resp.getWriter().print(response.build());


        } catch (ClassNotFoundException | SQLException e) {
//            throw new RuntimeException(e);
            JsonObjectBuilder response = Json.createObjectBuilder();//create object
            response.add("state", "Error");
            response.add("message", e.getMessage());
            response.add("data", "");
            resp.setStatus(400);
            resp.getWriter().print(response.build());
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException { //QueryString Support,Formdata Support,Json Support
        String cusID = req.getParameter("cusID");
        String cusName = req.getParameter("cusName");
        String cusAddress = req.getParameter("cusAddress");
        String cusSalary = req.getParameter("cusSalary");

//        JsonReader reader = Json.createReader(req.getReader());
//        JsonObject jsonObject = reader.readObject();
//        String cusID =jsonObject.getString("Id");
//        String cusName=jsonObject.getString("Name");
//        String cusAddress =jsonObject.getString("Address");
//        String cusSalary = jsonObject.getString("Salary");
//
//        System.out.println("DoPost "+cusID + ":" + cusName + ":" + cusAddress + ":"+ cusSalary);


//        JsonReader reader = Json.createReader(req.getReader());
//        JsonArray jsonValues = reader.readArray();

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/company", "root", "1234");

//            for (JsonValue jsonValue : jsonValues) {
//                JsonObject jsonObject = jsonValue.asJsonObject();
//                String cusID = jsonObject.getString("id");
//                String cusName = jsonObject.getString("name");
//                String cusAddress = jsonObject.getString("address");
//                String cusSalary = jsonObject.getString("salary");

                PreparedStatement pstm = connection.prepareStatement("insert into Customer values(?,?,?,?)");
                pstm.setObject(1, cusID);
                pstm.setObject(2, cusName);
                pstm.setObject(3, cusAddress);
                pstm.setObject(4, cusSalary);
                resp.addHeader("Content-Type", "application/json");/*if you want to alert in json you have to use it again(my point) !!!*/
            resp.addHeader("Access-Control-Allow-Origin","*");
//            resp.addHeader("Access-Control-Allow-Origin","*");

                if (pstm.executeUpdate() > 0) {
                    JsonObjectBuilder response = Json.createObjectBuilder();//create object
                    response.add("state", "OK");
                    response.add("message", "Successfully Added....!");
                    response.add("data", "");
                    resp.getWriter().print(response.build());
                }
//            }

            } catch(ClassNotFoundException | SQLException e){
                JsonObjectBuilder response = Json.createObjectBuilder();//create object
                response.add("state", "Error");
                response.add("message", e.getMessage());
                response.add("data", "");
                resp.setStatus(400);
                resp.getWriter().print(response.build());
            }
        }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {  //QueryString Support,Formdata NotSupport,Json Support
//        String cusID = req.getParameter("cusID");
//        String cusName = req.getParameter("cusName");
//        String cusAddress = req.getParameter("cusAddress");
//        String cusSalary = req.getParameter("cusSalary");

        JsonReader reader = Json.createReader(req.getReader());
        JsonObject customerJsonObject = reader.readObject();
        String cusID =customerJsonObject.getString("id");
        String cusName = customerJsonObject.getString("name");
        String cusAddress =customerJsonObject.getString("address");
        String cusSalary =customerJsonObject.getString("salary");

//        JsonReader reader = Json.createReader(req.getReader());
//        JsonArray jsonValues = reader.readArray();
//        for (JsonValue jsonValue : jsonValues) {
//            JsonObject jsonObject = jsonValue.asJsonObject();
//            String oid =jsonObject.getString("oid");
//            String date=jsonObject.getString("date");
//            JsonArray orderDetails = jsonObject.getJsonArray("orderDetails");
//            System.out.print(oid + " : " + date );
//
//            for (JsonValue orderDetail : orderDetails) {
//                JsonObject jsonObject1 = orderDetail.asJsonObject();
//                String code=jsonObject1.getString("code");
//                System.out.println("\t"+ code + "\t");
//            }
//        }


//        System.out.println(cusID + "" + cusName + "" + cusAddress + ""+ cusSalary);//null null null null
        /*Why? backend ekei front ekei data transfer karawita transfer karana krama(Data Transforming Format) 03k ataha QueryString,FormData,JSON
        complex data yawimata JSON Object use karai */




        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/company", "root", "1234");

            PreparedStatement pstm3 = connection.prepareStatement("update Customer set name=?,address=?,salary=? where id=?");
                    pstm3.setObject(4, cusID);
                    pstm3.setObject(1, cusName);
                    pstm3.setObject(2, cusAddress);
                    pstm3.setObject(3, cusSalary);
                    resp.addHeader("Content-Type","application/json");/*if you want to alert in json you have to use it again(my point) !!!*/
            resp.addHeader("Access-Control-Allow-Origin","*");
            resp.addHeader("Access-Control-Allow-Headers","content-type");


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
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException { //QueryString Support,Formdata NotSupport,Json Support
        String id = req.getParameter("id");

//        JsonReader reader = Json.createReader(req.getReader());
//        JsonObject jsonObject = reader.readObject();
//        String cusID =jsonObject.getString("Id");
//
//        System.out.println("DoDelete"+cusID + ":");

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/company", "root", "1234");

            PreparedStatement pstm2 = connection.prepareStatement("delete from Customer where id=?");
                    pstm2.setObject(1, id);
                    resp.addHeader("Content-Type","application/json");/*if you want to alert in json you have to use it again(my point) !!!*/
                    resp.addHeader("Access-Control-Allow-Origin","*");


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

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.addHeader("Access-Control-Allow-Origin", "*");
        resp.addHeader("Access-Control-Allow-Methods", "PUT,DELETE");
        resp.addHeader("Access-Control-Allow-Headers", "content-type");
    }
}


/*monolithic architecture :- application components serama eka unit ekak widiyata thiye nam monolithic architecture wee. */
/*monolithic architecture's  disadvantages:- can't edit /maintain karanna amurui/'*/
/*RESOURCES ORIENTED ARCHITECTURE:- pradan washayen kotas 02kata bediya haka ekama layer eka kotas kihipayakata bedila thiye nam RESOURCES ORIENTED ARCHITECTURE wee */
/*MEKATH EXPAND KARANA LAYAER EKA (MICRO ARCHITECTURE)*/