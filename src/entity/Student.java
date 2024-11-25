package entity;

import java.sql.*;
import db.MyConnection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Student {
    Connection con;
    PreparedStatement ps;

    public Student() throws ClassNotFoundException, SQLException {
        this.con = MyConnection.getConnection();
    }
    
    // get table max row
    public int getMax() {
        int id = 0;
        Statement st;
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT MAX(id) FROM student");
            while(rs.next()) {
                id = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id + 1;
    }
    
    // insert du lieu vao bang hoc sinh
    public void insert(int id, String name, String date, String gender, String email, String phone, String father, String mother, String address, String imagePath) {
        String sql = "INSERT INTO student VALUES(?,?,?,?,?,?,?,?,?,?,?)";
        int class_id =(int) (Math.random() * 10) + 1;
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setString(2, name);
            ps.setString(3, date);
            ps.setString(4, gender);
            ps.setString(5, email);
            ps.setString(6, phone);
            ps.setString(7, father);
            ps.setString(8, mother);
            ps.setString(9, address);
            ps.setString(10, imagePath);
            ps.setInt(11, class_id);
            if(ps.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Thêm học sinh thành công");
            } 
        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 
    
    // check email da ton tai 
    public boolean isEmailExist(String email) {
        try {
            ps = con.prepareStatement("SELECT * FROM student WHERE email = ?");
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if(rs.next())
                return true;
        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    // check so dien thoai da ton tai 
    public boolean isPhonelExist(String phone) {
        try {
            ps = con.prepareStatement("SELECT * FROM student WHERE phone = ?");
            ps.setString(1, phone);
            ResultSet rs = ps.executeQuery();
            if(rs.next())
                return true;
        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    // check id da ton tai 
    public boolean isIdExist(int id) {
        try {
            ps = con.prepareStatement("SELECT * FROM student WHERE id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next())
                return true;
        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    // lay thong tin hoc sinh tu db
    public void getStudentValue(JTable table, String searchValue) {
        String sql = "SELECT * FROM student WHERE concat(id,name,email,phone) like ? order by id ASC";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, "%" + searchValue + "%");
            ResultSet rs = ps.executeQuery();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            Object[] row;
            while(rs.next()) {
                row = new Object[10];
                row[0] = rs.getInt(1);
                row[1] = rs.getString(2);
                row[2] = rs.getString(3);
                row[3] = rs.getString(4);
                row[4] = rs.getString(5);
                row[5] = rs.getString(6);
                row[6] = rs.getString(7);
                row[7] = rs.getString(8);
                row[8] = rs.getString(9);
                row[9] = rs.getString(10);
                model.addRow(row);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // update thong tin hoc sinh
    public void update(int id, String name, String date, String gender, String email, String phone, String father, String mother, String address, String imagePath) {
        String sql = "UPDATE student set name = ?, date_of_birth = ?, gender = ?, email = ?, phone = ?, father_name = ?, mother_name = ?, address = ?, image_path = ? WHERE id = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, date);
            ps.setString(3, gender);
            ps.setString(4, email);
            ps.setString(5, phone);
            ps.setString(6, father);
            ps.setString(7, mother);
            ps.setString(8, address);
            ps.setString(9, imagePath);
            ps.setInt(10, id);
            
            if(ps.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Cập nhật thành công");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // xoa thong tin hoc sinh
    public void delete(int id) {
        int yesOrNo = JOptionPane.showConfirmDialog(null, "Môn học và điểm cũng sẽ bị xóa", "Xóa bản ghi", JOptionPane.OK_OPTION, 0);
        if(yesOrNo == JOptionPane.OK_OPTION) {
            try {
                ps = con.prepareStatement("DELETE FROM student WHERE id = ?");
                ps.setInt(1, id);
                if(ps.executeUpdate() > 0) {
                    JOptionPane.showMessageDialog(null, "Xóa bản ghi thành công");
                }
            } catch (SQLException ex) {
                Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
