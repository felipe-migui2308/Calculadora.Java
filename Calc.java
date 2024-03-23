import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
 * Calc
 */
class Calc {

    public double subtracao(double x, double y) {
        return x - y;
    }

    public double adicao(double x, double y) {
        return x + y;
    }

    public double multiplicacao(double x, double y) {
        return x * y;
    }

    public double divisao(double x, double y) {
        if (y == 0) {
            throw new ArithmeticException("Inválido");
        }
        return x / y;
    }
}

class PainelCalc extends JPanel {
    private JTextField tela;
    private String expressao;

    public PainelCalc() {
        setLayout(new BorderLayout());

        tela = new JTextField();
        tela.setEditable(false);
        tela.setPreferredSize(new Dimension(300, 100));
        tela.setFont(tela.getFont().deriveFont(Font.BOLD, 35));
        add(tela, BorderLayout.NORTH);

        JPanel PainelBotoes = new JPanel();
        PainelBotoes.setLayout(new GridLayout(4, 4));

        String[] botoes = { "7", "8", "9", "/", "4", "5", "6", "*", "1", "2", "3", "-", "0", ".", "=", "+" };
        for (String textoBotao : botoes) {
            JButton botao = new JButton(textoBotao);
            botao.setFont(botao.getFont().deriveFont(Font.BOLD, 25));
            botao.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String textoBotao = botao.getText();
                    if (textoBotao.equals("=")) {
                        calcularResultado();
                    } else if (textoBotao.equals("C")) {
                        limparTela();
                    } else {
                        tela.setText(tela.getText() + textoBotao);
                    }
                }
            });
            PainelBotoes.add(botao);
        }

        JPanel PainelC = new JPanel();
        PainelC.setLayout(new GridLayout(1, 1));
        JButton botaoLimpar = new JButton("C");
        botaoLimpar.setFont(botaoLimpar.getFont().deriveFont(Font.BOLD, 25));
        botaoLimpar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                limparTela();
            }
        });
        PainelC.add(botaoLimpar);
        add(PainelC, BorderLayout.WEST);

        add(PainelBotoes, BorderLayout.CENTER);
    }

    private void calcularResultado() {
        expressao = tela.getText();
        try {
            String[] partes = expressao.split("(?<=[-+*/])|(?=[-+*/])");
            double resultado = Double.parseDouble(partes[0]);
            for (int i = 1; i < partes.length; i += 2) {
                String operador = partes[i];
                double proximoNumero = Double.parseDouble(partes[i + 1]);
                switch (operador) {
                    case "+":
                        resultado = new Calc().adicao(resultado, proximoNumero);
                        break;
                    case "-":
                        resultado = new Calc().subtracao(resultado, proximoNumero);
                        break;
                    case "*":
                        resultado = new Calc().multiplicacao(resultado, proximoNumero);
                        break;
                    case "/":
                        resultado = new Calc().divisao(resultado, proximoNumero);
                        break;
                    default:
                        throw new IllegalArgumentException("Operador inválido: " + operador);
                }
            }
            tela.setText(String.valueOf(resultado));
        } catch (NumberFormatException ex) {
            tela.setText("Erro");
        } catch (ArithmeticException ex) {
            tela.setText("Inválido");
        }
    }

    private void limparTela() {
        tela.setText("");
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Calculadora");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(new PainelCalc());
            frame.pack();
            frame.setSize(400, 600);
            frame.setVisible(true);
        });

    }
}
