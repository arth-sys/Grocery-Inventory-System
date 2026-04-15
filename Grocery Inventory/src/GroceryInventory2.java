import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

class Products
{
    int productId, stock, sales;
    double price;
    float discount;
    String productName, category;
    LocalDate expDate;

    Products(int productId, String productName, String category, double price, int stock, LocalDate expDate, int sales, float discount)
    {
        this.productId = productId;
        this.productName = productName;
        this.category = category;
        this.price = price;
        this.stock = stock;
        this.expDate = expDate;
        this.sales = sales;
        this.discount = discount;
    }
    Products(int productId, double price)
    {
        this.productId = productId;
        this.price = price;
    }
}
class Customer
{
    int customerId, number;
    String customerName, password;
    Customer(int customerId, String customerName, int number, String password)
    {
        this.customerId = customerId;
        this.customerName = customerName;
        this.number = number;
        this.password = password;
    }

    public Customer(String customerName, int customerId)
    {
        this.customerId = customerId;
        this.customerName = customerName;
    }
    public Customer(int customerId)
    {
        this.customerId = customerId;
    }
}
class Order
{
    int orderId, customerId;
    LocalDate curDate;
    double totalAmount;
    Order(int orderId, int customerId, LocalDate curdate, double totalAmount)
    {
        this.orderId = orderId;
        this.customerId = customerId;
        this.curDate = curdate;
        this.totalAmount = totalAmount;
    }

    public Order(int orderId)
    {
        this.orderId = orderId;
    }
}
class OrderedItem
{
    int orderedItemId, orderId, productId, quantity;
    double priceAtPurchase;
    OrderedItem(int orderedItemId, int orderId, int productId, int quantity, double priceAtPurchase)
    {
        this.orderedItemId = orderedItemId;
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.priceAtPurchase = priceAtPurchase;
    }
}

class Suggetion
{
    String suggestion;
    Suggetion(String sugg)
    {
        this.suggestion = sugg;
    }
}

