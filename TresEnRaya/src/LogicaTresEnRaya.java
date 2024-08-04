public class LogicaTresEnRaya {
    private final char[][] tablero;
    private char jugadorActual;
    private boolean juegoTerminado;

    public LogicaTresEnRaya() {
        tablero = new char[3][3];
        reiniciarJuego();
    }

    public void reiniciarJuego() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                tablero[i][j] = '-';
            }
        }
        jugadorActual = 'O';
        juegoTerminado = false;
    }

    public boolean jugar(int fila, int columna) {
        if (tablero[fila][columna] == '-' && !juegoTerminado) {
            tablero[fila][columna] = jugadorActual;
            juegoTerminado = verificarGanador() || verificarEmpate();
            jugadorActual = (jugadorActual == 'O') ? 'X' : 'O';
            return true;
        }
        return false;
    }

    public boolean verificarGanador() {
        for (int i = 0; i < 3; i++) {
            if (tablero[i][0] == tablero[i][1] && tablero[i][1] == tablero[i][2] && tablero[i][0] != '-') {
                return true;
            }
            if (tablero[0][i] == tablero[1][i] && tablero[1][i] == tablero[2][i] && tablero[0][i] != '-') {
                return true;
            }
        }
        if (tablero[0][0] == tablero[1][1] && tablero[1][1] == tablero[2][2] && tablero[0][0] != '-') {
            return true;
        }
        return tablero[0][2] == tablero[1][1] && tablero[1][1] == tablero[2][0] && tablero[0][2] != '-';
    }

    private boolean verificarEmpate() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (tablero[i][j] == '-') {
                    return false;
                }
            }
        }
        return true;
    }

    public char getJugadorActual() {
        return jugadorActual;
    }

    public char[][] getTablero() {
        return tablero;
    }

    public boolean isJuegoTerminado() {
        return juegoTerminado;
    }
}
