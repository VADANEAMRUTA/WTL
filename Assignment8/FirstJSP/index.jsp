<%@ page import="java.sql.*" %>
<%@ page import="javax.servlet.*" %>
<%@ page import="javax.servlet.http.*" %>
<html>
<body>

<%
    String action = request.getParameter("action");

    if(action != null) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3308/pragati", "root", "");

            String book_id = request.getParameter("book_id");  // Not used in Insert
            String book_title = request.getParameter("book_title");
            String book_author = request.getParameter("book_author");
            String book_price = request.getParameter("book_price");
            String quantity = request.getParameter("quantity");

            if("insert".equals(action)) {
                // No need to set Book_id as it will auto-increment
                PreparedStatement ps = con.prepareStatement("INSERT INTO ebookshop (Book_Title, Book_Author, Book_Price, Quantity) VALUES (?, ?, ?, ?)");
                ps.setString(1, book_title);
                ps.setString(2, book_author);
                ps.setString(3, book_price);
                ps.setString(4, quantity);

                int i = ps.executeUpdate();
                if(i > 0){
                    out.println("<p>Record inserted successfully!</p>");
                } else {
                    out.println("<p>Insertion failed.</p>");
                }
            } else if("update".equals(action)) {
                PreparedStatement ps = con.prepareStatement("UPDATE ebookshop SET Book_Title=?, Book_Author=?, Book_Price=?, Quantity=? WHERE Book_id=?");
                ps.setString(1, book_title);
                ps.setString(2, book_author);
                ps.setString(3, book_price);
                ps.setString(4, quantity);
                ps.setString(5, book_id);

                int i = ps.executeUpdate();
                if(i > 0){
                    out.println("<p>Record updated successfully!</p>");
                } else {
                    out.println("<p>Update failed. Book not found!</p>");
                }
            } else if("delete".equals(action)) {
                PreparedStatement ps = con.prepareStatement("DELETE FROM ebookshop WHERE Book_id=?");
                ps.setString(1, book_id);

                int i = ps.executeUpdate();
                if(i > 0){
                    out.println("<p>Record deleted successfully!</p>");
                } else {
                    out.println("<p>Deletion failed. Book not found!</p>");
                }
            }

            con.close();
        } catch(Exception e) {
            out.println(e);
        }
    }
%>

<h2>Book Record Management (Insert / Update / Delete)</h2>

<form method="post">
    <label>Action:</label>
    <select name="action" required>
        <option value="">--Select--</option>
        <option value="insert">Insert</option>
        <option value="update">Update</option>
        <option value="delete">Delete</option>
    </select>
    <br><br>

    Book ID: <input type="text" name="book_id"><br><br>  <!-- This field is now optional for Insert -->
    Title: <input type="text" name="book_title"><br><br>
    Author: <input type="text" name="book_author"><br><br>
    Price: <input type="text" name="book_price"><br><br>
    Quantity: <input type="text" name="quantity"><br><br>

    <input type="submit" value="Submit">
</form>

</body>
</html>
