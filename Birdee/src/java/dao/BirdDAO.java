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

    public static ArrayList<Bird> getBird(String keyword) {
        ArrayList<Bird> list = new ArrayList<>();
        Connection cn = null;
        try {
            cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = "select *\n"
                        + "from Bird join Bird_Img on Bird.bird_id = Bird_Img.bird_id \n" +
                        "join Account on Bird.email_shop_staff = Account.email \n";

                sql = sql + "where Bird.bird_name like ?";
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
                        String description = rs.getString("description");
                        int quantity = rs.getInt("quantity");
                        float price = rs.getFloat("price");
                        String email_shop_staff = rs.getString("email_shop_staff");
                        int cate_id = rs.getInt("cate_id");
                        String email_platform_staff = rs.getString("email_platform_staff");
                        String url = rs.getString("url");
                        String address = rs.getString("address");
                        Bird bird = new Bird(id, name, dob, gender, height, weight, origin, description, quantity,
                                price, email_shop_staff, cate_id, email_platform_staff, url, address);
                        list.add(bird);

                    }
                }
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    public static float getBirdVote(int Bird_id) {
        float tmp = 0;
        ArrayList<Integer> list = new ArrayList();
        Connection cn = null;
        try {
            cn = DBUtils.makeConnection();
            if (cn != null) {

                String sql = "select Bird.bird_id, rating\n"
                        + "from Bird join Bird_Img on Bird.bird_id = Bird_Img.bird_id \n" +
                        "join Account on Bird.email_shop_staff = Account.email \n" +
                        "join Order_Detail_Bird on Order_Detail_Bird.bird_id = Bird.bird_id\n" +
                        "join Review_Bird on Review_Bird.order_detail_id_B = Order_Detail_Bird.order_detail_id_B\n" +
                        "where Bird.bird_id like ?";

                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, Bird_id);
                ResultSet rs = pst.executeQuery();
                while (rs != null && rs.next()) {
                    int id = rs.getInt("rating");
                    list.add(id);
                    tmp += id;
                }

            }

        } catch (Exception e) {
            System.out.println(e);
        }
        if (list.size() != 0) {
            return tmp / list.size();
        }
        return 0;
    }

    public static int getBirdBuying(int Bird_id) {
        int tmp = 0;

        Connection cn = null;
        try {
            cn = DBUtils.makeConnection();
            if (cn != null) {

                String sql = "select Bird.bird_id, order_detail_id_B\n" +
                        "from Bird join Bird_Img on Bird.bird_id = Bird_Img.bird_id \n" +
                        "join Account on Bird.email_shop_staff = Account.email \n" +
                        "join Order_Detail_Bird on Order_Detail_Bird.bird_id = Bird.bird_id\n" +
                        "where Bird.bird_id like ?";

                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, Bird_id);
                ResultSet rs = pst.executeQuery();
                while (rs != null && rs.next()) {
                    tmp += 1;
                }

            }

        } catch (Exception e) {
            System.out.println(e);
        }

        return tmp;
    }

    public static ArrayList<Bird> searchBird(String keyword) {
        ArrayList<Bird> list = new ArrayList<>();
        try {
            Connection cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = "SELECT B.bird_name,BI.url, B.dob, B.gender, B.height, B.weight, B.origin,B.quantity, B.description, B.price\n"
                        + "FROM dbo.Bird AS B \n"
                        + "JOIN dbo.Bird_Img AS BI \n"
                        + "ON B.bird_id = BI.bird_id\n"
                        + "WHERE bird_name LIKE + ?";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setString(1, "%" + keyword + "%");
                ResultSet rs = pst.executeQuery();

                if (rs != null) {
                    while (rs.next()) {
                        String name = rs.getString("bird_name");
                        Date dob = rs.getDate("dob");
                        String url = rs.getString("url");
                        boolean gender = rs.getBoolean("gender");
                        float height = rs.getFloat("height");
                        float weight = rs.getFloat("weight");
                        String origin = rs.getString("origin");
                        int quantity = rs.getInt("quantity");
                        String description = rs.getString("description");
                        float price = rs.getFloat("price");
                        Bird Br = new Bird(name, url, dob, gender, height, weight, origin, quantity, description, price);

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
                String s = "SELECT B.bird_name, BI.url, B.dob, B.gender, B.height, B.weight, B.origin,B.quantity, B.description, B.price\n"
                        + "FROM dbo.Bird AS B \n"
                        + "JOIN dbo.Bird_Img AS BI \n"
                        + "ON B.bird_id = BI.bird_id";
                Statement st = cn.createStatement();
                ResultSet rs = st.executeQuery(s);
                ArrayList<Bird> list = new ArrayList<>();
                if (rs != null) {
                    while (rs.next()) {
                        String name = rs.getString("bird_name");
                        Date dob = rs.getDate("dob");
                        String url = rs.getString("url");
                        boolean gender = rs.getBoolean("gender");
                        float height = rs.getFloat("height");
                        float weight = rs.getFloat("weight");
                        String origin = rs.getString("origin");
                        int quantity = rs.getInt("quantity");
                        String description = rs.getString("description");
                        float price = rs.getFloat("price");
                        Bird Br = new Bird(name, url, dob, gender, height, weight, origin, quantity, description, price);
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

}
