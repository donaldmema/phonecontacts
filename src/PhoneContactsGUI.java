import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PhoneContactsGUI extends JFrame {
    private DoublyLinkedList contacts;
    private DefaultListModel<Contact> listModel;
    private JList<Contact> contactList;
    private JTextField nameField;
    private JTextField contactDeleteField;
    private JTextField phoneField;
    private JTextField searchField;

    public PhoneContactsGUI() {
        contacts = new DoublyLinkedList();
        listModel = new DefaultListModel<>();
        contactList = new JList<>(listModel);
        nameField = new JTextField(20);
        phoneField = new JTextField(20);
        contactDeleteField = new JTextField(20);
        searchField = new JTextField(20);

        setTitle("Phone Contacts");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridBagLayout());
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.ipady = 10;
        constraints.insets = new Insets(0, 0, 5, 0);
        constraints.anchor = GridBagConstraints.WEST;
        inputPanel.add(new JLabel("Name: "), constraints);
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        inputPanel.add(nameField, constraints);
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        inputPanel.add(new JLabel("Phone Number: "), constraints);
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        inputPanel.add(phoneField, constraints);

        JButton addButton = new JButton("Add Contact");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String phoneNumber = phoneField.getText();
                if (!name.isEmpty() && !phoneNumber.isEmpty()) {
                    Contact contact = new Contact(name, phoneNumber);
                    contacts.add(contact);
                    listModel.addElement(contact);
                    nameField.setText("");
                    phoneField.setText("");
                }
            }
        });
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        inputPanel.add(addButton, constraints);

        JPanel deletePanel = new JPanel();
        deletePanel.setLayout(new GridBagLayout());
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        deletePanel.add(new JLabel("Name: "), constraints);
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        deletePanel.add(contactDeleteField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        deletePanel.add(new JLabel(" "), constraints);

        JButton deleteButton = new JButton("Delete Contact");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = contactDeleteField.getText();
                contacts.delete(name);
                updateContactList();
            }
        });
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        deletePanel.add(deleteButton, constraints);

        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new GridBagLayout());
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.anchor = GridBagConstraints.WEST;
        searchPanel.add(new JLabel("Search: "), constraints);
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        searchPanel.add(searchField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        searchPanel.add(new JLabel("Contacts List: "), constraints);
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        JScrollPane scrollPane = new JScrollPane(contactList);
        scrollPane.setPreferredSize(new Dimension(490, 200));
        searchPanel.add(scrollPane, constraints);

        searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                updateSearchResults();
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                updateSearchResults();
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                updateSearchResults();
            }
        });

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        constraints.ipady = 30;
        add(searchPanel, constraints);
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        constraints.ipadx = 20;
        add(inputPanel, constraints);
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        add(deletePanel, constraints);
    }

    private void updateContactList() {
        listModel.clear();
        for (Contact contact : contacts.getAllContacts()) {
            listModel.addElement(contact);
        }
    }

    private void updateSearchResults() {
        String prefix = searchField.getText();
        listModel.clear();
        for (Contact contact : contacts.search(prefix)) {
            listModel.addElement(contact);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new PhoneContactsGUI().setVisible(true);
            }
        });
    }
}
