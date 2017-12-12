package test;

import java.sql.*;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import test.Test.Student;

public class Test {
	static class Student {
        private int Id;
        private String Name;
        private String Sex;
        private int Age;

        Student(int Id, String Name, String Sex, int Age) {
            this.Id = Id; 
            this.Name = Name;
            this.Sex = Sex;
            this.Age = Age;
        }

        public int getId() {
            return Id;
        }

        public void setId(int Id) {
            this.Id = Id;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public String getSex() {
            return Sex;
        }

        public void setSex(String Sex) {
            this.Sex = Sex;
        }

        public int getAge() {
            return Age;
        }

        public void setage(int Age) {
            this.Age = Age;
        }

	}
	
	private static Connection getConn() {  //ms1
	    String driver = "com.mysql.jdbc.Driver";
	    String url = "jdbc:mysql://10.11.1.195:15520";
	    String username = "admin";
	    String password = "admin";
	    Connection conn = null;
	    try {
	        Class.forName(driver); //classLoader,加载对应驱动
	        conn = (Connection) DriverManager.getConnection(url, username, password);
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return conn;
	}
	
	private static Connection getConn2() {  //ms2
	    String driver = "com.mysql.jdbc.Driver";
	    String url = "jdbc:mysql://10.11.1.195:15522";
	    String username = "admin";
	    String password = "admin";
	    Connection conn = null;
	    try {
	        Class.forName(driver); //classLoader,加载对应驱动
	        conn = (Connection) DriverManager.getConnection(url, username, password);
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return conn;
	}
	
	private static int insert(Student student) {
	    Connection conn = getConn();
	    int i = 0;
	    String sql = "insert into students (id,Name,Sex,Age) values(?,?,?,?)";
	    PreparedStatement pstmt;
	    try {
	        pstmt = (PreparedStatement) conn.prepareStatement(sql);
	        pstmt.setInt(1, student.getId());
	        pstmt.setString(2, student.getName());
	        pstmt.setString(3, student.getSex());
	        pstmt.setInt(4, student.getAge());
	        i = pstmt.executeUpdate();
	        pstmt.close();
	        conn.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return i;
	}
	
	
	public static int update(Student student) {
	    Connection conn = getConn();
	    int i = 0;
	    String sql = "update students set Age=" + student.getAge() + " where Name='" + student.getName() + "'";
	    PreparedStatement pstmt;
	    try {
	        pstmt = (PreparedStatement) conn.prepareStatement(sql);
	        i = pstmt.executeUpdate();
	        System.out.println("resutl: " + i);
	        pstmt.close();
	        conn.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return i;
	}
	
	
	public static Integer getAll() {  //ms2
	    Connection conn = getConn2();
	    String sql = "select * from students";
	    PreparedStatement pstmt;
	    try {
	        pstmt = (PreparedStatement)conn.prepareStatement(sql);
	        ResultSet rs = pstmt.executeQuery();
	        int col = rs.getMetaData().getColumnCount();
	        System.out.println("===================================");
	        while (rs.next()) {
	            for (int i = 1; i <= col; i++) {
	                System.out.print(rs.getString(i) + "\t");
	                if ((i == 2) && (rs.getString(i).length() < 8)) {
	                    System.out.print("\t");
	                }
	             }
	            System.out.println("");
	        }
	            System.out.println("===================================");
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return null;
	}
	
	
	public static Integer getAll2() {  //ms2
	    Connection conn2 = getConn();
	    String sql = "select * from students";
	    PreparedStatement pstmt;
	    try {
	        pstmt = (PreparedStatement)conn2.prepareStatement(sql);
	        ResultSet rs = pstmt.executeQuery();
	        int col = rs.getMetaData().getColumnCount();
	        System.out.println("===================================");
	        while (rs.next()) {
	            for (int i = 1; i <= col; i++) {
	                System.out.print(rs.getString(i) + "\t");
	                if ((i == 2) && (rs.getString(i).length() < 8)) {
	                    System.out.print("\t");
	                }
	             }
	            System.out.println("");
	        }
	            System.out.println("===================================");
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return null;
	}
	
	private static int delete(String name) {
	    Connection conn = getConn();
	    int i = 0;
	    String sql = "delete from students where Name='" + name + "'";
	    PreparedStatement pstmt;
	    try {
	        pstmt = (PreparedStatement) conn.prepareStatement(sql);
	        i = pstmt.executeUpdate();
	        System.out.println("resutl: " + i);
	        pstmt.close();
	        conn.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return i;
	}
	
	
	
	public static void main(String args[]) throws Exception {
		RunnableDemo R1 = new RunnableDemo("Thread1");
		R1.start();
		RunnableDemo2 R2 = new RunnableDemo2("Thread2");
		R2.start();
	    Test.getAll();
	    Test.insert(new Student(7,"Achilles", "Male", 14));
	    Test.insert(new Student(8,"Achilles1", "Male", 14));
	    Test.getAll();
	    Test.update(new Student(2,"lisi", "male", 8));

	    Test.delete("Achilles");
	    Test.delete("Achilles1");
	    Test.getAll();
	    String sql;
	    Connection conn = getConn();
	   
			Statement stmt = conn.createStatement();
		
	    sql = "select * from students";
	    ResultSet rs = stmt.executeQuery(sql);
	    System.out.println("ID\t姓名\t性别\t年龄");
	    while (rs.next()) {
	    	System.out.println(rs.getInt(1)+"\t"+rs.getString(2)+"\t"+rs.getString(3)+"\t"+rs.getInt(4));
	    }
	    sql = "update students set age=10 where id=2";
	    int rs1 = stmt.executeUpdate(sql);
	    sql = "select * from students";
	     rs = stmt.executeQuery(sql);
	    System.out.println("ID\t姓名\t性别\t年龄");
	    while (rs.next()) {
	    	System.out.println(rs.getInt(1)+"\t"+rs.getString(2)+"\t"+rs.getString(3)+"\t"+rs.getInt(4));
	    }
	    
	}
	
}


class RunnableDemo implements Runnable {
	   private Thread t;
	   private String threadName;
	   
	   RunnableDemo( String name) {
	      threadName = name;
	      System.out.println("Creating " +  threadName );
	   }
	   
	   public void run() {
	      System.out.println("Running " +  threadName );
	      try {
	    	  Test.update(new Student(2,"lisi", "male", 8));
	         for(int i = 4; i > 0; i--) {
	            System.out.println("Thread: " + threadName + ", " + i);
	            // 让线程睡眠一会
	            Thread.sleep(50);
	         }
	      }catch (InterruptedException e) {
	         System.out.println("Thread " +  threadName + " interrupted.");
	      }
	      System.out.println("Thread " +  threadName + " exiting.");
	   }
	   
	   public void start () {
	      System.out.println("Starting " +  threadName );
	      if (t == null) {
	         t = new Thread (this, threadName);
	         t.start ();
	      }
	   }
} 


class RunnableDemo2 implements Runnable {
	   private Thread t;
	   private String threadName;
	   
	   RunnableDemo2( String name) {
	      threadName = name;
	      System.out.println("Creating " +  threadName );
	   }
	   
	   public void run() {
	      System.out.println("Running " +  threadName );
	      try {
	    	  Test.getAll2();
	         for(int i = 4; i > 0; i--) {
	            System.out.println("Thread: " + threadName + ", " + i);
	            // 让线程睡眠一会
	            Thread.sleep(50);
	         }
	      }catch (InterruptedException e) {
	         System.out.println("Thread " +  threadName + " interrupted.");
	      }
	      System.out.println("Thread " +  threadName + " exiting.");
	   }
	   
	   public void start () {
	      System.out.println("Starting " +  threadName );
	      if (t == null) {
	         t = new Thread (this, threadName);
	         t.start ();
	      }
	   }
} 
	
	
	

	
	
	