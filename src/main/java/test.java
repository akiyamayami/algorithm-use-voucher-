import java.util.*;


public class test {

//    private static final List<ListVoucherUse> list = new ArrayList<>();


    private static void permGen(Voucher[] listVoucher, int i, int k, Voucher[] buff, long itemPrice,
                                List<ListVoucherUse> list, Extra extra) {
        if (i < k) {
            for (int j = 0; j < listVoucher.length; j++) {
                if (i > 0)
                    if (buff[i - 1].ID.equals(listVoucher[j].ID)
                            || getPosition(buff[i - 1], listVoucher) > j
                            || (alreadyuseVoucherPer(buff, i) && listVoucher[j].onlyOne))
                        continue;
                buff[i] = listVoucher[j];
                permGen(listVoucher, i + 1, k, buff, itemPrice, list, extra);
            }
        } else {
            Voucher[] newbuff = buff.clone();
            long Arrear = calArrear(newbuff, itemPrice);
            if (extra.minValue <= Arrear && Arrear <= 0) {
                if ((extra.minValue == Arrear && getArraySize(list.get(extra.minPos).listVoucher) > getArraySize(newbuff)))
                {
                    return;
                }
                if (extra.minPos != -1)
                    list.remove(extra.minPos);
                extra.minValue = Arrear;
                list.add(new ListVoucherUse(newbuff, Arrear));
                extra.minPos = list.size() - 1;
                if (extra.maxPos > 0)
                    extra.maxPos--;
            } else if (extra.maxValue >= Arrear && Arrear >= 0) {
                if ((extra.maxPos == Arrear && getArraySize(list.get(extra.maxPos).listVoucher) > getArraySize(newbuff)))
                {
                    return;
                }
                if (extra.maxPos != -1)
                    list.remove(extra.maxPos);
                extra.maxValue = Arrear;
                list.add(new ListVoucherUse(newbuff, Arrear));
                extra.maxPos = list.size() - 1;
                if (extra.minPos > 0)
                    extra.minPos--;
            }

        }

    }

    private static int getArraySize(Voucher[] Array) {

        for (int i = 0; i < Array.length; i++) {
            if (Array[i] == null)
                return i;
        }
        return -1;
    }

    private static Long calArrear(Voucher[] vouchers, long itemPrice) {
        long sum = 0;
        for (Voucher voucher : vouchers) {
            if (voucher == null)
                break;
            sum += voucher.value;
        }
        return sum - itemPrice;
    }

    private static int getPosition(Voucher voucher, Voucher[] listVoucher) {
        for (int i = 0; i < listVoucher.length; i++) {
            if (voucher == listVoucher[i])
                return i;
        }
        return -1;
    }

    private static boolean alreadyuseVoucherPer(Voucher[] buff, int position) {
        for (int i = 0; i < position; i++) {
            if (buff[i] == null)
                return false;
            if (buff[i].onlyOne)
                return true;
        }
        return false;
    }

    public static void main(String[] args) {
        Voucher[] voucherList = {
                new Voucher("10k-1", 10000L, false),
                new Voucher("10k-2", 10000L, false),
                new Voucher("17.5k", 17500L, false),
                new Voucher("25k", 25000L, false),
                new Voucher("30k", 30000L, false),
                new Voucher("50k", 50000L, false),
                new Voucher("100k", 100000L, false),
                new Voucher("10%", 7500L, true),
                new Voucher("20%", 14000L, true)};


        long itemPrice = 17000L;
        List<ListVoucherUse> list = new LinkedList<>();
        Voucher[] buff = new Voucher[voucherList.length];
        System.out.println(buff.length);
        int k = voucherList.length;
        Extra extra = new Extra();
        long now = System.currentTimeMillis();
        for (int i = 1; i <= k; i++) {
            permGen(voucherList, 0, i, buff, itemPrice, list, extra);
        }

        for (ListVoucherUse item : list) {
            for (int i = 0; i < item.listVoucher.length; i++) {
                if (item.listVoucher[i] == null)
                    break;
                System.out.print(item.listVoucher[i].ID + " : " + item.listVoucher[i].value + ", ");
            }
            System.out.println(": " + item.Arrear);

        }
        System.out.println(System.currentTimeMillis() - now);

    }

    private static class ListVoucherUse {
        Voucher[] listVoucher;
        long Arrear;

        ListVoucherUse(Voucher[] listVoucher, long arrear) {
            this.listVoucher = listVoucher;
            Arrear = arrear;
        }

    }

    private static class Extra {
        long minValue = -999999999;
        int minPos = -1;

        long maxValue = 999999999;
        int maxPos = -1;

    }
}
