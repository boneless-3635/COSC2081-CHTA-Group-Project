import java.util.Date;

public class Order {
    private final String ORDER_ID;
    private final String USER_ID;
    private String userName;
    private String [] productNames;
    private String status;
    private double totalPrice;
    private Date date;

    public Order(String ORDER_ID, String user_id, String [] productName, String userName, String status, double totalPrice, Date date) {
        this.ORDER_ID = ORDER_ID;
        this.USER_ID = user_id;
        this.productNames = productName;
        this.userName = userName;
        this.status = status;
        this.totalPrice = totalPrice;
        this.date = date;
    }



    //getter

    public String getORDER_ID() {
        return ORDER_ID;
    }

    public String getUSER_ID() {
        return USER_ID;
    }

    public String [] getProductName() {
        return productNames;
    }

    public String getUserName() {
        return userName;
    }

    public String getStatus() {
        return status;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public Date getDate() {
        return date;
    }
}
