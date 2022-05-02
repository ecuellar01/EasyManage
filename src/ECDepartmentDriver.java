//@author Esau Cuellar

import java.sql.*;
import java.lang.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ECDepartmentDriver {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        //Displays the info to the user.
        System.out.println("Welcome to EC & Sons Department Store Database. This is meant for use by managers only! Select an option.");

        int userChoice,userChoiceB;//Sentinels for the while loop, and if conditions.
        Scanner scanA, scanB, scanC;//Scanners for user input
        int storeID, custID, emplID, percentile;//The id the user will input. For use for the queries
        String dName, fName, lName, address, phone_num;//The strings the user will input. Will be used for the queries.
        boolean found;//Boolean used for the queries.
        char sinon, middleI;//Used for middle_i tuple and Y/N answer.
        double salary;

        ArrayList<Integer> cutomerIds = ECDepartmentStore.customer();//Arraylist that contains the customer ids from the database.
        ArrayList<Integer> empIds = ECDepartmentStore.employee();//Arraylist that contains the employee ids from the database.
        ArrayList<Integer> managerIds = ECDepartmentStore.manager();//Arraylist that contains the manager ids from the database.
        ArrayList<ECDepartmentStore> storeInfo = ECDepartmentStore.storeInfo();//Arraylist that contains the stores and their respective departments

        do{
            //Displays options to the user.
            System.out.println("1. Display Store, Department, Employee, or Customer Information. ");
            System.out.println("2. Provide an employee with a raise. ");
            System.out.println("3. Delete employee from records. ");
            System.out.println("4. Insert a new customer or employee.");
            System.out.println("5. Exit");

            System.out.println("Select an option from 1 - 5: ");//Tells the use to choose an option

            scanA = new Scanner(System.in);
            scanB = new Scanner(System.in);
            scanC = new Scanner(System.in);
            userChoice = scanA.nextInt();

            //Option 1
            if(userChoice == 1) {
                found = false;

                //Displays the 4 options in option 1
                System.out.println("1. For Store Info ");
                System.out.println("2. For Department Info ");
                System.out.println("3. For Customer Info ");
                System.out.println("4. For Employee Info ");

                userChoiceB = scanA.nextInt();

                //Displays store info
                if (userChoiceB == 1) {

                    System.out.println("Enter the store id: ");//Requests input from user.
                    storeID = scanA.nextInt();

                    for (int i = 0; i < storeInfo.size(); ++i)
                        if (storeInfo.get(i).getStore_id() == storeID)//Compares the data with the users input
                            found = true;//Found is true if condition is met

                    if (found)
                        ECDepartmentStore.dispStoreInfo(storeID);//The query is executed. The information the user requested is printed.
                    else
                        System.out.println("Store not found in Database!");//User is notified if store doesn't exist.

                }
                //Displays department info
                else if (userChoiceB == 2) {

                    //Requests input from user.
                    System.out.println("Enter the store id: ");
                    storeID = scanA.nextInt();
                    System.out.println("Enter the department name: ");
                    dName = scanB.nextLine();


                    for (int i = 0; i < storeInfo.size(); ++i)
                        if (storeInfo.get(i).getStore_id() == storeID && storeInfo.get(i).getDept_name().equals(dName))//Compares the data with the users input
                            found = true;//Found is true if condition is met

                    if (found)
                        ECDepartmentStore.dispDeptInfo(storeID, dName);//The query is executed. The information the user requested is printed.
                    else
                        System.out.println("Department does not exist in Database!");//User is notified if id doesn't exist.

                }
                //Displays customer info
                else if (userChoiceB == 3) {
                    //Requests info from the user.
                    System.out.println("Enter the customer id: ");
                    custID = scanB.nextInt();

                    for (int i = 0; i < cutomerIds.size(); ++i)
                        if (cutomerIds.get(i) == custID)//Compares the data with the users input
                            found = true;//Found is true if condition is met

                    if (found)
                        ECDepartmentStore.dispCustomerInfo(custID);//The query is executed. The information the user requested is printed.
                    else
                        System.out.println("Customer does not exist in Database!");//User is notified if id doesn't exist.
                }
                //Displays employee info
                else if (userChoiceB == 4) {
                    //Requests info from user.
                    System.out.println("Enter the employee id: ");
                    emplID = scanA.nextInt();

                    for (int i = 0; i < empIds.size(); ++i)
                        if (empIds.get(i) == emplID)//Compares the data with the users input
                            found = true;//Found is true if condition is met

                    if (found)
                        ECDepartmentStore.dispEmployeeInfo(emplID);//The query is executed. The information the user requested is printed.
                    else
                        System.out.println("Employee does not exist in Database!");//User is notified if id doesn't exist.
                } else
                    System.out.println("Invalid Choice! Returning to start of program...");
            }
            //Option 2
            else if(userChoice == 2){
                found = false;
                //Requests input from user.
                System.out.println("Enter the percent raise as a whole number. Max is 30%: ");
                percentile = scanA.nextInt();

                //Checks for valid input
                if(percentile > 30 || percentile < 1){
                    System.out.println("Invalid raise! Returning to start of program...");
                }
                else {
                    //Requests input from user.
                    System.out.println("Enter the employee id: ");
                    emplID = scanB.nextInt();

                    for (int i = 0; i < managerIds.size(); ++i)
                        if (managerIds.get(i) == emplID)//Compares data with users input.
                            found = true;//Found is true if condition is met.

                    if (found) {
                        System.out.println("Sorry, managers cannot be given a raise through this application...\n");//Error
                    } else {
                        found = false;

                        for (int i = 0; i < empIds.size(); ++i)
                            if (empIds.get(i) == emplID)//Compares data with users input.
                                found = true;//Found is true if condition is met.

                        if (found) {
                            ECDepartmentStore.dispEmployeeInfo(emplID);

                            System.out.println("Would you like to give this employee a " + percentile + "% raise? (Y/N): ");

                            sinon = scanC.next().charAt(0);

                            //If user decides to give raise, query will be executed
                            if (sinon == 'Y')
                                ECDepartmentStore.giveRaise(emplID, percentile);

                            empIds = ECDepartmentStore.employee();//Data is updated in java app.

                        } else
                            System.out.println("Employee not found in Database!\n");//error
                    }
                }
            }
            //Option 3
            else if(userChoice == 3){
                found = false;

                //Displays warning to user. Requests input.
                System.out.println("WARNING! OPTION SHOULD ONLY BE USED IF EMPLOYEE HAS LEFT THE COMPANY" +
                        "OR BEEN TERMINATED FROM EMPLOYMENT!! ");
                System.out.println("Enter the employee id: ");
                emplID = scanB.nextInt();

                for(int i = 0; i < managerIds.size(); ++i)
                    if(managerIds.get(i) == emplID)//Compares data with users input.
                        found = true;//Found is true if condition is met.

                if(found){
                    System.out.println("Warning! This employee is a manager!\n");//Error
                }
                else {

                    for (int i = 0; i < empIds.size(); ++i)
                        if (empIds.get(i) == emplID)//Compares data with users input.
                            found = true;//Found is true if condition is met.

                    if (found) {
                        ECDepartmentStore.dispEmployeeInfo(emplID);//Displays employee info.

                        System.out.println("Would you delete this employee from the records? (Y/N): ");

                        sinon = scanC.next().charAt(0);

                        //Asks for conformation to execute the deletion.
                        if (sinon == 'Y')
                            ECDepartmentStore.deleteEmployee(emplID);


                        empIds = ECDepartmentStore.employee();//Updates data in java app
                    } else
                        System.out.println("Employee not found in Database!\n");//Error
                }
            }
            //Choice 4
            else if(userChoice == 4) {
                found = false;
                //Requests choice from user.
                System.out.println("1. To insert new employee data");
                System.out.println("2. To insert new customer data");

                userChoiceB = scanA.nextInt();

                if (userChoiceB == 1) {
                    //Requests input
                    System.out.println("Enter the new employee id: ");
                    emplID = scanA.nextInt();

                    for (int i = 0; i < empIds.size(); ++i)
                        if (empIds.get(i) == emplID)//Compares the data with the users input
                            found = true;//Found is true if condition is met

                    if (!found) {

                        //Requests input
                        System.out.println("Enter store id: ");
                        storeID = scanA.nextInt();

                        System.out.println("Enter department name: ");
                        dName = scanB.nextLine();

                        for (int i = 0; i < storeInfo.size(); ++i)
                            if (storeInfo.get(i).getStore_id() == storeID && storeInfo.get(i).getDept_name().equals(dName))//Compares the data with the users input
                                found = true;//Found is true if condition is met

                        if(found) {
                            //Requests input for insert statement.
                            System.out.println("Enter first name: ");
                            fName = scanB.nextLine();

                            System.out.println("Enter middle initial: ");
                            middleI = scanC.next().charAt(0);

                            System.out.println("Enter last name: ");
                            lName = scanB.nextLine();

                            System.out.println("Enter salary: ");
                            salary = scanA.nextDouble();

                            System.out.println("Enter the address: ");
                            address = scanB.nextLine();

                            System.out.println("Enter phone number: ");
                            phone_num = scanB.nextLine();

                            ECDepartmentStore.insertEmployee(emplID, fName, middleI, lName, salary, phone_num, storeID, dName, address);//The insert statement is executed.

                            empIds = ECDepartmentStore.employee();//Updates data in java app.
                        }
                        else
                            System.out.println("Department not found in Database! Record cannot be added!\n");//User is notified if id doesn't exist.
                    }
                    else
                        System.out.println("Employee already exists in database! Record cannot be added!\n");//User is notified if id doesn't exist.

                } else if (userChoiceB == 2) {
                    //Requests input
                    System.out.println("Enter the new customer id: ");
                    custID = scanA.nextInt();

                    for (int i = 0; i < cutomerIds.size(); ++i)
                        if (cutomerIds.get(i) == custID)//Compares the data with the users input
                            found = true;//Found is true if condition is met

                    if (!found){
                        //Request input for insert statement.
                        System.out.println("Enter first name: ");
                        fName = scanB.nextLine();

                        System.out.println("Enter middle initial: ");
                        middleI = scanC.next().charAt(0);

                        System.out.println("Enter last name: ");
                        lName = scanB.nextLine();


                        System.out.println("Enter the address: ");
                        address = scanB.nextLine();

                        System.out.println("Enter phone number: ");
                        phone_num = scanB.nextLine();


                        ECDepartmentStore.insertCustomer(custID, fName, middleI, lName, phone_num, address);//The insert statement is executed.

                        cutomerIds = ECDepartmentStore.customer();//Updates data for java app.
                    }
                    else
                        System.out.println("Customer already in Database! Record cannot be added \n");//User is notified if id doesn't exist.
                }
                else
                    System.out.println("Invalid Choice! Returning to start of program...\n");
            }
            else if(userChoice == 5)
                System.out.println("Thank you for using this program!");//User quits app.
            else
                System.out.println("Invalid Choice!\n");//Error

        }
        while(userChoice != 5);
    }

}
