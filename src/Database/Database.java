package Database;

import Model.*;
import javax.swing.JOptionPane;
import java.sql.*;
import java.util.ArrayList;

public class Database {
    Connection conn;

    public Database() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbtubes", "root14", "pass");
            System.out.println("Koneksi ke database berhasil!");
        } catch (Exception e) {
            System.err.println("Koneksi ke database gagal: " + e.getMessage());
            JOptionPane.showMessageDialog(null, e);
        }
    }
        
    // Metode untuk menutup koneksi
    public void closeConnection() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("Koneksi ditutup.");
            }
        } catch (SQLException e) {
            System.err.println("Gagal menutup koneksi: " + e.getMessage());
        }
    }

    // Metode untuk mendapatkan koneksi
    public Connection getConnection() {
        return conn;
    }
    
    // Metode untuk Insert
    public void insert(String firstname, String lastname, String gender, String asalsekolah, String address, String username, String password) {
        try {
            String query = "INSERT INTO siswa (firstname, lastname, gender, asalsekolah, address, user_id) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, firstname);
            stmt.setString(2, lastname);
            stmt.setString(3, gender);
            stmt.setString(4, asalsekolah);
            stmt.setString(5, address);
            stmt.setInt(6, getUserID(username, password));
            stmt.executeUpdate();
            System.out.println("Data siswa berhasil ditambahkan!");
        } catch (SQLException e) {
            System.err.println("Gagal menambahkan data siswa: " + e.getMessage());
        }
    }
    
    // Metode untuk Read
    public ArrayList<Siswa> readSiswa() {
        ArrayList<Siswa> siswaList = new ArrayList<>();
        try {
            String query = "SELECT * FROM siswa";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Siswa siswa = new Siswa();
                siswa.setId(rs.getInt("id"));
                siswa.setFirstName(rs.getString("firstname"));
                siswa.setLastName(rs.getString("lastname"));
                siswa.setGender(rs.getString("gender"));
                siswa.setAsalSekolah(rs.getString("asalsekolah"));
                siswa.setAddress(rs.getString("address"));
                siswaList.add(siswa);
            }
        } catch (SQLException e) {
            System.err.println("Gagal membaca data siswa: " + e.getMessage());
        }
        return siswaList;
    }

    // Metode untuk Update
    public void updateSiswa(int id, String firstname, String lastname, String gender, String asalsekolah, String address) {
        try {
            String query = "UPDATE siswa SET firstname = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, firstname);
            stmt.setInt(2, id);
            stmt.executeUpdate();
            System.out.println("Data siswa berhasil diperbarui!");
        } catch (SQLException e) {
            System.err.println("Gagal memperbarui data siswa: " + e.getMessage());
        }
    }

    private int getUserID(String username, String password) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
