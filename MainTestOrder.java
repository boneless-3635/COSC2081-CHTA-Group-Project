import java.io.IOException;

public class MainTestOrder {
    public static void main(String[] args) throws IOException {
        Product.initializeProduct();
        Order.placeOrder(Product.getProductArrayList());
    }
}
