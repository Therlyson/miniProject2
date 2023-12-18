package apresentacao;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class LoginScreen extends JFrame{
    JTextField field;
    public LoginScreen() {
        setTitle("Space Planner");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        setLayout(null);
        JButton jButton = new JButton("Entrar");
        jButton.setBounds(340, 310, 90, 30);
        jButton.setFont(new Font("Arial", Font.PLAIN, 14));
        jButton.setForeground(new Color(5, 4, 4));
        jButton.setBackground(new Color(179, 184, 184));
        add(jButton);
        jButton.addActionListener(this::loginButtonClicked);

        JLabel title = new JLabel("SPACE PLANNER");
        title.setBounds(310,3,3500,50);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setForeground(Color.DARK_GRAY);
        add(title);

        JLabel texto = new JLabel("Entrar no Sistema ");
        texto.setBounds(110,150,400,50);
        texto.setFont(new Font("Arial", Font.BOLD, 22));
        texto.setForeground(Color.DARK_GRAY);
        add(texto);

        JLabel user = new JLabel("Usuário: ");
        user.setBounds(180,210,400,50);
        user.setFont(new Font("Arial", Font.PLAIN, 17));
        add(user);
        field = new JTextField();
        field.setBounds(250,220,330,30);
        field.setFont(new Font("Arial", Font.ITALIC, 17));
        field.setSelectionColor(Color.black);
        add(field);

        setVisible(true);
    }

    private void loginButtonClicked(ActionEvent e) {
        String nomeUsuario = field.getText();

        if (!nomeUsuario.isEmpty()) {
            openFuncionalidadesTela(nomeUsuario);
        } else {
            JOptionPane.showMessageDialog(null, "Por favor, digite um nome de usuário.",
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void openFuncionalidadesTela(String nomeUsuario) {
        dispose();
        new Options(nomeUsuario);
    }

}
