public class SiswaBaru extends Siswa {
    private boolean lulus;

    public SiswaBaru(String fullName, String alamat, double nilaiUjian, boolean lulus) {
        super(fullName, alamat, nilaiUjian);
        this.lulus = lulus;
    }

    public boolean isLulus() {
        return lulus;
    }

    public void setLulus(boolean lulus) {
        this.lulus = lulus;
    }
}
