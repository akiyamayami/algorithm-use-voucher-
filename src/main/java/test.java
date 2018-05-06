import java.util.*;


public class test {

//    private static final List<ListVoucherUse> list = new ArrayList<>();


    private static void permGen(Voucher[] listVoucher, int i, int k, Voucher[] buff, long itemPrice,
                                 List<ListVoucherUse> list) {
        if(i<k) {
            for(int j=0;j<listVoucher.length;j++) {
                if (i > 0)
                    if (buff[i-1].ID.equals(listVoucher[j].ID)
                            || getPosition(buff[i-1],listVoucher) > j
                            || (alreadyuseVoucherPer(buff,i) && listVoucher[j].onlyOne))
                        continue;
                buff[i] = listVoucher[j];
                permGen(listVoucher,i+1,k,buff,itemPrice, list);
            }
        }
        else {
            Voucher[] newbuff = buff.clone();
            long Arrear = calArrear(newbuff,itemPrice);
            list.add(new ListVoucherUse(newbuff, Arrear));
        }

    }

    private static Long calArrear(Voucher[] vouchers,long itemPrice){
        long sum = 0;
        for (Voucher voucher: vouchers) {
            if (voucher == null)
                break;
            sum += voucher.value;
        }
        return sum - itemPrice;
    }

    private static int getPosition(Voucher voucher, Voucher[] listVoucher){
        for (int  i = 0 ; i < listVoucher.length; i++) {
            if (voucher == listVoucher[i])
                return i;
        }
        return -1;
    }

    private static boolean alreadyuseVoucherPer(Voucher[] buff, int position){
        for( int i = 0 ; i < position ; i++)
        {
            if (buff[i] == null)
                return false;
            if (buff[i].onlyOne)
                return true;
        }
        return false;
    }
    public static void main(String[] args) {
        Voucher[] voucherList = {
                new Voucher("10k-1",10000L,false),
                new Voucher("10k-2",10000L,false),
                new Voucher("25k",25000L,false),
                new Voucher("30k",30000L,false),
                new Voucher("50k",50000L,false),
                new Voucher("100k",100000L,false),
                new Voucher("10%",7000L,true),
                new Voucher("20%",14000L,true)};


        long itemPrice = 70000L;
        List<ListVoucherUse> list = new ArrayList<>();
        Voucher[] buff = new Voucher[voucherList.length];
        int k = voucherList.length;
        for(int i=1;i<=k;i++) {
            permGen(voucherList,0,i,buff,itemPrice, list);
        }

        list.sort((l1, l2) -> (int) (l1.Arrear - l2.Arrear));
        for (ListVoucherUse item : list) {
            for (Voucher voucher : item.listVoucher){
                if (voucher == null)
                    break;
                System.out.print(voucher.ID + " ");
            }
            System.out.println(" : " + item.Arrear);
        }
    }

    private static class ListVoucherUse{
        Voucher[] listVoucher;
        long Arrear;

        ListVoucherUse(Voucher[] listVoucher, long arrear) {
            this.listVoucher = listVoucher;
            Arrear = arrear;
        }
    }
}
