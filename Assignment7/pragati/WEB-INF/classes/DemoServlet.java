import jakarta.servlet.http.*;  
import jakarta.servlet.*;  
import java.io.*; 
import java.sql.*;  

public class DemoServlet extends HttpServlet {  

    public void doGet(HttpServletRequest req, HttpServletResponse res)  
    throws ServletException, IOException {  

        res.setContentType("text/html");  //setting the content type  
        PrintWriter pw = res.getWriter();  //get the stream to write the data   

        // Database connection
        try { 
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establishing connection to the database
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3308/pragati", "root", "");

            // Create Statement object to execute queries
            Statement stmt = con.createStatement();

            // Check if action is performed (insert, update, or delete)
            String action = req.getParameter("action");

            // Insert Operation
            // Insert Operation
// Insert Operation
if ("insert".equals(action)) {
    String title = req.getParameter("title");
    String author = req.getParameter("author");
    double price = Double.parseDouble(req.getParameter("price"));
    int qty = Integer.parseInt(req.getParameter("qty"));

    // Insert without specifying Book_id, as it will be auto-generated
    stmt.executeUpdate("INSERT INTO ebookshopp (Book_Title, Book_Author, Book_Price, Quantity) VALUES ('"
            + title + "', '" + author + "', " + price + ", " + qty + ")");
}


            // Delete Operation
            if ("delete".equals(action)) {
                int id = Integer.parseInt(req.getParameter("id"));
                stmt.executeUpdate("DELETE FROM ebookshopp WHERE Book_id = " + id);
            }

            // Update Operation
            if ("update".equals(action)) {
                int id = Integer.parseInt(req.getParameter("id"));
                String title = req.getParameter("title");
                String author = req.getParameter("author");
                double price = Double.parseDouble(req.getParameter("price"));
                int qty = Integer.parseInt(req.getParameter("qty"));

                stmt.executeUpdate("UPDATE ebookshopp SET Book_Title='" + title + "', Book_Author='" + author +
                        "', Book_Price=" + price + ", Quantity=" + qty + " WHERE Book_id=" + id);
            }

            // Display the table with updated data
            pw.println("<html><body>");
            pw.println("<h2>Welcome to Pragati eBookShop</h2>");
            pw.println("<table border='2'>");  
            pw.println("<tr><th>Book id</th><th>Book Title</th><th>Book Author</th><th>Book Price</th><th>Quantity</th><th>Actions</th></tr>");

            // Query to fetch data from ebookshopp table
            ResultSet rs = stmt.executeQuery("SELECT * FROM ebookshopp");
            while (rs.next()) {
                int id = rs.getInt("Book_id");
                String title = rs.getString("Book_Title");
                String author = rs.getString("Book_Author");
                double price = rs.getDouble("Book_Price"); // Corrected the column name to "Book_Price"
                int qty = rs.getInt("Quantity");

                // Form for Update and Delete actions
                pw.println("<form method='get'>");
                pw.println("<tr>");
                pw.println("<td>" + id + "<input type='hidden' name='id' value='" + id + "'></td>");
                pw.println("<td><input type='text' name='title' value='" + title + "'></td>");
                pw.println("<td><input type='text' name='author' value='" + author + "'></td>");
                pw.println("<td><input type='text' name='price' value='" + price + "'></td>");
                pw.println("<td><input type='text' name='qty' value='" + qty + "'></td>");
                pw.println("<td>");
                pw.println("<button type='submit' name='action' value='update'>Update</button>");
                pw.println("<button type='submit' name='action' value='delete'>Delete</button>");
                pw.println("</td>");
                pw.println("</tr>");
                pw.println("</form>");
            }

            // Insert form
            pw.println("<form method='get'>");
            pw.println("<tr>");
            pw.println("<td>New</td>");
            pw.println("<td><input type='text' name='title'></td>");
            pw.println("<td><input type='text' name='author'></td>");
            pw.println("<td><input type='text' name='price'></td>");
            pw.println("<td><input type='text' name='qty'></td>");
            pw.println("<td><button type='submit' name='action' value='insert'>Insert</button></td>");
            pw.println("</tr>");
            pw.println("</form>");

            pw.println("</table>");
            pw.println("</body></html>");

            pw.close();
            con.close();
        } catch (Exception e) {
            pw.println("<p style='color:red;'>" + e + "</p>");
        }
    }
}  
