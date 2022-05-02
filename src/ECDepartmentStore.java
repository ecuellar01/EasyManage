//@author Esau Cuellar
import java.sql.*;
import java.lang.*;
import java.util.ArrayList;
/**
 *
 * @author Esau Cuellar
 */
public class ECDepartmentStore {
    //Data members
    private int store_id;
    private String dept_name;

    //Constructors
    ECDepartmentStore(int s, String d, String l, double b) {
        store_id = s;
        dept_name = d;
    }

    ECDepartmentStore() {
        store_id = 0;
        dept_name = "";
    }

    //Getters and Setters
    public void setStore_id(int store_id) {
        this.store_id = store_id;
    }

    public void setDept_name(String dept_name) {
        this.dept_name = dept_name;
    }

    public String getDept_name() {
        return dept_name;
    }

    public int getStore_id() {
        return store_id;
    }


    //Function that connects to the database
    //Function that connects to the database
    public static Connection initializeDatabase() throws SQLException, ClassNotFoundException
    {
        Class.forName("com.mysql.cj.jdbc.Driver");//Opens the driver(mySQL connector)

        //returns a connection. Connects to the database.
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/department_store?useSSL = false", "root", "root");
    }

    //Inserts the data into the database
    public static void insertEmployee(int id, String first_name, char middle_i, String last_name, double salary, String phone_num,
                                      int store_id, String dept_name, String address) throws SQLException, ClassNotFoundException {
        String query = "INSERT INTO employee VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";//The insert statement
        PreparedStatement preState = null;//The PreparedStatement object
        Connection conn = initializeDatabase();//Connects to the database
        try {
            preState = conn.prepareStatement(query);//PreparedStatement is set to the query
            preState.setInt(1, id);//Sets the first ? to the id
            preState.setString(2, first_name);//Sets the second ? to the first name
            preState.setString(3, String.valueOf(middle_i));//Sets the third ? to the middle initial
            preState.setString(4, last_name);//Sets the fourth ? to the last name
            preState.setDouble(5, salary);//Sets the fifth ? to the salary
            preState.setString(6, address);//Sets the sixth ? to the address
            preState.setString(7, phone_num);//Sets the seventh ? to the phone_num
            preState.setInt(8, store_id);//Sets the 8th ? to the store id
            preState.setString(9, dept_name);//Sets the ninth ? to the department
            preState.execute();//Executes the insert statement.
        }
//Closes the statement and connection.
        finally {
            try { if (conn != null) conn.close(); } catch (Exception ignore) { }
            try { if (preState != null) preState.close(); } catch (Exception ignore) { }
        }
    }

    public static void insertCustomer(int id, String first_name, char middle_i, String last_name,
                                      String phone_num, String address) throws SQLException, ClassNotFoundException {
        String query = "INSERT INTO customer VALUES(?, ?, ?, ?, ?, ?)";//The insert statement
        PreparedStatement preState = null;//The PreparedStatement object
        Connection conn = initializeDatabase();//Connects to the database
        try {
            preState = conn.prepareStatement(query);//PreparedStatement is set to the query
            preState.setInt(1, id);//Sets the first ? to the id
            preState.setString(2, first_name);//Sets the second ? to the first name
            preState.setString(3, String.valueOf(middle_i));//Sets the third ? to the middle initial
            preState.setString(4, last_name);//Sets the fourth ? to the last name
            preState.setString(5, address);//Sets the fifth ? to the address
            preState.setString(6, phone_num);//Sets the sixth ? to the phone_num

            preState.execute();//Executes the insert statement.
        }

//Closes the statement and connection.
        finally {
            try { if (conn != null) conn.close(); } catch (Exception ignore) { }
            try { if (preState != null) preState.close(); } catch (Exception ignore) { }
        }
    }

