package Client;

import API.MySql.MySqlConnection;
import API.Operation.ApiOperation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public abstract class MainWindow extends JFrame{
    static MySqlConnection api = new MySqlConnection();
    protected static JButton connect = new JButton("Connect");
    protected static JButton disconnect = new JButton("Disconnect");
    protected static JButton PUT = new JButton("PUT");
    protected static JButton GET = new JButton("GET");
    protected static JPanel panel;
    protected static BackEndWindow backEndWindow = new BackEndWindow();
    protected static GridBagConstraints gbc;

    static class Window extends JFrame {

        public Window () throws SQLException {

            panel = new JPanel(new GridBagLayout());
            connect = new JButton("Connect");
            disconnect = new JButton("Disconnect");
            setSize(500, 500);
            setTitle("Client");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            //setLayout(new GridBagLayout());

            BackEndWindow.getInstance().setWindow();

            BackEndWindow.getInstance().setConnectPosition();
            panel.add(connect,gbc);

            BackEndWindow.getInstance().setDisconnectPosition();
            panel.add(disconnect, gbc);

            BackEndWindow.getInstance().setPUTPosition();
            panel.add(PUT, gbc);

            BackEndWindow.getInstance().setGETPosition();
            panel.add(GET, gbc);

            getContentPane().add(panel);
            System.out.print("test");

        }

        public static void main(String[] args) throws SQLException {
            Window wnd = new Window();
            wnd.setVisible(true);
        }
    }
}

