import java.io.IOException;
import java.util.Scanner;
import java.sql.*;

public class StudenDBClass
{
    static int i=0;
    static int subject_id;
    static String name;
    static int credit;

    static String ID;
    static String pass;
    static String Type;

    static String EntryNo;
    static String stu_name;
    static String dept;
    static String degree;
    static String dob;

    static int[] AssID = new int[2];
    static int[] Marks = new int[2];

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/";
    static final String USER = "root";
    static final String PASS = "testRoot";

    static Connection con = null;
    static Statement stmt = null;
    static ResultSet rs = null;
    static Scanner in = new Scanner(System.in);

    public static void createTables(String dbName) throws IOException, ClassNotFoundException
    {
        try
        {
            Class.forName(JDBC_DRIVER);
            String url = DB_URL;
            url = url + dbName;
            con = DriverManager.getConnection(url,USER,PASS);
            System.out.println("URL: " + url);
            System.out.println("Connection: " + con);
            stmt = con.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS login " +
                        "(EntryID VARCHAR(255) NOT NULL, " +
                        " Pass VARCHAR(255), " +
                        " Type VARCHAR(255), " +
                        " PRIMARY KEY ( EntryID ))";

            stmt.executeUpdate(sql);
            System.out.println("Created table in given database...");

            String sql = "CREATE TABLE IF NOT EXISTS student " +
                            "(EntryID VARCHAR(255) NOT NULL , " +
                            " Stu_name VARCHAR(255), " +
                            " Dept VARCHAR(255), " +
                            " Degree VARCHAR(255)," +
                            " DOB VARCHAR(255)," +
                            " FOREIGN KEY (EntryID) REFERENCES login (EntryID) ON DELETE CASCADE) ";

            stmt.executeUpdate(sql);
            System.out.println("Created table in given database...");

            String sql = "CREATE TABLE IF NOT EXISTS subject " +
                            "(EntryID  VARCHAR(255) NOT NULL, " +
                            " SubjectID INTEGER NOT NULL, " +
                            " Subject_name VARCHAR(255), " +
                            " Credits INTEGER, " +
                            " PRIMARY KEY (EntryID, SubjectID)," +
                            " FOREIGN KEY (EntryID) REFERENCES login (EntryID) ON DELETE CASCADE)";

            stmt.executeUpdate(sql);
            System.out.println("Created table in given database...");

            String sql = "CREATE TABLE IF NOT EXISTS marks " +
                            "(EntryID  VARCHAR(255) NOT NULL, " +
                            " SubjectID INTEGER NOT NULL, " +
                            " AssignID INTEGER NOT NULL, " +
                            " Marks INTEGER, " +
                            " PRIMARY KEY (EntryID, SubjectID, AssignID), "+
                            " FOREIGN KEY (EntryID) REFERENCES login (EntryID) ON DELETE CASCADE, "+
                            " FOREIGN KEY (EntryId,SubjectID) REFERENCES subject (EntryId,SubjectID) ON DELETE CASCADE )";

            stmt.executeUpdate(sql);
            System.out.println("Created table in given database...Marks");

            String sql = "CREATE TABLE IF NOT EXISTS Grades " +
                            " (EntryID VARCHAR(255) NOT NULL, " +
                            " SubjectID INTEGER NOT NULL, " +
                            " TotalMarks INTEGER, " +
                            " Grades varchar(255), " +
                            " PRIMARY KEY (EntryID, SubjectID)," +
                            " FOREIGN KEY (EntryID) REFERENCES login (EntryID) ON DELETE CASCADE,"+
                            " FOREIGN KEY (EntryId,SubjectID) REFERENCES subject (EntryID, SubjectID) ON DELETE CASCADE)";

            stmt.executeUpdate(sql);
            System.out.println("Created table in given database...Grades");
        }
        catch (SQLException e)
        {
            System.out.println("Error in SQL Query!");
            System.err.println(e);
            e.printStackTrace();
        }
    }

