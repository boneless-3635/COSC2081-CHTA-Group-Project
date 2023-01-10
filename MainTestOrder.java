import java.io.IOException;

public class MainTestOrder {
    public static void main(String[] args) throws IOException {
        Product.initializeProduct();
        Customer.initializeCustomers();
        Order.initializeOrders();
        Customer an = new Customer("781a821a-027e-450c-b762-63b2b7669460","customer1","Customer1@","Minh Thong","0909090909","customer1@gmail.com","321 ABC, bca, HCM", "Silver");
        Order.placeOrder(an);
//        System.out.println("----------");
//        Customer.initializeCustomers();
//        System.out.println("----------");
//        System.out.println("hi");
//        an.viewUserOrder();
//        an.viewOrderByOrderID();
//
        Admin admin = new Admin("admin","admin1");
//        admin.getAllOrderByCustomerId();
        admin.checkMostPaidCustomer();
//        admin.viewAllOrderMadeInDay();

//        Customer customer = new Customer("customer1", "Abc123#", "abc123@", "Minh Thong", "0909090909", "thong@gmail.com", "abc123 hcm","Silver");
//        Order.placeOrder(customer);


//        Order.initializeOrders();
//        Admin admin = new Admin("admin1", "abc123@A");
//        System.out.println(admin.calculateTotalRevenue());
//        System.out.println(admin.calculateTotalRevenueWithDateValidate());;
//        admin.changeOrderStatus(Order.getOrders());
    }
}