    //Loads all of the ids from the employee table into an ArrayList
    public static ArrayList<Integer> employee() throws SQLException,
            ClassNotFoundException {
        String query = "SELECT ID FROM employee";
        PreparedStatement preState = null;
        Connection conn = initializeDatabase();//Connects to the database
        ResultSet rs;
        ArrayList<Integer> ids = new ArrayList<>();
        try {
            preState = conn.prepareStatement(query);
            rs = preState.executeQuery();//Executes the query. While rs has values, said values are added to the ArrayList ids
            while (rs.next()) ids.add(rs.getInt("ID"));
            return ids;//Returns the ArrayList ids
        } //Closes the statement and the connection.
        finally {
            try { if (conn != null) conn.close(); } catch (Exception ignore) { }
            try { if (preState != null) preState.close(); } catch (Exception ignore) { }
        }
    }

    public static ArrayList<Integer> manager() throws SQLException,
            ClassNotFoundException {
        String query = "SELECT manager_id FROM manager";
        PreparedStatement preState = null;
        Connection conn = initializeDatabase();//Connects to the database
        ResultSet rs;
        ArrayList<Integer> ids = new ArrayList<>();
        try {
            preState = conn.prepareStatement(query);
            rs = preState.executeQuery();//Executes the query. While rs has values, said values are added to the ArrayList ids
            while (rs.next())
                ids.add(rs.getInt("manager_id"));
            return ids;//Returns the ArrayList ids
        } //Closes the statement and the connection.
        finally {
            try { if (conn != null) conn.close(); } catch (Exception ignore) { }
            try { if (preState != null) preState.close(); } catch (Exception ignore) { }
        }
    }

    public static ArrayList<Integer> customer() throws SQLException,
            ClassNotFoundException {
        String query = "SELECT customer_id FROM customer";
        PreparedStatement preState = null;
        Connection conn = initializeDatabase();//Connects to the database
        ResultSet rs;
        ArrayList<Integer> ids = new ArrayList<>();
        try {
            preState = conn.prepareStatement(query);
            rs = preState.executeQuery();//Executes the query. While rs has values, said values are added to the ArrayList ids
            while (rs.next()) ids.add(rs.getInt("customer_id"));
            return ids;//Returns the ArrayList ids
        } //Closes the statement and the connection.
        finally {
            try { if (conn != null) conn.close(); } catch (Exception ignore) { }
            try { if (preState != null) preState.close();
            } catch (Exception ignore) { }
        }
    }

    public static ArrayList<ECDepartmentStore> storeInfo() throws SQLException, ClassNotFoundException {
        String query = "SELECT store_id, dept_name FROM department";
        PreparedStatement preState = null;
        Connection conn = initializeDatabase();
        ResultSet rs;
        ArrayList<ECDepartmentStore> joins = new ArrayList<>();
        try {
            preState = conn.prepareStatement(query);//Sets the statement to the query
            rs = preState.executeQuery();//executes the query Adds all of the information into an ArrayList
            while (rs.next()) {
                ECDepartmentStore toAdd = new ECDepartmentStore();//Creates a new ECDepartmentStore object
                toAdd.store_id = rs.getInt("store_id");//Gets the store id from the database
                toAdd.dept_name = rs.getString("dept_name");//Gets the dept_name from the database
                joins.add(toAdd);//toAdd is added to the ArrayList, joins
            }
            return joins;//joins is returned
        } //The statement and connection are closed.
        finally {
            try { if (conn != null) conn.close(); } catch (Exception ignore) { }
            try { if (preState != null) preState.close(); } catch (Exception ignore) { }
        }
    }

