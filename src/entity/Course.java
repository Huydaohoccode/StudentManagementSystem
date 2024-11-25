package entity;

import GUI.Home;
import db.MyConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Course {
    Connection con;
    PreparedStatement ps;
    
    public Course() throws ClassNotFoundException, SQLException {
        this.con = MyConnection.getConnection();
    }
    
    // get table max row
    public int getMax() {
        int id = 0;
        Statement st;
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT MAX(id) FROM course");
            while(rs.next()) {
                id = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Course.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id + 1;
    }
        
    public boolean getId(int id) {
        try {
            ps = con.prepareStatement("SELECT * FROM student WHERE id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                Home.jTextField10.setText(String.valueOf(rs.getInt(1)));
                return true;
            }
            else {
                JOptionPane.showMessageDialog(null, "Mã học sinh không tồn tại");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Course.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    // dem so hoc ky
    public int countSemester(int id) {
        int total = 0;
        try {
            ps = con.prepareStatement("SELECT COUNT(*) AS 'total' FROM course WHERE student_id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                total = rs.getInt(1);
            }
            if(total == 8) {
                JOptionPane.showMessageDialog(null, "Học sinh đã hoàn thành tất cả môn học");
                return -1;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Course.class.getName()).log(Level.SEVERE, null, ex);
        }
        return total;
    }
    
    // check hoc sinh da hoc hoc ky nay hay chua
    public boolean isSemesterExist(int sid, int semesterNo) {
        try {
            ps = con.prepareStatement("SELECT * FROM course WHERE student_id = ? AND semester = ?");
            ps.setInt(1, sid);
            ps.setInt(2, semesterNo);
            ResultSet rs = ps.executeQuery();
            if(rs.next())
                return true;
        } catch (SQLException ex) {
            Logger.getLogger(Course.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    // check hoc sinh da hoc mon hoc nay hay chua
    public boolean isCourseExist(int sid, String courseNo, String course) {
        String sql = "SELECT * FROM course WHERE student_id = ? AND " + courseNo + " = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, sid);
            ps.setString(2, course);
            ResultSet rs = ps.executeQuery();
            if(rs.next())
                return true;
        } catch (SQLException ex) {
            Logger.getLogger(Course.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    // insert du lieu vao bang course
    public void insert(int id, int sid, int semester, String course1, String course2, String course3, String course4, String course5) {
        String sql = "INSERT INTO course VALUES(?,?,?,?,?,?,?,?)";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setInt(2, sid);
            ps.setInt(3, semester);
            ps.setString(4, course1);
            ps.setString(5, course2);
            ps.setString(6, course3);
            ps.setString(7, course4);
            ps.setString(8, course5);
            if(ps.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Thêm môn học thành công");
            } 
        } catch (SQLException ex) {
            Logger.getLogger(Course.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // lay thong tin course tu db
    public void getCourseValue(JTable table, String searchValue) {
        String sql = "SELECT * FROM course WHERE concat(id,student_id,semester) like ? order by id ASC";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, "%" + searchValue + "%");
            ResultSet rs = ps.executeQuery();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            Object[] row;
            while(rs.next()) {
                row = new Object[8];
                row[0] = rs.getInt(1);
                row[1] = rs.getString(2);
                row[2] = rs.getString(3);
                row[3] = rs.getString(4);
                row[4] = rs.getString(5);
                row[5] = rs.getString(6);
                row[6] = rs.getString(7);
                row[7] = rs.getString(8);
                model.addRow(row);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Course.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
