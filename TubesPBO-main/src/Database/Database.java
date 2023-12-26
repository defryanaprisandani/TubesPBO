package Database;

import Model.*;
import javax.swing.JOptionPane;
import java.sql.*;
// import java.util.ArrayList;

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
    public void create(Siswa siswa) {
        try {
            // Cek apakah id sudah ada
            int id = siswa.getId();
            String sql = "SELECT id FROM siswa WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                JOptionPane.showMessageDialog(null, "ID sudah ada!");
                return;
            }

            // Insert data untuk table 'siswa'
            sql = "INSERT INTO siswa (id, firstname, lastname, gender, asalsekolah, address) VALUES (?, ?, ?, ?, ?, ?)";
            stmt.setInt(1, id);
            stmt.setString(2, siswa.getFirstName());
            stmt.setString(3, siswa.getLastName());
            stmt.setString(4, siswa.getGender());
            stmt.setString(5, siswa.getAsalSekolah());
            stmt.setString(6, siswa.getAddress());
            stmt.executeUpdate();

            // Insert data untuk table 'user'
            sql = "INSERT INTO user (id, username, password) VALUES (?, ?, ?)";
            stmt.setInt(1, id);
            stmt.setString(2, siswa.getUsername());
            stmt.setString(3, siswa.getPassword());
            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Data berhasil disimpan!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    // Metode untuk Read
    public Siswa read(int id) {
        try {
            // Cek apakah id ada
            String sql = "SELECT * FROM siswa WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                return null;
            }

            // Buat objek Siswa
            Siswa siswa = new Siswa();
            siswa.setId(rs.getInt("id"));
            siswa.setFirstName(rs.getString("firstname"));
            siswa.setLastName(rs.getString("lastname"));
            siswa.setGender(rs.getString("gender"));
            siswa.setAsalSekolah(rs.getString("asalsekolah"));
            siswa.setAddress(rs.getString("address"));

            return siswa;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
            return null;
        }
    }

    public void update(Siswa siswa) {
        try {
            // Cek apakah id ada
            int id = siswa.getId();
            String sql = "SELECT id FROM siswa WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                JOptionPane.showMessageDialog(null, "ID tidak ada!");
                return;
            }

            // Update data siswa
            sql = "UPDATE siswa SET firstname = ?, lastname = ?, gender = ?, asalsekolah = ?, address = ? WHERE id = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, siswa.getFirstName());
            ps.setString(2, siswa.getLastName());
            ps.setString(3, siswa.getGender());
            ps.setString(4, siswa.getAsalSekolah());
            ps.setString(5, siswa.getAddress());
            ps.setInt(6, id);
            ps.executeUpdate();

            // Update data user (jika perlu)
            if (siswa.getUsername() != null && siswa.getPassword() != null) {
                sql = "UPDATE user SET username = ?, password = ? WHERE id = ?";
                ps = conn.prepareStatement(sql);
                ps.setString(1, siswa.getUsername());
                ps.setString(2, siswa.getPassword());
                ps.setInt(3, id);
                ps.executeUpdate();
            }

            JOptionPane.showMessageDialog(null, "Data berhasil diupdate!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    // Metode untuk delete
    public void delete(int id) {
        try {
            // Cek apakah id ada
            String sql = "SELECT id FROM siswa WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                JOptionPane.showMessageDialog(null, "ID tidak ada!");
                return;
            }

            // Delete data user
            sql = "DELETE FROM user WHERE id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();

            // Delete data siswa
            sql = "DELETE FROM siswa WHERE id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(null, "Data berhasil dihapus!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
}
