package bussinesslayer;

import java.util.List;

public interface IDeliveryServiceProcessing {

    /**
     * @param begin begin of hour time; must be non-negative and less
     *              than end
     *  @param end end of hour time; must be non-negative and more
     *                   than end
     * @throws IndexOutOfBoundsException if the begin is out of range
     *                                   ({@code Integer.parseInt(begin) < 0 || Integer.parseInt(begin) >= Integer.parseInt(end) || Integer.parseInt(end) < 0})
     */
    public static List<Order> findOrders(List<Order> orders, String begin, String end) {
        return null;
    }
    /**
     * @param nr nr; must be non-negative
     * @throws IndexOutOfBoundsException if the nr is out of range
     *                                   ({@code Integer.parseInt(nr) < 0 })
     */
    public static String findProductsMoreThanX(List<Order> orders, String nr) {
        return null;
    }
    /**
     * @param nrOrder nrOrder; must be non-negative
     *@param nrPayment nrPayment; must be non-negative
     * @throws IndexOutOfBoundsException if the nrOrder is out of range
     *                                   ({@code Integer.parseInt(nrOrder) < 0 || Integer.parseInt(nrPayment) < 0 })
     */
    public static String findClientsMoreThanX(List<Order> orders, String nrOrder, String nrPayment) {
        return null;
    }
    /**
     * @param day day; must be non-negative and a number from 00 to 24
     * @throws IndexOutOfBoundsException if the day is out of range
     *                                   ({@code Integer.parseInt(day) < 0 || Integer.parseInt(day) > 24 })
     */
    public static String findProducstFromDay(List<Order> orders, String day) {
        return null;
    }
}
