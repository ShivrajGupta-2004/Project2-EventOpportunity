
package login1;

import javax.swing.*;
import java.awt.*;

public class HomePage {
    private JPanel contentPanel;

    public HomePage() {
        contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());

        JLabel welcomeLabel = new JLabel("Welcome to EventOpportunity", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));

        JTextArea contentArea = new JTextArea();
        contentArea.setText(
            "What is EventOpportunity?\n" +
            "A platform designed to provide part-time job opportunities not only for college students but also for others, such as bouncers, caterers, and more.\n\n" +
            "Why EventOpportunity?\n" +
            "Events require a variety of roles, including promoters, coordinators, supervisors, workers, and other specialists. " +
            "Our platform connects these individuals directly with event organizers, eliminating inefficiencies.\n\n" +
            "How does it work?\n" +
            "Users can browse events, access detailed information, and apply for roles. Event organizers can directly connect with candidates, " +
            "streamlining the staffing process."
        );
        contentArea.setFont(new Font("Arial", Font.PLAIN, 14));
        contentArea.setEditable(false);
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(contentArea);

        contentPanel.add(welcomeLabel, BorderLayout.NORTH);
        contentPanel.add(scrollPane, BorderLayout.CENTER);
    }

    public JPanel getContentPanel() {
        return contentPanel;
    }
}
