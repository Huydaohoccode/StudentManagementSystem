package entity;

import db.MyConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Class {
    Connection con;
    PreparedStatement ps;

    public Class() throws ClassNotFoundException, SQLException {
        this.con = MyConnection.getConnection();
    }
    
    // check id da ton tai 
    public boolean isIdExist(int cid) {
        try {
            ps = con.prepareStatement("SELECT * FROM class WHERE id = ?");
            ps.setInt(1, cid);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Class.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    // lay thong tin score tu db
    public void getClassValue(JTable table, int cid) {
        String sql = "SELECT student.id, student.name, student.gender, student.email, student.phone ,student.address FROM student JOIN class ON student.class_id = class.id WHERE class.id = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, cid);
            ResultSet rs = ps.executeQuery();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            Object[] row;
            while (rs.next()) {
                row = new Object[6];
                row[0] = rs.getInt(1);
                row[1] = rs.getString(2);
                row[2] = rs.getString(3);
                row[3] = rs.getString(4);
                row[4] = rs.getString(5);
                row[5] = rs.getString(6);
                model.addRow(row);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Class.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // lay ten lop
    public String getClassName(int cid) {
        String sql = "SELECT name FROM class WHERE id = ?";
        String res = null;
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, cid);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                res = rs.getString(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Class.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }
    
    // lay ten giao vien phu trach
    public String getTeacherName(int cid) {
        String sql = "SELECT teacher.name FROM teacher JOIN class ON class.teacher_id = teacher.id WHERE class.id = ?";
        String res = null;
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, cid);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                res = rs.getString(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Class.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }
    
    // lay si so tren 1 lop
    public int getTotalStudent(int cid) {
        String sql = "SELECT COUNT(*) FROM student WHERE class_id = ?";
        int res = 0;
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, cid);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                res = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Class.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }
}
