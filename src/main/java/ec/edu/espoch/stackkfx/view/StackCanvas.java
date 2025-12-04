
package ec.edu.espoch.stackkfx.view;



import java.util.Collections;
import java.util.List;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;


public class StackCanvas extends Canvas {
    
    /** Datos a dibujar: snapshot de los valores. Lo entrega el Controlador. */
    private List<Integer> values = Collections.emptyList();

    /** Índice del nodo a resaltar (por ejemplo, cuando se encontró un valor). -1 = ninguno. */
    private int highlightIndex = -1;

    // Medidas de dibujo (layout) para la representación gráfica
    private final double margin = 24;   // margen izquierdo/superior
    private final double nodeW = 90;    // ancho de un nodo
    private final double nodeH = 48;    // alto de un nodo
    private final double spacing = 36;  // separación horizontal entre nodos
    private final double baseY = 110;   // eje Y base donde se dibujan los nodos

    /**
     * Constructor: configura tamaño inicial del Canvas.
     * Luego, en render() se puede reajustar el ancho según el número de nodos
     * para permitir scroll horizontal en el ScrollPane.
     */
    public StackCanvas() {
        setWidth(1200);
        setHeight(260);
    }

    /**
     * Recibe desde el Controlador el snapshot (lista de valores) que se debe dibujar.
     * La Vista solo almacena estos valores y luego render() los representa.
     *
     * @param values lista de enteros a dibujar (si llega null se usa lista vacía).
     */
    public void setValues(List<Integer> values) {
        this.values = (values == null) ? Collections.emptyList() : values;
    }

    /**
     * Define cuál índice se debe resaltar (por ejemplo, resultado de una búsqueda).
     *
     * @param idx índice a resaltar; si no existe, se suele usar -1 (o llamar clearHighlight()).
     */
    public void setHighlightIndex(int idx) {
        this.highlightIndex = idx;
    }

    /**
     * Quita cualquier resaltado.
     * Se usa cuando no hay búsqueda activa o cuando no se encontró el elemento.
     */
    public void clearHighlight() {
        this.highlightIndex = -1;
    }

    /**
     * Redibuja completamente el Canvas.
     * - Ajusta el tamaño del Canvas según la cantidad de nodos (para scroll).
     * - Limpia el fondo.
     * - Dibuja títulos e instrucciones.
     * - Dibuja head, nodos y flechas, y el "null" final.
     */
    public void render() {
        int n = values.size();

        // Ajustar ancho según cantidad de nodos (para scroll horizontal)
        double neededW = (n == 0)
                ? 900
                : margin * 2 + n * nodeW + (n - 1) * spacing + 180; // extra para "null"
        setWidth(Math.max(900, neededW));
        setHeight(260);

        GraphicsContext g = getGraphicsContext2D();

        // 1) Limpiar fondo (pintar blanco)
        g.setFill(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());

        // 2) Título y leyenda
        g.setFill(Color.rgb(20, 20, 20));
        g.setFont(Font.font(16));
        g.fillText("Pila", margin, 28);

        g.setFill(Color.rgb(90, 90, 90));
        g.setFont(Font.font(12));
        g.fillText("Nodo: [dato | next]  →  último apunta a null", margin, 48);

        // 3) Si está vacía, mostrar mensaje y salir
        if (n == 0) {
            g.setFill(Color.rgb(120, 120, 120));
            g.setFont(Font.font(14));
            g.fillText("La pila está vacía. Inserta valores para ver los nodos.", margin, baseY);
            return;
        }

        // 4) Dibujar "head" apuntando al primer nodo
        drawHead(g);

        // 5) Dibujar nodos y sus flechas al siguiente
        double x = margin;
        for (int i = 0; i < n; i++) {
            boolean hl = (i == highlightIndex);

            // Dibujar nodo [dato | next]
            drawNode(g, x, baseY, String.valueOf(values.get(i)), hl);

            // Dibujar flecha al siguiente (o a null en el último)
            double midY = baseY + nodeH / 2;
            double x2 = x + nodeW + spacing;

            g.setStroke(Color.rgb(60, 60, 60));
            g.setLineWidth(2);
            g.strokeLine(x + nodeW, midY, x2, midY);
            arrowHead(g, x + nodeW, midY, x2, midY);

            // Solo en el último, dibuja el "null"
            if (i == n - 1) {
                g.setFill(Color.rgb(120, 120, 120));
                g.setFont(Font.font(12));
                g.fillText("null", x2 + 10, midY + 4);
            }

            x += nodeW + spacing;
        }
    }

    /**
     * Dibuja la etiqueta "head" y una flecha vertical hacia el primer nodo.
     * Es un detalle visual para indicar dónde inicia la lista.
     */
    private void drawHead(GraphicsContext g) {
        double headX = margin;
        double headY = baseY - 45;

        g.setFill(Color.rgb(50, 50, 50));
        g.setFont(Font.font(12));
        g.fillText("head", headX, headY);

        g.setStroke(Color.rgb(60, 60, 60));
        g.setLineWidth(2);

        // Línea vertical hacia el nodo
        g.strokeLine(headX + 14, headY + 6, headX + 14, baseY - 8);

        // Punta de flecha (vertical)
        arrowHead(g, headX + 14, baseY - 8, headX + 14, baseY);
    }

    /**
     * Dibuja un nodo con dos secciones:
     * - izquierda: dato
     * - derecha: "next"
     * Si highlighted=true, cambia el borde y marca "FOUND".
     */
    private void drawNode(GraphicsContext g, double x, double y, String text, boolean highlighted) {
        double splitX = x + nodeW * 0.62; // división entre dato y next

        // Borde del nodo
        g.setLineWidth(highlighted ? 3 : 2);
        g.setStroke(highlighted ? Color.rgb(25, 90, 200) : Color.rgb(40, 40, 40));
        g.strokeRoundRect(x, y, nodeW, nodeH, 16, 16);

        // Línea divisoria
        g.strokeLine(splitX, y, splitX, y + nodeH);

        // Texto del dato
        g.setFill(Color.rgb(20, 20, 20));
        g.setFont(Font.font(14));
        g.fillText(text, x + 24, y + 30);

        // Texto "next"
        g.setFill(Color.rgb(90, 90, 90));
        g.setFont(Font.font(11));
        g.fillText("next", splitX + 6, y + 28);

        // Indicador de resaltado
        if (highlighted) {
            g.setFill(Color.rgb(25, 90, 200));
            g.setFont(Font.font(11));
            g.fillText("FOUND", x + 6, y - 6);
        }
    }

    /**
     * Dibuja la punta de una flecha desde (x1,y1) hacia (x2,y2).
     * Esta función asume que la línea principal ya fue dibujada con strokeLine.
     */
    private void arrowHead(GraphicsContext g, double x1, double y1, double x2, double y2) {
        double angle = Math.atan2(y2 - y1, x2 - x1);
        double len = 10;

        double a1 = angle - Math.PI / 8;
        double a2 = angle + Math.PI / 8;

        double ax1 = x2 - len * Math.cos(a1);
        double ay1 = y2 - len * Math.sin(a1);
        double ax2 = x2 - len * Math.cos(a2);
        double ay2 = y2 - len * Math.sin(a2);

        g.strokeLine(x2, y2, ax1, ay1);
        g.strokeLine(x2, y2, ax2, ay2);
    }
    
   
}
