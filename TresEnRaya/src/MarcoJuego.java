import javax.swing.*;
import java.awt.*;

public class MarcoJuego extends JFrame {
    private final JButton[][] botones;
    private final LogicaTresEnRaya logica;
    private final JLabel estadoJuego;
    private final JPanel panelBotones;
    private final JLabel estadisticas;
    private int victoriasX;
    private int victoriasO;
    private int empates;

    public MarcoJuego() {
        logica = new LogicaTresEnRaya();
        botones = new JButton[3][3];
        estadoJuego = new JLabel("Jugador O está jugando");
        panelBotones = new JPanel(new GridLayout(3, 3));
        estadisticas = new JLabel("Victorias X: 0 | Victorias O: 0 | Empates: 0");

        inicializarGUI();
    }

    private void inicializarGUI() {
        setTitle("Juego de Tres en Raya");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        estadoJuego.setHorizontalAlignment(SwingConstants.CENTER);
        estadoJuego.setFont(new Font("Arial", Font.BOLD, 16));
        add(estadoJuego, BorderLayout.NORTH);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                botones[i][j] = new JButton() {
                    @Override
                    protected void paintComponent(Graphics g) {
                        super.paintComponent(g);
                        int width = getWidth();
                        int height = getHeight();
                        Graphics2D g2 = (Graphics2D) g;
                        g2.setStroke(new BasicStroke(5));
                        if (getText().equals("X")) {
                            g2.setColor(Color.RED);
                            g2.drawLine(10, 10, width - 10, height - 10);
                            g2.drawLine(10, height - 10, width - 10, 10);
                        } else if (getText().equals("O")) {
                            g2.setColor(Color.BLUE);
                            g2.drawOval(10, 10, width - 20, height - 20);
                        }
                    }
                };
                botones[i][j].setFont(new Font("Arial", Font.BOLD, 40));
                panelBotones.add(botones[i][j]);

                final int fila = i;
                final int columna = j;

                botones[i][j].addActionListener(e -> {
                    if (logica.jugar(fila, columna)) {
                        actualizarTablero();
                        actualizarEstadoJuego();
                    }
                });
            }
        }

        add(panelBotones, BorderLayout.CENTER);

        JButton botonReiniciar = new JButton("Reiniciar Juego");
        botonReiniciar.addActionListener(e -> {
            logica.reiniciarJuego();
            actualizarTablero();
            actualizarEstadoJuego();
        });
        add(botonReiniciar, BorderLayout.SOUTH);

        estadisticas.setHorizontalAlignment(SwingConstants.CENTER);
        add(estadisticas, BorderLayout.SOUTH);

        pack();
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximizar ventana
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void actualizarTablero() {
        char[][] tablero = logica.getTablero();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (tablero[i][j] == 'X') {
                    botones[i][j].setText("X");
                } else if (tablero[i][j] == 'O') {
                    botones[i][j].setText("O");
                } else {
                    botones[i][j].setText("");
                }
                botones[i][j].repaint();
            }
        }
    }

    private void actualizarEstadoJuego() {
        if (logica.isJuegoTerminado()) {
            if (logica.verificarGanador()) {
                char ganador = logica.getJugadorActual() == 'O' ? 'X' : 'O';
                mostrarMensaje("Jugador " + ganador + " ha ganado!");
                if (ganador == 'X') {
                    victoriasX++;
                } else {
                    victoriasO++;
                }
            } else {
                mostrarMensaje("Empate!");
                empates++;
            }
            actualizarEstadisticas();
            logica.reiniciarJuego();
            actualizarTablero();
            actualizarEstadoJuego();
        } else {
            estadoJuego.setText("Jugador " + logica.getJugadorActual() + " está jugando");
        }
    }

    private void actualizarEstadisticas() {
        estadisticas.setText("Victorias X: " + victoriasX + " | Victorias O: " + victoriasO + " | Empates: " + empates);
    }

    private void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
}

