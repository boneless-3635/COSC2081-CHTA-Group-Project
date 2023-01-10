public class Admin extends User{
    public Admin(String userName, String password) {
        super(userName, password);
    }

    @Override
    public boolean isAdmin (){
        return true;
    }

    @Override
    public String toString(){
        return "Admin account\n";
    }
}
