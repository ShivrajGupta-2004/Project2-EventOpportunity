package login1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Home extends JFrame implements ActionListener {
    JMenuBar mb;
    JMenu homeMenu, eventsMenu, updateMenu, addEventsMenu, userInfoMenu;
    JMenuItem homePageItem, addEventItem, updateEventItem, informationItem, eventsItem;
    JPanel contentPanel;

    public Home() {
        mb = new JMenuBar();

        homeMenu = new JMenu("Home");
        eventsMenu = new JMenu("Events");
        updateMenu = new JMenu("Update");
        addEventsMenu = new JMenu("Add Events");
        userInfoMenu = new JMenu("User Information");

        homePageItem = new JMenuItem("Home Page");
        addEventItem = new JMenuItem("Add Event");
        updateEventItem = new JMenuItem("Update Event");
        informationItem = new JMenuItem("Information");
        eventsItem = new JMenuItem("Events");

        homeMenu.add(homePageItem);
        addEventsMenu.add(addEventItem);
        updateMenu.add(updateEventItem);
        userInfoMenu.add(informationItem);
        eventsMenu.add(eventsItem);

        mb.add(homeMenu);
        mb.add(eventsMenu);
        mb.add(updateMenu);
        mb.add(addEventsMenu);
        mb.add(userInfoMenu);

        homePageItem.addActionListener(this);
        addEventItem.addActionListener(this);
        updateEventItem.addActionListener(this);
        informationItem.addActionListener(this);
        eventsItem.addActionListener(this);

        setJMenuBar(mb);

        contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());

        setLayout(new BorderLayout());
        add(contentPanel, BorderLayout.CENTER);

        setSize(800, 600);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        contentPanel.removeAll();
        if (e.getSource() == homePageItem) {
            contentPanel.add(new HomePage().getContentPanel(), BorderLayout.CENTER);
        } else if (e.getSource() == addEventItem) {
            contentPanel.add(new AddEvent().getContentPanel(), BorderLayout.CENTER);
        } else if (e.getSource() == updateEventItem) {
            contentPanel.add(new Update().getContentPanel(), BorderLayout.CENTER);
        } else if (e.getSource() == informationItem) {
            contentPanel.add(new Information().getContentPanel(), BorderLayout.CENTER);
        } else if (e.getSource() == eventsItem) {
            contentPanel.add(new Events().getContentPanel(), BorderLayout.CENTER);
        }
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    public static void main(String[] args) {
        new Home();
    }
}
