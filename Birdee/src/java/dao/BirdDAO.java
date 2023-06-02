/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dto.Bird;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import util.DBUtils;

/**
 *
 * @author ASUS
 */
public class BirdDAO {

    public static ArrayList<Bird> searchBird(String keyword) {
        ArrayList<Bird> list = new ArrayList<>();
        try {
            Connection cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = "SELECT bird_id, bird_name,dob,gender,height,weight,origin,description,quantity,price,email_shop_staff,cate_id,email_platform_staff\n"
                        + "FROM dbo.Bird\n"
                        + "where bird_name Like ?";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setString(1, "%" + keyword + "%");
                ResultSet rs = pst.executeQuery();

                if (rs != null) {
                    while (rs.next()) {
                        int id = rs.getInt("bird_id");
                        String name = rs.getString("bird_name");
                        Date dob = rs.getDate("dob");
                        boolean gender = rs.getBoolean("gender");
                        float height = rs.getFloat("height");
                        float weight = rs.getFloat("weight");
                        String origin = rs.getString("origin");
                        int quantity = rs.getInt("quantity");
                        String description = rs.getString("description");
                        float price = rs.getFloat("price");
                        String email_shop_staff= rs.getString("email_shop_staff");
                        int cate_id =rs.getInt("cate_id");
                        String email_platform_staff = rs.getString("email_platform_staff");
                        Bird Br = new Bird(id, name, dob, gender, height, weight, origin, description, quantity, price, email_shop_staff, cate_id, email_platform_staff);

                        list.add(Br);
                    }
                    return list;
                }
                cn.close();
            } else {
                System.out.println("Connection Error");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    public static ArrayList<Bird> getBirdsList() {
        try {
            Connection cn = DBUtils.makeConnection();
            if (cn != null) {
                String s = "SELECT *"
                        + "FROM dbo.Bird";
                Statement st = cn.createStatement();
                ResultSet rs = st.executeQuery(s);
                ArrayList<Bird> list = new ArrayList<>();
                if (rs != null) {
                    while (rs.next()) {
                        int id = rs.getInt("bird_id");
                        String name = rs.getString("bird_name");
                        Date dob = rs.getDate("dob");
                        boolean gender = rs.getBoolean("gender");
                        float height = rs.getFloat("height");
                        float weight = rs.getFloat("weight");
                        String origin = rs.getString("origin");
                        int quantity = rs.getInt("quantity");
                        String description = rs.getString("description");
                        float price = rs.getFloat("price");
                        String email_shop_staff= rs.getString("email_shop_staff");
                        int cate_id =rs.getInt("cate_id");
                        String email_platform_staff = rs.getString("email_platform_staff");
                        Bird Br = new Bird(id, name, dob, gender, height, weight, origin, description, quantity, price, email_shop_staff, cate_id, email_platform_staff);

                        list.add(Br);
                    }
                    return list;
                }
                cn.close();
            } else {
                System.out.println("Connection Error");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static ArrayList<String> getBirdImg(int Bird_id){
        ArrayList<String> tmp = new ArrayList<>();

        Connection cn = null;
        try {
            cn = DBUtils.makeConnection();
            if(cn != null){

                String sql = "select url\n"
                    + "from Bird join Bird_Img on Bird.bird_id = Bird_Img.bird_id \n"
                    + "where Bird.bird_id like ?";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, Bird_id);
                ResultSet rs = pst.executeQuery();
                while(rs != null && rs.next()){
                     String url = rs.getString("url");
                     tmp.add(url);
                }

            }

        } catch (Exception e) {
            System.out.println(e);
        } 
        return tmp;
        }
    public static String getBirdAddress(int Bird_id){
        String tmp = "";

        Connection cn = null;
        try {
            cn = DBUtils.makeConnection();
            if(cn != null){

                String sql = "select address\n"
                    + "from Bird join Bird_Img on Bird.bird_id = Bird_Img.bird_id \n"
                    + "join Account on Bird.email_shop_staff = Account.email \n"
                    + "where Bird.bird_id like ?";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, Bird_id);
                ResultSet rs = pst.executeQuery();
                while(rs != null && rs.next()){
                     String url = rs.getString("address");
                     tmp= url;
                }

            }

        } catch (Exception e) {
            System.out.println(e);
        } 
        return tmp;
        }



}