    public static void dispStoreInfo(int store_id) throws SQLException, ClassNotFoundException {
        String queryA = "SELECT * FROM STORE WHERE store_id = ?";//The insert statement
        String queryB = "select department.dept_name, num_ext, first_name, last_name from department join employee using(dept_name, store_id) " +
                "where department.store_id = ? and ID = manager_id";//The insert statement
        PreparedStatement preStateA = null;//The PreparedStatement object
        PreparedStatement preStateB = null;
        Connection conn = initializeDatabase();//Connects to the database
        ResultSet rsA, rsB;
        try {
            preStateA = conn.prepareStatement(queryA);//PreparedStatement is set to the query
            preStateB = conn.prepareStatement(queryB);//PreparedStatement is set to the query
            preStateA.setInt(1, store_id);//Sets the first ? to the id
            preStateB.setInt(1, store_id);//Sets the first ? to the id
            rsA = preStateA.executeQuery();//Executes the query.
            rsB = preStateB.executeQuery();//Executes the query.

            while (rsA.next()) {
                System.out.println("EC and Son Department Store#" + rsA.getInt("store_id")
                        + " | Phone Number: " + rsA.getString("phone_number") + " | Address: " + rsA.getString("address"));
            }

            System.out.println("");

            System.out.println("Department Info");

            while (rsB.next()) {
                System.out.println(" Department: " + rsB.getString("dept_name") + " | Number Extension: "
                        + rsB.getString("num_ext") + " | Manager: " + rsB.getString("first_name")
                        + " " + rsB.getString("last_name"));
            }

            System.out.println("");
        }
//Closes the statement and connection.
        finally {
            try { if (conn != null) conn.close(); } catch (Exception ignore) { }
            try { if (preStateA != null) preStateA.close(); } catch (Exception ignore) { }
            try { if (preStateB != null) preStateB.close(); } catch (Exception ignore) { }
        }
    }

    public static void dispDeptInfo(int store_id, String dept_name) throws SQLException, ClassNotFoundException {
        String queryA = "SELECT * FROM department WHERE store_id = ? and dept_name = ?";//The insert statement
        String queryB = "select first_name, middle_i, last_name, salary from employee where store_id = ? and dept_name = ?";//The insert statement
        PreparedStatement preStateA = null;//The PreparedStatement object
        PreparedStatement preStateB = null;
        Connection conn = initializeDatabase();//Connects to the database
        ResultSet rsA, rsB;
        try {
            preStateA = conn.prepareStatement(queryA);//PreparedStatement is set to the query
            preStateB = conn.prepareStatement(queryB);//PreparedStatement is set to the query
            preStateA.setInt(1, store_id);
            preStateA.setString(2, dept_name);
            preStateB.setInt(1, store_id);
            preStateB.setString(2, dept_name);
            rsA = preStateA.executeQuery();//Executes the query.
            rsB = preStateB.executeQuery();//Executes the query.

            while (rsA.next()) {
                System.out.println("EC and Son Department Store#" + rsA.getInt("store_id")
                        + " | Department: " + rsA.getString("dept_name"));
            }

            System.out.println("");

            System.out.println("Department Info");

            while (rsB.next()) {
                System.out.println("Employee: " + rsB.getString("first_name") + " " + rsB.getString("middle_i")
                        + ". " + rsB.getString("last_name") + " | Salary: " + rsB.getDouble("salary"));
            }

            System.out.println("");
        }
//Closes the statement and connection.
        finally {
            try { if (conn != null) conn.close(); } catch (Exception ignore) { }
            try { if (preStateA != null) preStateA.close(); } catch (Exception ignore) { }
            try { if (preStateB != null) preStateB.close(); } catch (Exception ignore) { }
        }
    }

