import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class JavaCrud {
    private JPanel MainPanel;
    private JTextField nametxt;
    private JTextField pricetxt;
    private JTextField qtytxt;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton saveButton;
    private JTextField textid;
    private JButton searchButton;

    public static void main(String[] args) {
        JFrame frame = new JFrame("JavaCrud");
        frame.setContentPane(new JavaCrud().MainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    Connection con;
    PreparedStatement pst;

    public JavaCrud() {
        connect();

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                String name, price, qty;

                name = nametxt.getText();
                price = pricetxt.getText();
                qty = qtytxt.getText();

                try {
                    pst = con.prepareStatement("insert into products (pname, price, qty)values(?,?,?)");
                    pst.setString(1, name);
                    pst.setString(2, price);
                    pst.setString(3, qty);
                    pst.executeUpdate();

                    JOptionPane.showMessageDialog(null, "Record Added.");

                    nametxt.setText("");
                    pricetxt.setText("");
                    qtytxt.setText("");
                    nametxt.requestFocus();
                }

                catch (SQLException e1) {
                    e1.printStackTrace();
                }


            }
        });
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                String pid = textid.getText();
                try {
                    pst = con.prepareStatement("select pname, price, qty from products where pid = ?");
                    pst.setString(1, pid);
                    ResultSet rs = pst.executeQuery();

                    if (rs.next()==true) {
                        String name = rs.getString(1);
                        String price = rs.getString(2);
                        String qty = rs.getString(3);

                        nametxt.setText(name);
                        pricetxt.setText(price);
                        qtytxt.setText(qty);
                    }
                    else {
                        nametxt.setText("");
                        pricetxt.setText("");
                        qtytxt.setText("");

                        JOptionPane.showMessageDialog(null, "Invalid id.");


                    }

                }
                catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                String name, price, qty, pid;

                name = nametxt.getText();
                price = pricetxt.getText();
                qty = qtytxt.getText();
                pid = textid.getText();

                try {
                    pst = con.prepareStatement("update products set pname = ?, price = ?, qty = ? where pid = ?");
                    pst.setString(1, name);
                    pst.setString(2, price);
                    pst.setString(3, qty);
                    pst.setString(4, pid);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record Updated.");

                    nametxt.setText("");
                    pricetxt.setText("");
                    qtytxt.setText("");
                    textid.setText("");
                    nametxt.requestFocus();


                }

                catch (SQLException e1) {
                    e1.printStackTrace();
                }

            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String bid;
                bid = textid.getText();

                try {
                    pst = con.prepareStatement("delete from products where pid = ?");
                    pst.setString(1, bid);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record Delete.");

                    nametxt.setText("");
                    pricetxt.setText("");
                    qtytxt.setText("");
                    textid.setText("");
                    nametxt.requestFocus();


                }
                catch (SQLException e1) {
                    e1.printStackTrace();
                }

            }
        });
    }


    public void connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/gbproducts", "root","root");
            System.out.println("Success");
        }
        catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
    }






}
