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

public class Score {
    Connection con;
    PreparedStatement ps;
    
    public Score() throws ClassNotFoundException, SQLException {
        this.con = MyConnection.getConnection();
    }
    
    // get table max row
    public int getMax() {
        int id = 0;
        Statement st;
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT MAX(id) FROM score");
            while(rs.next()) {
                id = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Score.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id + 1;
    }
    
    public boolean getDetails(int sid, int semesterNo) {
        try {
            ps = con.prepareStatement("SELECT * FROM course WHERE student_id = ? AND semester = ?");
            ps.setInt(1, sid);
            ps.setInt(2, semesterNo);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                //Home.jTextField11.setText(String.valueOf(rs.getInt(1)));
                Home.jTextField13.setText(String.valueOf(rs.getInt(2)));
                Home.jTextField15.setText(String.valueOf(rs.getInt(3)));
                Home.jTextField16.setText(rs.getString(4));
                Home.jTextField17.setText(rs.getString(5));
                Home.jTextField18.setText(rs.getString(6));
                Home.jTextField19.setText(rs.getString(7));
                Home.jTextField20.setText(rs.getString(8));
                return true;
            }
            else {
                JOptionPane.showMessageDialog(null, "Mã học sinh hoặc học kỳ không tồn tại");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Score.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    // check id da ton tai
    public boolean isIdExist(int id) {
        try {
            ps = con.prepareStatement("SELECT * FROM score WHERE id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next())
                return true;
        } catch (SQLException ex) {
            Logger.getLogger(Score.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    // check ma hoc sinh hoac hoc ky da ton tai
    public boolean isSidOrSemesterNoExist(int sid, int semesterNo) {
        try {
            ps = con.prepareStatement("SELECT * FROM score WHERE student_id = ? AND semester = ?");
            ps.setInt(1, sid);
            ps.setInt(2, semesterNo);
            ResultSet rs = ps.executeQuery();
            if(rs.next())
                return true;
        } catch (SQLException ex) {
            Logger.getLogger(Score.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    // insert du lieu vao bang score
    public void insert(int id, int sid, int semester, String course1, String course2, String course3, String course4, String course5, double score1, double score2, double score3, double score4, double score5, double average) {
        String sql = "INSERT INTO score VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setInt(2, sid);
            ps.setInt(3, semester);
            ps.setString(4, course1);
            ps.setDouble(5, score1);
            ps.setString(6, course2);
            ps.setDouble(7, score2);
            ps.setString(8, course3);
            ps.setDouble(9, score3);
            ps.setString(10, course4);
            ps.setDouble(11, score4);
            ps.setString(12, course5);
            ps.setDouble(13, score5);
            ps.setDouble(14, average);
            if(ps.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Thêm điểm môn học thành công");
            } 
        } catch (SQLException ex) {
            Logger.getLogger(Score.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // lay thong tin score tu db
    public void getScoreValue(JTable table, String searchValue) {
        String sql = "SELECT * FROM score WHERE concat(id,student_id,semester) like ? order by id ASC";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, "%" + searchValue + "%");
            ResultSet rs = ps.executeQuery();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            Object[] row;
            while(rs.next()) {
                row = new Object[14];
                row[0] = rs.getInt(1);
                row[1] = rs.getInt(2);
                row[2] = rs.getInt(3);
                row[3] = rs.getString(4);
                row[4] = rs.getDouble(5);
                row[5] = rs.getString(6);
                row[6] = rs.getDouble(7);
                row[7] = rs.getString(8);
                row[8] = rs.getDouble(9);
                row[9] = rs.getString(10);
                row[10] = rs.getDouble(11);
                row[11] = rs.getString(12);
                row[12] = rs.getDouble(13);
                row[13] = rs.getDouble(14);
                model.addRow(row);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Score.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // update thong tin diem
    public void update(int id, double score1, double score2, double score3, double score4, double score5, double average) {
        String sql = "UPDATE score set score1 = ?, score2 = ?, score3 = ?, score4 = ?, score5 = ?, average = ? WHERE id = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setDouble(1, score1);
            ps.setDouble(2, score2);
            ps.setDouble(3, score3);
            ps.setDouble(4, score4);
            ps.setDouble(5, score5);
            ps.setDouble(6, average);
            ps.setInt(7, id);
            if(ps.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Cập nhật thành công");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Score.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
