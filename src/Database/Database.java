package Database;

import javax.swing.JOptionPane;
import java.sql.*;

// Import Package lainnya pada project ini
import Model.*;
import Program.*;

/* SEBELUM MELAKUKAN EDIT DAN MENJALANKAN PROGRAM!
* Program ini membutuhkan database dengan nama tabel sebagai berikut:
* (Pastikan nama tabel sesuai untuk menghindari error)
*
* 1. siswa
* 2. user_siswa
 */

public class Database {


    // Jangan lupa terlebih dahulu mengubah DB_URL, DB_USER, DB_PASS jika tidak sesuai!
    static final String DB_URL = "jdbc:mysql://localhost:3306/dbtubes";
    static final String DB_USER = "root";
    static final String DB_PASS = "";

    private String sql;
    static PreparedStatement stmt;
    static Connection conn;
    static ResultSet rs;

    private int id;

    public Database() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            System.out.println("Koneksi ke database berhasil!");
        } catch (Exception e) {
            System.err.println("Koneksi ke database gagal: " + e.getMessage());
            JOptionPane.showMessageDialog(null, e);
        }
    }

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

    public Connection getConnection() {
        return conn;
    }

    /* CREATE METHOD (DONE) */
    public void createUser(String username, String password){
        conn = getConnection();
        sql = "INSERT INTO user_siswa (username, password) VALUES (?, ?)";

        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);

            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data berhasil disimpan!");
        } catch (SQLException e){
            JOptionPane.showMessageDialog(null, e);
        } finally {
            closeConnection();
        }
    }

    public void createSiswa(Siswa siswa){
        conn = getConnection();
        sql = "INSERT INTO siswa (firstname, lastname, gender, asalsekolah, address) VALUES (?, ?, ?, ?, ?)";

        try {
            stmt = conn.prepareStatement(sql);

            // Insert data untuk table 'siswa'
            stmt.setString(1, siswa.getFirstName());
            stmt.setString(2, siswa.getLastName());
            stmt.setString(3, siswa.getGender());
            stmt.setString(4, siswa.getAsalSekolah());
            stmt.setString(5, siswa.getAddress());

            rs = stmt.executeQuery();
            JOptionPane.showMessageDialog(null, "Data berhasil disimpan!");
        } catch (SQLException e){
            JOptionPane.showMessageDialog(null, e);
        } finally {
            closeConnection();
        }
    }

    /* READ METHOD */
    public ResultSet validateUser(String username, String password){
        conn = getConnection();
        sql = "SELECT * FROM user_siswa WHERE username = ? AND password = ?";

        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);

            rs = stmt.executeQuery();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error connecting to the database");
        } finally {
            closeConnection();
        }

        return rs;
    }

    public void readData(Siswa siswa){}

    /* UPDATE METHOD */
    public void update(Siswa siswa) {}

    /* DELETE METHOD */


    // Metode untuk Read (BELUM SELESAI)
    public Siswa read(String username) {
        conn = getConnection();
        sql = "SELECT * FROM siswa WHERE id = ?";
        Siswa siswa = new Siswa();

        try {
            stmt = conn.prepareStatement(sql);

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
