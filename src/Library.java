import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;

public class Library {

    private ArrayList<Book> books = new ArrayList<>();

    // CREATE
    public void addBookToDatabase(Book book) {
        String sql = "INSERT INTO books (isbn, title, author, available) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, book.getIsbn());
            stmt.setString(2, book.getTitle());
            stmt.setString(3, book.getAuthor());
            stmt.setBoolean(4, book.isAvailable());

            stmt.executeUpdate();
            System.out.println("Book added to database.");

        } catch (SQLException e) {
            System.out.println("Error adding book.");
            e.printStackTrace();
        }
    }

    // READ
    public void readBooksFromDatabase() {
        String sql = "SELECT * FROM books";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("\n Books in database ");
            while (rs.next()) {
                System.out.println(
                        rs.getString("isbn") + " | " +
                                rs.getString("title") + " | " +
                                rs.getString("author") + " | Available: " +
                                rs.getBoolean("available")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // UPDATE
    public void updateAvailability(String isbn, boolean available) {
        String sql = "UPDATE books SET available = ? WHERE isbn = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setBoolean(1, available);
            stmt.setString(2, isbn);

            stmt.executeUpdate();
            System.out.println("Book updated.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // DELETE
    public void deleteBook(String isbn) {
        String sql = "DELETE FROM books WHERE isbn = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, isbn);
            stmt.executeUpdate();
            System.out.println("Book deleted.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}