import java.io.IOException;

public class MainTestOrder {
    public static void main(String[] args) throws IOException {
        Product.initializeProduct();

//        Order.placeOrder(Product.getProductArrayList());
        Order.initializeOrders();
//        System.out.println("----------");
//        Customer.initializeCustomers();
//        System.out.println("----------");
//        Customer an = new Customer("an","an123","userid123","anName","anPhone","anMail","anAddress");
//        System.out.println("hi");
//        an.viewUserOrder();
        Admin admin = new Admin("admin","admin1");
//        admin.getAllOrderByCustomerId();
//        admin.checkMostPaidCustomer();
        admin.viewAllOrderMadeInDay();

        Customer customer = new Customer("customer1", "Abc123#", "abc123@", "Minh Thong", "0909090909", "thong@gmail.com", "abc123 hcm","Silver");
        Order.placeOrder(customer);


//        Order.initializeOrders();
//        Admin admin = new Admin("admin1", "abc123@A");
//        System.out.println(admin.calculateTotalRevenue());
//        System.out.println(admin.calculateTotalRevenueWithDateValidate());;
//        admin.changeOrderStatus(Order.getOrders());
    }
}
