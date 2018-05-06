public class Voucher {
    public String ID;
    public long value;
    public boolean onlyOne;

    public Voucher(String ID, long value, boolean onlyOne) {
        this.ID = ID;
        this.value = value;
        this.onlyOne = onlyOne;
    }
}
