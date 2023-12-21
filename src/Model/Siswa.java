package Model;

public abstract class Siswa {
    private String fullName, alamat;
    private double nilaiUjian;

    public Siswa(String fullName, String alamat, double nilaiUjian) {
        this.fullName = fullName;
        this.alamat = alamat;
        this.nilaiUjian = nilaiUjian;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public double getNilaiUjian() {
        return nilaiUjian;
    }

    public void setNilaiUjian(double nilaiUjian) {
        this.nilaiUjian = nilaiUjian;
    }
}