    public static void Studentlogin(String dbName) throws IOException
    {
        try
        {
            Class.forName(JDBC_DRIVER); // database connection string;
            String url = DB_URL;
            url = url + dbName;
            con = DriverManager.getConnection(url,USER,PASS);
            System.out.println("URL: " + url);
            System.out.println("Connection: " + con);
            stmt = con.createStatement();
            Statement stmt1 = con.createStatement();
            Statement stmt2 = con.createStatement();
            Statement stmt3 = con.createStatement();
            System.out.println("Welcome Student!");
            System.out.println("Enter you ID!: ");
            ID = in.next();
            /*
             * selects all the entries with the specified ID
             */
            rs = stmt.executeQuery("SELECT * from login where EntryID ='" +ID+ "'");
            while(rs.next())
            {
                System.out.println("Enter you password!: ");
                pass = in.next();
                /*
                 * checks for the correct combination of password and username
                 */
                if(ID.equals(rs.getString("EntryID")) && pass.equals(rs.getString("Pass")) && rs.getString("Type").equals("Student"))
                {
                    System.out.println("Sucessfull login!");
                    System.out.println("1. add course to student");
                    System.out.println("2. remove course to student");

                    System.out.println("3. displaying Grade information");
                    System.out.println("4. change password");
                    System.out.println("5. EXIT");
                    int choice = in.nextInt();

                    /*
                     * add a course to student registration cart;
                     */
                    if(choice == 1)
                    {
                        System.out.println("Enter SubjectID");
                        int SubjectID = in.nextInt();
                        System.out.println("Enter Subject Name");
                        String Name = in.next();
                        System.out.println("Enter Subject Credits");
                        int Credits = in.nextInt();
                        stmt.executeUpdate("INSERT INTO subject VALUES ('" +ID+ "','" +SubjectID+ "','" +Name+ "','" +Credits+ "')");
                        rs = stmt.executeQuery("SELECT *  from subject where EntryID='"+ID+"'");
                        while(rs.next())
                        {

                            subject_id = rs.getInt("SubjectId");
                            name = rs.getString("Subject_name");
                            credit = rs.getInt("Credits");
                            System.out.println("EntryID:\t"+ID+"SubjectID:\t"+subject_id+"\tSubject Name:\t"+name+"\tCredits:\t"+credit);

                        }
                        rs.close();
                        return;
                    }
                    else if(choice == 2)
                    {
                        System.out.println("Enter SubjectID");
                        int SubjectID = in.nextInt();
                        ID=rs.getString("EntryID");
                        stmt.executeUpdate("DELETE from subject where SubjectID='" +SubjectID+ "'");
                        rs = stmt.executeQuery("SELECT *  from subject where EntryID='"+ID+"'");
                        while(rs.next())
                        {

                            subject_id = rs.getInt("SubjectId");
                            name = rs.getString("Subject_name");
                            credit = rs.getInt("Credits");
                            System.out.println("EntryID="+ID+"SubjectID:\t"+subject_id+"\tSubject Name:\t"+name+"\tCredits:\t"+credit);

                        }
                        rs.close();
                        return;
                    }
                    else if(choice == 3)
                    {
                        rs = stmt1.executeQuery("SELECT M.EntryID as id,SUM(M.Marks) as total,S.SubjectID as subject_id,S.Subject_name as name from marks M,subject S where M.EntryID = '"+ID+"'");
                        while(rs.next())
                        {
                            String entry = rs.getString("id");
                            subject_id = rs.getInt("subject_id");
                            int m = rs.getInt("total");
                            name = rs.getString("name");
                            //System.out.println("EntryID:\t"+entry+"\tMarks:\t"+m);

                            System.out.println("EntryID:\t"+entry+"SubjectID:\t"+subject_id+"\tMarks:\t"+m+"\tSubject name:\t"+name);
                            rs = stmt2.executeQuery("SELECT Count(*) as c from Grades where EntryID = '" +entry+ "'AND SubjectID='"+subject_id+"'");

                            while(rs.next())
                            {
                                String Grade;
                                int count = rs.getInt("c");
                                if(count>=1)
                                {
                                    System.out.println("already present");

                                }
                                else
                                {
                                    if(m >= 80)
                                    {
                                        Grade = "A";
                                        System.out.println("A");
                                    }
                                    else if (m >= 72)
                                    {
                                        Grade = "A-";
                                        System.out.println("A-");
                                    }
                                    else if (m >= 64)
                                    {
                                        Grade = "B";
                                        System.out.println("B");
                                    }
                                    else if (m >= 56)
                                    {
                                        Grade = "B-";
                                        System.out.println("B-");
                                    }
                                    else if (m >= 48)
                                    {
                                        Grade = "C";
                                        System.out.println("C");
                                    }
                                    else if (m >= 40)
                                    {
                                        Grade = "C-";
                                        System.out.println("C-");
                                    }
                                    else if (m >= 32)
                                    {
                                        Grade = "D";
                                        System.out.println("D");
                                    }
                                    else
                                    {
                                        Grade = "F";
                                        System.out.println("F");
                                    }
                                    stmt.executeUpdate("INSERT into Grades values('"+entry+"','"+subject_id+"','"+m+"','"+Grade+"')");
                                    System.out.println("EntryID: "+entry+" SubjectID: "+subject_id+" Marks "+m+" Grades "+Grade);


                                }

                            }

                        }
                        rs.close();
                        return;

                    }
                    //to change the password
                    else if(choice == 4)
                    {
                        System.out.println("Enter the new password");
                        pass = in.next();
                        stmt.executeUpdate("UPDATE login set Pass='"+pass+"'where EntryID ='"+ID+"'");
                        rs = stmt.executeQuery("SELECT *  from login where EntryID = '"+ID+"'");
                        while(rs.next())
                        {
                            ID = rs.getString("EntryID");
                            pass = rs.getString("Pass");
                            System.out.println("EntryID= "+ID+"Pass= "+pass);
                        }
                        rs.close();
                        return;
                    }
                    else if(choice == 5)
                    {
                        System.out.println("exit");
                        return;
                    }
                    else
                    {
                        System.out.println("Wrong Choice!!!");
                        Studentlogin(dbName);
                    }
                    rs.close();
                }
                else
                {
                    System.out.println("Login failed!");
                    Studentlogin(dbName);
                }
            }
        }
        catch(SQLException e)
        {
            System.out.println("Error in SQL Query!");
            System.err.println(e);
            e.printStackTrace();
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("Error in class!");
            System.err.println(e);
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    /*
     * menu portal for TA, to assign marks to subject assignments for a subject entry;
     */
    public static void talogin(String dbName) throws IOException
    {
        try
        {
            Class.forName(JDBC_DRIVER); //database connection string;
            String url = DB_URL;
            url = url + dbName;
            con = DriverManager.getConnection(url,USER,PASS);
            System.out.println("URL: " + url);
            System.out.println("Connection: " + con);
            stmt = con.createStatement(rs.TYPE_SCROLL_INSENSITIVE, rs.CONCUR_UPDATABLE);
            Statement stmt1 = con.createStatement(rs.TYPE_SCROLL_INSENSITIVE, rs.CONCUR_UPDATABLE);
            Statement stmt2 = con.createStatement(rs.TYPE_SCROLL_INSENSITIVE, rs.CONCUR_UPDATABLE);
            System.out.println("Welcome TA!");
            System.out.println("Enter you TA-ID!: ");
            ID = in.next();
            rs = stmt.executeQuery("SELECT * from login where EntryID = '" +ID+ "'");

            while (rs.next())
            {
                System.out.println("Enter you password!: ");
                pass = in.next();
                if(ID.equals(rs.getString("EntryID")) && pass.equals(rs.getString("Pass")) && rs.getString("Type").equals("TA"))
                {

                    //rs = stmt.executeQuery("SELECT Pass from login where EntryID = '" +ID+ "'");
                    //while (rs.next())
                    //{
                    //if(pass.equals(rs.getString("Pass")))
                    //{
                    System.out.println("Sucessfull login!");
                    System.out.println("1. giving marks");
                    System.out.println("2. change password");
                    System.out.println("3. exit");
                    int choice = in.nextInt();
                    if(choice == 1)
                    {

                        System.out.println("Enter SubjectID");
                        int SubjectID = in.nextInt();
                        int num1=0;
                        rs = stmt1.executeQuery("SELECT COUNT(SubjectID) as num from subject where SubjectID ='"+SubjectID+"'");
                        while(rs.next())
                        {
                            num1 = rs.getInt("num");
                            System.out.println("num: "+num1);
                        }

                        rs = stmt2.executeQuery("SELECT * from subject where SubjectID = '"+SubjectID+"'");
                        while (num1>0)
                        {
                            while(rs.next())//(rs.absolute(num1)==true)//(rs.next() && rs.relative(1))
                            {
                                System.out.println("in");
                                ID = rs.getString("EntryID");
                                System.out.println("ID: "+ID);
                                System.out.println("enter marks of 2 assignments for student's subject");
                                for(i =0; i<2; i++)
                                {
                                    System.out.println("Enter the assigmnet ID");
                                    AssID[i] = in.nextInt();
                                    System.out.println("Enter the marks");
                                    Marks[i] = in.nextInt();

                                    stmt.executeUpdate("INSERT into marks values('"+ID+"','"+SubjectID+"','"+AssID[i]+"','"+Marks[i]+"')");
                                    System.out.println("mmm");

                                }

                                num1--;

                            }
                        }


                    }

                    else if(choice==2)
                    {
                        System.out.println("Enter the new password");
                        pass = in.next();
                        stmt.executeUpdate("UPDATE login set Pass='"+pass+"'where EntryID ='"+ID+"'");
                        rs = stmt.executeQuery("SELECT *  from login where EntryID = '"+ID+"'");
                        while(rs.next())
                        {
                            ID = rs.getString("EntryID");
                            pass = rs.getString("Pass");
                            System.out.println("EntryID= "+ID+"Pass= "+pass);

                        }
                        rs.close();
                        return;

                    }
                    else if(choice == 3)
                    {
                        System.out.println("exit");
                        return;
                    }
                    else
                    {
                        System.out.println("Wrong choice!!");
                        talogin(dbName);
                    }
                }
                else
                {
                    System.out.println("Enter correct password!");
                    talogin(dbName);
                }
            }
        }
        catch(SQLException e)
        {
            System.out.println("Error in SQL Query!");
            System.err.println(e);
            e.printStackTrace();
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("Error in Class!");
            System.err.println(e);
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /*
     * sign in window that can make a new entry for Student / TA;
     */
    public static void signin(String dbName) throws IOException
    {
        try
        {
            Class.forName(JDBC_DRIVER); //database connection string
            String url = DB_URL;
            url = url + dbName;
            con = DriverManager.getConnection(url,USER,PASS);
            System.out.println("URL: " + url);
            System.out.println("Connection: " + con);
            stmt = con.createStatement();

            System.out.println("Enter the IDNumber!");
            ID = in.next();
            System.out.println("Enter Password");
            pass = in.next();
            System.out.println("Student/TA");
            Type = in.next();

            //update by inserting the new entry in main table;
            stmt.executeUpdate("INSERT into login Values('"+ID+"','"+pass+"','"+Type+"')");
            System.out.println("Sign In Done!");
            if(Type.equals("Student"))
            {
                System.out.println("Enter your name!");
                stu_name = in.next();
                System.out.println("Enter your department");
                dept = in.next();
                System.out.println("Enter your degree");
                degree = in.next();
                System.out.println("Enter your DOB");
                dob = in.next();
                stmt.executeUpdate("INSERT into student Values('"+ID+"','"+stu_name+"','"+dept+"','"+degree+"','"+dob+"')");
                System.out.println("Completed Sign In Process");
                Studentlogin(dbName);
            }
            else if(Type.equals("TA"))
            {
                talogin(dbName);
            }

            return;
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("Error in Class!");
            System.err.println(e);
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (SQLException e)
        {
            System.out.println("Error in SQL Query!");
            System.err.println(e);
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /*
     * searching menu portal for everyone to check the students information according to ID,Name or any other field;
     */
    public static void search(String dbName) throws IOException
    {
        try
        {
            Class.forName(JDBC_DRIVER); //database connectivity
            String url = DB_URL;
            url = url +dbName;
            con = DriverManager.getConnection(url,USER,PASS);
            System.out.println("URL: " + url);
            System.out.println("Connection: " + con);
            stmt = con.createStatement();

            System.out.println("How you want to search?\n1. SubjectID\n2. Student Name\n3. Department\n4. Degree\n5. DOB\n6. Exit");
            int Search = in.nextInt();
            //to search for a student information using studentID;
            if(Search == 1)
            {
                System.out.println("Enter the EntryID.");
                String No = in.next();
                //SQL Executed Query is stored in a statement, ResultSet check the database and hold attributes;
                rs = stmt.executeQuery("SELECT * from student where EntryID='" + No + "'");
                while(rs.next())
                {
                    EntryNo = rs.getString("EntryID");
                    stu_name = rs.getString("Stu_name");
                    dept = rs.getString("Dept");
                    degree = rs.getString("Degree");
                    dob = rs.getString("DOB");

                    //display of the generated Query;
                    System.out.println("EntryNo:\t"+EntryNo+"\tStudent Name:\t"+stu_name+"\tDepartment:\t"+dept+"\tDegree:\t"+degree+"\tDOB:\t"+dob);

                }
                rs.close();
                search(dbName);
                //return;

            }
            //to search for a student information using name;
            else if (Search == 2)
            {
                System.out.println("Enter the Student Name.");
                String No = in.next();
                rs = stmt.executeQuery("SELECT * from student where Stu_name = '" + No + "'");
                while(rs.next())
                {
                    EntryNo = rs.getString("EntryID");
                    stu_name = rs.getString("Stu_name");
                    dept = rs.getString("Dept");
                    degree = rs.getString("Degree");
                    dob = rs.getString("DOB");
                    //display of results of generated Query;
                    System.out.println("EntryNo:\t"+EntryNo+"\tStudent Name:\t"+stu_name+"\tDepartment:\t"+dept+"\tDegree:\t"+degree+"\tDOB:\t"+dob);

                }
                rs.close();
                search(dbName);//return;
            }
            //for displaying student information with Department name;
            else if (Search == 3)
            {
                System.out.println("Enter the Department Name.");
                String No = in.next();
                rs = stmt.executeQuery("SELECT * from student where Dept = '" + No + "'");
                while(rs.next())
                {
                    EntryNo = rs.getString("EntryID");
                    stu_name = rs.getString("Stu_name");
                    dept = rs.getString("Dept");
                    degree = rs.getString("Degree");
                    dob = rs.getString("DOB");
                    System.out.println("EntryNo:\t"+EntryNo+"\tStudent Name:\t"+stu_name+"\tDepartment:\t"+dept+"\tDegree:\t"+degree+"\tDOB:\t"+dob);

                }
                rs.close();
                search(dbName);//return;
            }
            //display of student information by the Degree Name;
            else if (Search == 4)
            {
                System.out.println("Enter the Degree Name.");
                String No = in.next();
                rs = stmt.executeQuery("SELECT * from student where Degree = '" + No + "'");
                while(rs.next())
                {
                    EntryNo = rs.getString("EntryID");
                    stu_name = rs.getString("Stu_name");
                    dept = rs.getString("Dept");
                    degree = rs.getString("Degree");
                    dob = rs.getString("DOB");
                    System.out.println("EntryNo:\t"+EntryNo+"\tStudent Name:\t"+stu_name+"\tDepartment:\t"+dept+"\tDegree:\t"+degree+"\tDOB:\t"+dob);

                }
                rs.close();
                search(dbName);//return;
            }
            //display of student information by Date of Birth in the form "DD/MM/YYYY"
            else if (Search == 5)
            {
                System.out.println("Enter the Date of Birth (DD/MM/YYYY).");
                String No = in.next();
                rs = stmt.executeQuery("SELECT * from student where DOB = '" + No + "'");
                while(rs.next())
                {
                    EntryNo = rs.getString("EntryID");
                    stu_name = rs.getString("Stu_name");
                    dept = rs.getString("Dept");
                    degree = rs.getString("Degree");
                    dob = rs.getString("DOB");
                    System.out.println("EntryNo:\t"+EntryNo+"\tStudent Name:\t"+stu_name+"\tDepartment:\t"+dept+"\tDegree:\t"+degree+"\tDOB:\t"+dob);

                }
                rs.close();
                search(dbName);//return;
            }
            //in case you need to exit;
            else if(Search==6)
            {
                System.out.println("Exiting.............!");
                return;
            }
            else
            {
                System.out.println("Wrong choice!!");
                search(dbName);
            }
        }
        /*
         * handles the exceptions of SQL and IO by displaying information;
         */
        catch(IOException e)
        {
            System.out.println("Error in IO!");
            System.err.println(e);
            e.printStackTrace();
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("Error in Class!");
            System.err.println(e);
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (SQLException e)
        {
            System.out.println("Error in SQL Query!");
            // TODO Auto-generated catch block
            System.err.println(e);
            e.printStackTrace();
        }
    }

    /*
     * main menu window for Student Login, TA login, Searching, Sign In that is recursively called each time a selection is made;
     */
    public static void Menu1(String dbName)
    {
        try
        {
            System.out.println("Enter choice: \n1. Student Login\n2. TA Login\n3. SignUp\n4. Searching the database\n5. Exit");
            int in1 = in.nextInt();
            if(in1 == 1)
            {
                Studentlogin(dbName); //student login portal;
                Menu1(dbName); //display of main menu after this;

            }
            else if(in1 == 2)
            {
                //TA login
                talogin(dbName); //TA login portal;
                Menu1(dbName); //display of main menu after this;

            }
            else if(in1 == 3)
            {
                //Sign In
                signin(dbName); //Sign In window for new registrations;
                Menu1(dbName); //display of main menu after this;
            }
            else if(in1 == 4)
            {
                //Searching info
                search(dbName); //displays different student search options;
                Menu1(dbName); //display of main menu after this;
            }
            else if(in1 == 5)
            {
                System.out.println("Exiting...........!");//way to exit the System;
                System.exit(1);
            }
            else
            {
                System.out.println("Wrong choice!!");
                Menu1(dbName); //in case of wrong choice, portal is redirected;
            }
        }
        catch(IOException e)
        {
            System.out.println("Error!");
            System.err.println(e);
            e.printStackTrace(); //handles the exception by displaying the information;
        }
    }

    /*
     * main that calls "Checkdb" to check database, "createTables" to check all the tables listed, main menu "Menu1" after verification;
     */
    public static void main(String[] args) throws IOException
    {
        try
        {
            String dbName = "stu";
            createTables(dbName);
            while(true)
            {
                Menu1(dbName);
            }
        }
        catch(Exception e )
        {
            System.out.println("Error in main");
            System.err.println(e);
            e.printStackTrace();
        }
    }
}