    public static void dispCustomerInfo(int customer_id) throws SQLException, ClassNotFoundException {
        String queryA = "SELECT * FROM customer WHERE customer_id = ?";//The insert statement
        String queryB = "select item_name, price, store_id, dept_name, date, time from buys join customer using(customer_id) " +
                "join item using(item_id) join bought using(receipt_id) where customer.customer_id = ?;";//The insert statement
        PreparedStatement preStateA = null;//The PreparedStatement object
        PreparedStatement preStateB = null;
        Connection conn = initializeDatabase();//Connects to the database
        ResultSet rsA, rsB;
        try {
            preStateA = conn.prepareStatement(queryA);//PreparedStatement is set to the query
            preStateB = conn.prepareStatement(queryB);//PreparedStatement is set to the query
            preStateA.setInt(1, customer_id);//Sets the first ? to the id
            preStateB.setInt(1, customer_id);//Sets the first ? to the id
            rsA = preStateA.executeQuery();//Executes the query.
            rsB = preStateB.executeQuery();//Executes the query.

            while (rsA.next()) {
                System.out.println("Customer ID#" + rsA.getInt("customer_id") + " | Name: " + rsA.getString("first_name")
                        + " " + rsA.getString("middle_i") + ". " + rsA.getString("last_name")
                        + " | Phone Number: " + rsA.getString("phone_num") + " | Address: " + rsA.getString("address"));
            }

            System.out.println("");

            System.out.println("Purchase History");

            while (rsB.next()) {
                System.out.println("Item Name: " + rsB.getString("item_name") + " | Price: "
                        + rsB.getDouble("price") + " | Store Bought: EC and Son Store#" + rsB.getInt("store_id")
                        + " | Department: " + rsB.getString("dept_name") + " | Date Purchased: " + rsB.getDate("date")
                        + " | Time: " + rsB.getTime("time"));
            }
            System.out.println("");

        }
        finally {
            try { if (conn != null) conn.close(); } catch (Exception ignore) { }
            try { if (preStateA != null) preStateA.close(); } catch (Exception ignore) { }
            try { if (preStateB != null) preStateB.close(); } catch (Exception ignore) { }
        }
    }

    public static void dispEmployeeInfo(int id) throws SQLException, ClassNotFoundException {
        String queryA = "SELECT first_name, middle_i, last_name, salary, store_id, dept_name FROM employee WHERE id = ?";//The insert statement
        PreparedStatement preStateA = null;//The PreparedStatement object
        Connection conn = initializeDatabase();//Connects to the database
        ResultSet rsA;
        try {
            preStateA = conn.prepareStatement(queryA);//PreparedStatement is set to the query
            preStateA.setInt(1, id);//Sets the first ? to the id
            rsA = preStateA.executeQuery();//Executes the insert statement.

            while (rsA.next()) {
                System.out.println("Name: " + rsA.getString("first_name")
                        + " " + rsA.getString("middle_i") + ". " + rsA.getString("last_name")
                        + " | Salary: " + rsA.getDouble("salary") + " | EC and Son Store# "
                        + rsA.getInt("store_id") + " | Department: " + rsA.getString("dept_name"));
            }
        } finally {
            try { if (conn != null) conn.close(); } catch (Exception ignore) { }
            try { if (preStateA != null) preStateA.close(); } catch (Exception ignore) { }
        }
    }

    public static void giveRaise(int id, double percentile) throws SQLException, ClassNotFoundException {
        String queryA = "update employee set salary = salary + (salary * ? / 100) where ID = ?;";//The insert statement
        PreparedStatement preStateA = null;//The PreparedStatement object
        Connection conn = initializeDatabase();//Connects to the database
        try {
            preStateA = conn.prepareStatement(queryA);//PreparedStatement is set to the query
            preStateA.setDouble(1, percentile);//Sets the first ? to the percentile
            preStateA.setInt(2, id);//Sets the second ? to the id
            preStateA.execute();//Executes the insert statement.
        } finally {
            try { if (conn != null) conn.close(); } catch (Exception ignore) { }
            try { if (preStateA != null) preStateA.close(); } catch (Exception ignore) {
            }
        }
    }

    public static void deleteEmployee(int id) throws SQLException, ClassNotFoundException {
        String queryA = "delete from employee where id = ?";//The insert statement
        PreparedStatement preStateA = null;//The PreparedStatement object
        Connection conn = initializeDatabase();//Connects to the database
        try {
            preStateA = conn.prepareStatement(queryA);//PreparedStatement is set to the query
            preStateA.setDouble(1, id);//Sets the first ? to the id
            preStateA.execute();//Executes the insert statement.
        } finally {
            try { if (conn != null) conn.close(); } catch (Exception ignore) { }
            try { if (preStateA != null) preStateA.close(); } catch (Exception ignore) { }
        }
    }
}