class DBconnection
{
    public static Connection getConnection() throws SQLException
    {
        String url = "jdbc:mysql://127.0.0.1:3306/grocery_inventory";
        String username = "root";
        String password = "Chakli@007";
        return DriverManager.getConnection(url, username, password);
    }
}
class ProductsSql
{
    public ArrayList<Products> getAllProducts()
    {
        ArrayList<Products> list = new ArrayList<>();
        try
        {
            Connection con = DBconnection.getConnection();
            String query = "SELECT * FROM product";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next())
            {
                Products p = new Products(
                        rs.getInt("product_id"),
                        rs.getString("product_name"),
                        rs.getString("category"),
                        rs.getDouble("price"),
                        rs.getInt("stock"),
                        rs.getDate("exp_date").toLocalDate(),
                        rs.getInt("sales"),
                        rs.getFloat("discount"));
                list.add(p);


            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<Products> addProduct(String productName, String category, double price, int quantity, LocalDate expDate)
    {
        try
        {
            Connection con = DBconnection.getConnection();
            String query = "INSERT INTO product(product_name, category, price, stock, exp_date) VALUES(?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, productName);
            ps.setString(2, category);
            ps.setDouble(3, price);
            ps.setInt(4, quantity);
            ps.setDate(5, Date.valueOf(expDate));
            System.out.println("Product added successfully.");
            ps.executeUpdate();

        }
        catch (SQLException e)
        {
            System.out.println("Product adding failed.");
        }
        return getAllProducts();
    }
    public ArrayList<Products> addExistingProduct(String productName, int quantity, LocalDate expDate)
    {
        try
        {
            Connection con = DBconnection.getConnection();
            String query = "UPDATE product SET stock = (stock + ?), exp_date= ? WHERE product_name =?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, quantity);
            ps.setDate(2, Date.valueOf(expDate));
            ps.setString(3, productName);
            System.out.println("Product updated successfully.");
            ps.executeUpdate();
        }
        catch (SQLException e)
        {
            System.out.println("Product adding failed.");
        }
        return getAllProducts();
    }

    public ArrayList<Products> changeDiscount(String productsName, float discount)
    {
        try
        {
            Connection con = DBconnection.getConnection();
            String query = "UPDATE product SET discount=?, price = (price-(price*?)) WHERE product_name=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setFloat(1, discount);
            ps.setFloat(2, discount/100);
            ps.setString(3, productsName);
            ps.executeUpdate();
            ps.close();
            con.close();
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
        return getAllProducts();
    }
    public ArrayList<Products> changePrice(String productName, Double price)
    {
        try
        {
            Connection con = DBconnection.getConnection();
            String query = "UPDATE product SET price = ? WHERE product_name=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setDouble(1, price);
            ps.setString(2, productName);
            ps.executeUpdate();

        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
        return getAllProducts();
    }
    public ArrayList<Products> lowStockProducts()
    {
        ArrayList<Products> list = new ArrayList<>();
        try
        {
            Connection con = DBconnection.getConnection();
            String query = "SELECT * FROM product WHERE stock<20";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next())
            {
                Products p = new Products(
                        rs.getInt("product_id"),
                        rs.getString("product_name"),
                        rs.getString("category"),
                        rs.getDouble("price"),
                        rs.getInt("stock"),
                        rs.getDate("exp_date").toLocalDate(),
                        rs.getInt("sales"),
                        rs.getFloat("discount"));
                list.add(p);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return list;
    }
    public ArrayList<Products> nearExpProducts()
    {
        ArrayList<Products> list = new ArrayList<>();
        try
        {
            Connection con = DBconnection.getConnection();
            String query = "SELECT * FROM product WHERE DATEDIFF(exp_date, CURDATE())<10";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next())
            {
                Products p = new Products(
                        rs.getInt("product_id"),
                        rs.getString("product_name"),
                        rs.getString("category"),
                        rs.getDouble("price"),
                        rs.getInt("stock"),
                        rs.getDate("exp_date").toLocalDate(),
                        rs.getInt("sales"),
                        rs.getFloat("discount"));
                list.add(p);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<Suggetion> seeSuggestion()
    {
        ArrayList<Suggetion> list1 = new ArrayList<>();
        try
        {
            Connection con = DBconnection.getConnection();
            String query = "SELECT * FROM suggestion";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next())
            {
                Suggetion s = new Suggetion(rs.getString("suggestion"));
                list1.add(s);
            }

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return list1;
    }
}
class CustomerSql
{

    public boolean customerVerification(String name, String password)
    {
        boolean isVerified = false;
        ArrayList<Customer> listC =  new ArrayList<>();
        try
        {
            Connection con = DBconnection.getConnection();
            String query = "SELECT * FROM customer where customer_name=?";

            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                Customer c = new Customer(
                        rs.getInt("customer_id"),
                        rs.getString("customer_name"),
                        rs.getInt("number"),
                        rs.getString("password"));
                listC.add(c);
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        for(Customer c:listC)
        {
            if(c.password.equalsIgnoreCase(password))
            {
                isVerified = true;
            }
        }
        return isVerified;
    }
    public void newCustomer(String name, long number ,String password)
    {
        try
        {
            Connection con = DBconnection.getConnection();
            String query = "INSERT INTO customer(customer_name, number, password) VALUES (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1,name);
            ps.setLong(2, number);
            ps.setString(3, password);
            ps.executeUpdate();
            System.out.println("Info saved.");
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}

class OrderSql
{

    public void orderIdCreation(String customerName)
    {
        ArrayList<Customer> list1 = new ArrayList<>();
        int customerId = 0;
        try
        {
            Connection con = DBconnection.getConnection();
            String query = "SELECT customer_id from customer WHERE customer_name =?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1,customerName);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                customerId = rs.getInt("customer_id");
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        try
        {
            Connection con =DBconnection.getConnection();
            String query = "INSERT INTO orders(customer_id, order_date, total_amount) VALUES(?, CURDATE(), 0)";
            PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, customerId);
            ps.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }
    public void updateTotalAmount()
    {
        ArrayList<Order> list1 = new ArrayList<>();
        ArrayList<Order> list2 = new ArrayList<>();
        int orderId = 0;
        double sum = 0;
        try
        {
            Connection con = DBconnection.getConnection();
            String query = "SELECT MAX(order_id) AS max_id FROM orders";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next())
            {
                orderId = rs.getInt("max_id");
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();;
        }
        try
        {
            Connection con = DBconnection.getConnection();
            String query = "SELECT SUM(price_at_purchase) AS total FROM ordered_items WHERE order_id =?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                sum = rs.getInt("total");
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();;
        }
        try
        {
            Connection con = DBconnection.getConnection();
            String query = "UPDATE orders SET total_amount=? WHERE order_id = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setDouble(1, sum);
            ps.setInt(2, orderId);
            ps.executeUpdate();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        System.out.println("Total amount= ₹"+sum);

    }


    public ArrayList<OrderedItem> order(String customerName, int productId, int quantity)
    {
        ArrayList<OrderedItem> cart = new ArrayList<>();
        ArrayList<Order> list1 = new ArrayList<>();
        ArrayList<Products> list2 = new ArrayList<>();
        double price = 0;
        int orderId = 0;
        ArrayList<Customer> list3 = new ArrayList<>();
        try
        {
            Connection con = DBconnection.getConnection();
            String query = "SELECT product_id, price from product";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next())
            {
                Products p = new Products(rs.getInt("product_id"), rs.getInt("price"));
                list2.add(p);
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }

        for(Products p: list2)
        {
            if(p.productId == productId)
            {
                price = quantity *p.price;
            }
        }
        try
        {
            Connection con = DBconnection.getConnection();
            String query = "SELECT MAX(order_id) AS max_id FROM orders";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next())
            {
                orderId = rs.getInt("max_id");
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();;
        }
        try
        {
            Connection con = DBconnection.getConnection();
            String query = "INSERT INTO ordered_items(product_id, order_id, quantity, price_at_purchase) VALUES(?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, productId);
            ps.setInt(2, orderId);
            ps.setInt(3, quantity);
            ps.setDouble(4, price);
            ps.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        try
        {
            Connection con = DBconnection.getConnection();
            String query = "SELECT * FROM ordered_items WHERE order_id=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                OrderedItem o = new OrderedItem(
                        rs.getInt("order_item_id"),
                        rs.getInt("order_id"),
                        rs.getInt("product_id"),
                        rs.getInt("quantity"),
                        rs.getInt("price_at_purchase"));
                cart.add(o);
            }

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        try
        {
            Connection con = DBconnection.getConnection();
            String query = "UPDATE product SET stock = (stock-?), sales = (sales+?) WHERE product_id =?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, quantity);
            ps.setInt(2, quantity);
            ps.setInt(3, productId);
            ps.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return cart;
    }

    public String productName(int productId)
    {
        String name = null;
        try
        {
            Connection con = DBconnection.getConnection();
            String query = "SELECT product_name FROM product WHERE product_id=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, productId);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                name = rs.getString("product_name");
            }

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return name;
    }

    public ArrayList<Order> reviewOrders(String customerName)
    {
        ArrayList<Order> list1 = new ArrayList<>();
        int customerId = 0;
        try
        {
            Connection con = DBconnection.getConnection();
            String query = "SELECT customer_id FROM customer where customer_name=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, customerName);
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                customerId = rs.getInt("customer_id");
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        try
        {
            Connection con = DBconnection.getConnection();
            String query = "SELECT * FROM orders Where customer_id = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, customerId);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                Order o = new Order(
                        rs.getInt("order_id"),
                        rs.getInt("customer_id"),
                        rs.getDate("order_date").toLocalDate(),
                        rs.getDouble("total_amount"));
                list1.add(o);
            }
        }
        catch (SQLException e)
        {
           e.printStackTrace();
        }
        return list1;
    }
    public ArrayList<OrderedItem> reviewOrderItems(int orderId)
    {
        ArrayList<OrderedItem> list1 = new ArrayList<>();
        try
        {
            Connection con = DBconnection.getConnection();
            String query = "SELECT * FROM ordered_items WHERE order_Id =?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                OrderedItem oi = new OrderedItem(
                        rs.getInt("order_item_id"),
                        rs.getInt("order_id"),
                        rs.getInt("product_id"),
                        rs.getInt("quantity"),
                        rs.getDouble("price_at_purchase"));
                list1.add(oi);
            }
        }
        catch (SQLException e)
        {
           e.printStackTrace();
        }
        return list1;
    }

    public void suggestion(String suggestion)
    {
        try
        {
            Connection con = DBconnection.getConnection();
            String query = "INSERT INTO suggestion VALUES(?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, suggestion);
            ps.executeUpdate();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void showCart()
    {
        ArrayList<OrderedItem> list1 = new ArrayList<>();
        int orderId = 0;
        int productId = 0;
        try
        {
            Connection con = DBconnection.getConnection();
            String query = "SELECT MAX(order_id) AS max_id FROM orders";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next())
            {
                orderId = rs.getInt("max_id");
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();;
        }
        try
        {
            Connection con = DBconnection.getConnection();
            String query = "SELECT * FROM ordered_items WHERE order_id =?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();
            System.out.printf("%-20s %-20s %-20s%n", "Product", "quantity", "Price");
            System.out.println("-".repeat(105));
            while (rs.next())
            {
                productId = rs.getInt("product_id");
                String productName = productName(productId);
                System.out.printf("%-20s %-20d ₹%-19.2f%n", productName, rs.getInt("quantity"), rs.getDouble("price_at_purchase"));

            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();;
        }

    }
}

public class GroceryInventory2
{
    public static void main(String[] args)
    {
        ProductsSql pd = new ProductsSql();
        CustomerSql cp = new CustomerSql();
        OrderSql od = new OrderSql();
        Scanner sc = new Scanner(System.in);
        System.out.println("Who are you? (Enter respective number)");
        System.out.println("1. Shopkeeper");
        System.out.println("2. Customer");
        int identity = sc.nextInt();
        if (identity == 1)
        {
            while (true)
            {
                System.out.println("-------Grocery Store-------");
                System.out.println("1. See Products");
                System.out.println("2. Add New Product");
                System.out.println("3. Add existing Product");
                System.out.println("4. Change Discount amount");
                System.out.println("5. Change price");
                System.out.println("6. Check Low Stock Products");
                System.out.println("7. Product About to Expire");
                System.out.println("8. See suggestions:");
                System.out.println("9. Exit");
                int choice = sc.nextInt();
                switch (choice)
                {
                    case 1:
                        System.out.println("Products List:");
                        var product1 = pd.getAllProducts();
                        System.out.printf("%-20s %-20s %-20s %-20s %-25s%n", "Product", "Category", "Stock", "Price", "Exp Date");
                        System.out.println("-".repeat(105));
                        for (Products p : product1)
                        {
                            System.out.printf("%-20s %-20s %-20d ₹%-19.2f %-25s%n",p.productName, p.category, p.stock, p.price, p.expDate);
                        }
                        System.out.println("\n");
                        break;
                    case 2:
                        System.out.println("Enter the details of new the products:");
                        System.out.print("Name:");
                        sc.nextLine();
                        String name2 = sc.nextLine();
                        System.out.print("Category:");
                        String cat = sc.nextLine();
                        System.out.print("Price:");
                        double price = sc.nextDouble();
                        System.out.print("Stock:");
                        int stock = sc.nextInt();
                        System.out.print("Exp Date:");
                        LocalDate expDate = LocalDate.parse(sc.next());
                        var product2 = pd.addProduct(name2, cat, price, stock, expDate);
                        System.out.printf("%-20s %-20s %-20s %-20s %-20s %-25s%n", "Id", "Product", "Category", "Stock", "Price", "Exp Date");
                        System.out.println("-".repeat(125));
                        for (Products p : product2)
                        {
                            System.out.printf("%-20d %-20s %-20s %-20d ₹%-19.2f %-25s%n",p.productId, p.productName, p.category, p.stock, p.price, p.expDate);
                        }
                        System.out.println("\n");
                        break;
                    case 3:
                        System.out.print("Enter name of existing product:");
                        sc.nextLine();
                        String name3 = sc.nextLine();
                        System.out.print("Enter quantity:");
                        int quantity = sc.nextInt();
                        System.out.print("New Exp Date:");
                        LocalDate expDate3 = LocalDate.parse(sc.next());
                        var product3 = pd.addExistingProduct(name3, quantity, expDate3);
                        System.out.println(name3);
                        System.out.printf("%-20s %-20s %-20s %-20s %-20s %-25s%n", "Id", "Product", "Category", "Stock", "Price", "Exp Date");
                        System.out.println("-".repeat(125));
                        for (Products p : product3)
                        {
                            System.out.printf("%-20d %-20s %-20s %-20d ₹%-19.2f %-25s%n",p.productId, p.productName, p.category, p.stock, p.price, p.expDate);
                        }
                        System.out.println("\n");
                        break;
                    case 4:
                        System.out.print("Product Name:");
                        sc.nextLine();
                        String name4 = sc.nextLine();
                        System.out.print("New Discount (in %):");
                        float dis = sc.nextFloat();
                        System.out.println("Updated Details:");
                        var product4 = pd.changeDiscount(name4, dis);
                        System.out.printf("%-20s %-20s %-20s%n", "Product", "Discount(in %)", "Price");
                        System.out.println("-".repeat(60));
                        for (Products p : product4)
                        {
                            System.out.printf("%-20s %-19.2f ₹%-19.2f%n", p.productName, p.discount, p.price);
                        }
                        System.out.println("\n");
                        break;
                    case 5:
                        System.out.print("Product Name:");
                        sc.nextLine();
                        String name5 = sc.nextLine();
                        System.out.print("New Price:");
                        Double price4 = sc.nextDouble();
                        System.out.println("Updated Details:");
                        var product5 = pd.changePrice(name5, price4);
                        System.out.printf("%-20s %-20s%n", "Product", "Price");
                        System.out.println("-".repeat(40));
                        for (Products p : product5)
                        {
                            System.out.printf("%-20s ₹%-19.2f%n", p.productName,p.price);
                        }
                        System.out.println("\n");
                        break;
                    case 6:
                        System.out.println("Products with less than 20 stock:");
                        var product6 = pd.lowStockProducts();
                        if (product6.size() != 0)
                        {
                            System.out.printf("%-20s %-20s%n", "Product", "Stock");
                            System.out.println("-".repeat(40));
                            for (Products p : product6)
                            {
                                System.out.printf("%-20s %-20d%n", p.productName,p.stock);
                            }
                            System.out.println("\n");
                        }
                        else
                        {
                            System.out.println("No such products.\n");
                        }
                        break;
                    case 7:
                        System.out.println("Products with near expiry date:");
                        var product7 = pd.nearExpProducts();
                        if (product7.size() != 0)
                        {
                            System.out.printf("%-20s %-25s%n", "Product", "Exp Date");
                            System.out.println("-".repeat(45));
                            for (Products p : product7)
                            {
                                System.out.printf("%-20s %-25s%n", p.productName, p.expDate);
                            }
                            System.out.println("\n");
                        }
                        else
                        {
                            System.out.println("No such products.\n");
                        }
                        break;
                    case 8:
                        var list = pd.seeSuggestion();
                        System.out.println("Suggestions");
                        System.out.println("-------------");
                        for(Suggetion s:list)
                        {
                            System.out.println(s.suggestion);
                        }
                        break;
                    case 9:
                        System.out.println("Thanks for visiting our store.");
                        return;
                    default:
                        System.out.println("Choose from given options.\n");
                }
            }
        }
        else if (identity == 2) {
            System.out.print("Enter your name (enter 0 if new Id):");
            sc.nextLine();
            String customerName = sc.nextLine();
            if (customerName.equalsIgnoreCase("0"))
            {
                System.out.print("Enter your name:");
                String Name = sc.nextLine();
                System.out.print("Enter your phone number:");
                long number = sc.nextInt();
                System.out.println("Enter your password:");
                sc.nextLine();
                String password = sc.nextLine();
                cp.newCustomer(Name, number, password);
                while (true)
                {
                    System.out.println("-------Grocery Store-------");
                    System.out.println("1. See Products");
                    System.out.println("2. Order Products");
                    System.out.println("3. Review Previous Orders");
                    System.out.println("4. Suggest Product");
                    System.out.println("5. Exit");
                    int choice = sc.nextInt();
                    switch (choice)
                    {
                        case 1:
                            System.out.println("Products List:");
                            var product1 = pd.getAllProducts();
                            System.out.printf("%-20s %-20s %-20s %-20s %-25s%n", "Product", "Category", "Stock", "Price", "Exp Date");
                            System.out.println("-".repeat(105));
                            for (Products p : product1)
                            {
                                System.out.printf("%-20s %-20s %-20d ₹%-19.2f %-25s%n",p.productName, p.category, p.stock, p.price, p.expDate);
                            }
                            System.out.println("\n");
                            break;
                        case 2:
                            System.out.println("Products List:");
                            int stock = 0;
                            var product2 = pd.getAllProducts();
                            ArrayList<String> productNames = new ArrayList<>();
                            System.out.printf("%-20s %-20s %-20s %-20s %-20s %-25s%n", "Id", "Product", "Category", "Stock", "Price", "Exp Date");
                            System.out.println("-".repeat(125));
                            for (Products p : product2)
                            {
                                stock = p.stock;
                                System.out.printf("%-20d %-20s %-20s %-20d ₹%-19.2f %-25s%n", p.productId, p.productName, p.category, p.stock, p.price, p.expDate);
                            }
                            int i = 1;
                            od.orderIdCreation(Name);
                            while (i != 0)
                            {
                                System.out.println("Enter product id of item you need (Enter 0 if done ordering):");
                                int id = sc.nextInt();
                                String productName = od.productName(id);
                                productNames.add(productName);
                                if (id == 0)
                                {
                                    i = 0;
                                }
                                else
                                {
                                    System.out.println("Enter quantity:");
                                    int quantity = sc.nextInt();
                                    if(quantity<=stock)
                                    {
                                        od.order(Name, id, quantity);
                                    }
                                    else
                                    {
                                        System.out.println("Product out of stock.");
                                    }
                                }
                            }
                            od.showCart();
                            od.updateTotalAmount();
                            break;
                        case 3:
                            System.out.println("Order List:");
                            int orderId = 0;
                            var list1 = od.reviewOrders(Name);
                            System.out.printf("%-20s %-20s %-25s%n", "Id", "Total Amount", "Date");
                            System.out.println("-".repeat(65));
                            for(Order o:list1)
                            {
                                orderId = o.orderId;
                                System.out.printf("%-20d ₹%-19.2f %-25s%n", o.orderId, o.totalAmount, o.curDate);
                            }
                            System.out.print("Enter order Id to view specific items:");
                            int id = sc.nextInt();
                            var list2 = od.reviewOrderItems(id);
                            System.out.printf("%-20s %-20s %-20s%n", "Product", "Quantity", "Price");
                            System.out.println("-".repeat(60));
                            for(OrderedItem oi:list2)
                            {
                                String name = od.productName(oi.productId);
                                System.out.printf("%-20s %-20d ₹%-19.2f%n", name, oi.quantity, oi.priceAtPurchase);
                            }
                            System.out.println("\n");
                            break;
                        case 4:
                            System.out.print("Suggestion:");
                            sc.nextLine();
                            String sugg = sc.nextLine();
                            od.suggestion(sugg);
                            break;
                        case 5:
                            return;
                        default:
                            System.out.println("Choose from given options.\n");
                    }
                }
            }
            else
            {
                System.out.print("Enter your password:");
                String pass = sc.nextLine();
                if (cp.customerVerification(customerName, pass))
                {
                    while (true)
                    {
                        System.out.println("-------Grocery Store-------");
                        System.out.println("1. See Products");
                        System.out.println("2. Order Products");
                        System.out.println("3. Review Previous Orders");
                        System.out.println("4. Suggest Product");
                        System.out.println("5. Exit");
                        int choice = sc.nextInt();
                        switch (choice)
                        {
                            case 1:
                                System.out.println("Products List:");
                                var product1 = pd.getAllProducts();
                                System.out.printf("%-20s %-20s %-20s %-20s %-25s%n", "Product", "Category", "Stock", "Price", "Exp Date");
                                System.out.println("-".repeat(105));
                                for (Products p : product1)
                                {
                                    System.out.printf("%-20s %-20s %-20d ₹%-19.2f %-25s%n",p.productName, p.category, p.stock, p.price, p.expDate);
                                }
                                System.out.println("\n");
                                break;
                            case 2:
                                System.out.println("Products List:");
                                int stock = 0;
                                var product2 = pd.getAllProducts();
                                ArrayList<String> productNames = new ArrayList<>();
                                System.out.printf("%-20s %-20s %-20s %-20s %-20s %-25s%n", "Id", "Product", "Category", "Stock", "Price", "Exp Date");
                                System.out.println("-".repeat(125));
                                for (Products p : product2)
                                {
                                    stock = p.stock;
                                    System.out.printf("%-20d %-20s %-20s %-20d ₹%-19.2f %-25s%n", p.productId, p.productName, p.category, p.stock, p.price, p.expDate);
                                }
                                int i = 1;
                                od.orderIdCreation(customerName);
                                while (i != 0)
                                {
                                    System.out.println("Enter product id of item you need (Enter 0 if done ordering):");
                                    int id = sc.nextInt();
                                    String productName = od.productName(id);
                                    productNames.add(productName);
                                    if (id == 0)
                                    {
                                        i = 0;
                                    }
                                    else
                                    {
                                        System.out.println("Enter quantity:");
                                        int quantity = sc.nextInt();
                                        if(quantity<=stock)
                                        {
                                            od.order(customerName, id, quantity);
                                        }
                                        else
                                        {
                                            System.out.println("Product out of stock.");
                                        }
                                    }
                                }
                                od.showCart();
                                od.updateTotalAmount();
                                break;
                            case 3:
                                System.out.println("Order List:");
                                int orderId = 0;
                                var list1 = od.reviewOrders(customerName);
                                System.out.printf("%-20s %-20s %-25s%n", "Id", "Total Amount", "Date");
                                System.out.println("-".repeat(65));
                                for(Order o:list1)
                                {
                                    orderId = o.orderId;
                                    System.out.printf("%-20d ₹%-19.2f %-25s%n", o.orderId, o.totalAmount, o.curDate);
                                }
                                System.out.print("Enter order Id to view specific items:");
                                int id = sc.nextInt();
                                var list2 = od.reviewOrderItems(id);
                                System.out.printf("%-20s %-20s %-20s%n", "Product", "Quantity", "Price");
                                System.out.println("-".repeat(60));
                                for(OrderedItem oi:list2)
                                {
                                    String name = od.productName(oi.productId);
                                    System.out.printf("%-20s %-20d ₹%-19.2f%n", name, oi.quantity, oi.priceAtPurchase);
                                }
                                System.out.println("\n");
                                break;
                            case 4:
                                System.out.print("Suggestion:");
                                sc.nextLine();
                                String sugg = sc.nextLine();
                                od.suggestion(sugg);
                                break;
                            case 5:
                                return;
                            default:
                                System.out.println("Choose from given options.\n");
                        }
                    }
                }
                else
                {
                    System.out.println("Please enter appropriate name and password.");
                }
            }
        }
        else
        {
            System.out.println("Please choose from given option.\n");
        }
    }
}
