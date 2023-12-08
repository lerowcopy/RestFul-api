package Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class BackEndWindow extends MainWindow {
    private static BackEndWindow instance = new BackEndWindow();
    public static BackEndWindow getInstance(){
        return instance;
    }
    protected void setWindow(){
        connect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                api.Connect();
            }
        });
        disconnect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                api.Disconect();
            }
        });

        PUT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    api.PUT();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        GET.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                api.request();
            }
        });
    }
    protected void setConnectPosition(){
        gbc = new GridBagConstraints(
                0, 0, 1, 1, 0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(1, 1, 1, 1), 0, 0
        );
    }

    protected void setPUTPosition(){
        gbc = new GridBagConstraints(
                0, 1, 1, 1, 0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(1, 1, 1, 1), 0, 0
        );
    }

    protected void setGETPosition(){
        gbc = new GridBagConstraints(
                1, 1, 2, 1, 0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(1, 1, 1, 1), 0, 0
        );
    }

    protected void setDisconnectPosition(){
        gbc = new GridBagConstraints(
                1, 0, 1, 1, 0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(1, 1, 1, 1), 0, 0
        );
    }


}
