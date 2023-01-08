import java.io.IOException;

public class MainTestOrder {
    public static void main(String[] args) throws IOException {
        Product.initializeProduct();
        Order.placeOrder(Product.getProductArrayList());
        Order.initializeOrders();
        System.out.println("----------");
        Customer.initializeCustomers();
        System.out.println("----------");
        Customer an = new Customer("an","an123","userid123","anName","anPhone","anMail","anAddress");
        System.out.println("hi");
        an.viewUserOrder();
        Admin admin = new Admin("admin","admin1");
        admin.getAllOrderByCustomerId();
        admin.checkMostPaidCustomer();
    }
}
