package View;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.File;
import java.util.List;

import Model.Employee;
import Model.Employee.Role;
import Model.Employee.Poste;

public class EmployeeView extends JFrame {
    private JPanel jp1 = new JPanel(), jp2 = new JPanel(), jp3 = new JPanel(), jp4 = new JPanel();
    private JLabel jlNom = new JLabel("Nom : "), jlPrenom = new JLabel("Prenom : "), jlEmail = new JLabel("Email"), 
                   jlTelephone = new JLabel("Téléphone : "), jlSalaire = new JLabel("Salaire : "), jlRole = new JLabel("Role : "), 
                   jlPoste = new JLabel("Poste : ");
    public JTextField jtfNom = new JTextField(), jtfPrenom = new JTextField(), jtfEmail = new JTextField(), 
                      jtfTelephone = new JTextField(), jtfSalaire = new JTextField();
    private JComboBox<Role> comboboxRole = new JComboBox<>(Role.values());
    private JComboBox<Poste> comboboxPoste = new JComboBox<>(Poste.values());
    private DefaultTableModel tableModel = new DefaultTableModel(new Object[][] {}, new String[] { "Id", "Nom", "Prenom", "Email", "Telephone", "Salaire" });
    private JTable jt = new JTable(tableModel);
    public JButton ajouterButton = new JButton("Ajouter"), modifierButton = new JButton("Modifier"), 
                   supprimerButton = new JButton("Supprimer"), afficherButton = new JButton("Afficher"),
                   importButton = new JButton("Importer"), exportButton = new JButton("Exporter");

    public EmployeeView() {
        setTitle("Gestion des employés");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        add(jp1);
        jp1.setLayout(new BorderLayout());
        jp1.add(jp2, BorderLayout.NORTH);
        jp2.setLayout(new GridLayout(7, 2));
        jp2.add(jlNom);
        jp2.add(jtfNom);
        jp2.add(jlPrenom);
        jp2.add(jtfPrenom);
        jp2.add(jlEmail);
        jp2.add(jtfEmail);
        jp2.add(jlTelephone);
        jp2.add(jtfTelephone);
        jp2.add(jlSalaire);
        jp2.add(jtfSalaire);
        jp2.add(jlRole);
        jp2.add(comboboxRole);
        jp2.add(jlPoste);
        jp2.add(comboboxPoste);

        jp1.add(jp3, BorderLayout.CENTER);
        jp3.setLayout(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane(jt);
        jp3.add(scrollPane, BorderLayout.CENTER);

        jp1.add(jp4, BorderLayout.SOUTH);
        jp4.setLayout(new FlowLayout());
        jp4.add(ajouterButton);
        jp4.add(modifierButton);
        jp4.add(supprimerButton);
        jp4.add(afficherButton);
        jp4.add(importButton); // New Import button
        jp4.add(exportButton); // New Export button

        jt.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int selectedRow = jt.getSelectedRow();
                if (selectedRow != -1) {
                    jtfNom.setText(tableModel.getValueAt(selectedRow, 1).toString());
                    jtfPrenom.setText(tableModel.getValueAt(selectedRow, 2).toString());
                    jtfEmail.setText(tableModel.getValueAt(selectedRow, 3).toString());
                    jtfTelephone.setText(tableModel.getValueAt(selectedRow, 4).toString());
                    jtfSalaire.setText(tableModel.getValueAt(selectedRow, 5).toString());
                }
            }
        });

        // Add ActionListener for Import button
        importButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                System.out.println("Selected file for import: " + selectedFile.getAbsolutePath());
                // Trigger import logic here (to be connected to controller)
            }
        });
        
        

        // Add ActionListener for Export button
        exportButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showSaveDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                System.out.println("Selected file for export: " + selectedFile.getAbsolutePath());
                // Trigger export logic here (to be connected to controller)
            }
        });

        // setVisible(true); Uncomment this when testing the UI
    }

    public int getId() {
        int selectedRow = jt.getSelectedRow();
        if (selectedRow != -1) {
            Object value = tableModel.getValueAt(selectedRow, 0);
            return Integer.parseInt(value.toString());
        }
        return -1;
    }

    public String getNom() { return jtfNom.getText(); }
    public String getPrenom() { return jtfPrenom.getText(); }
    public String getEmail() { return jtfEmail.getText(); }
    public String getTelephone() { return jtfTelephone.getText(); }
    public double getSalaire() { return Double.parseDouble(jtfSalaire.getText()); }
    public Role getRole() { return (Role) comboboxRole.getSelectedItem(); }
    public Poste getPoste() { return (Poste) comboboxPoste.getSelectedItem(); }

    public void afficherEmployees(List<Employee> employees) {
        tableModel.setRowCount(0);
        for (Employee elm : employees) {
            tableModel.addRow(new Object[] {
                elm.getId(),
                elm.getNom(),
                elm.getPrenom(),
                elm.getEmail(),
                elm.getTelephone(),
                elm.getSalaire()
            });
        }
    }

    public void afficherMessageErreur(String message) {
        JOptionPane.showMessageDialog(this, message, "Erreur", JOptionPane.ERROR_MESSAGE);
    }

    public void afficherMessageSucces(String message) {
        JOptionPane.showMessageDialog(this, message, "Succes", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public String showFileChooser(String title) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle(title);
        int userSelection = fileChooser.showOpenDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            return selectedFile.getAbsolutePath();
        }
        return null;
    }
}
