import java.io.IOException;

public class MainTestOrder {
    public static void main(String[] args) throws IOException {
//        Product.initializeProduct();
//        Customer customer = new Customer("customer1", "Abc123#", "abc123@", "Minh Thong", "0909090909", "thong@gmail.com", "abc123 hcm","Gold");
//        Order.placeOrder(Product.getProductArrayList() , customer);


        Order.initializeOrders();
        Admin admin = new Admin("admin1", "abc123@A");
        admin.changeOrderStatus(Order.getOrders());
    }
}
