
package ec.edu.espoch.stackkfx.view;

import java.util.Collections;
import java.util.List;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class StackCanvas extends Canvas {

    private List<Integer> values = Collections.emptyList();
    private int highlightIndex = -1;

    private final double nodeW = 90;
    private final double nodeH = 45;
    private final double spacing = 20;
    private final double marginX = 150;
    private final double marginY = 80;

    public StackCanvas() {
        setWidth(500);
        setHeight(500);
    }

    public void setValues(List<Integer> values) {
        this.values = values == null ? Collections.emptyList() : values;
    }

    public void setHighlightIndex(int idx) {
        this.highlightIndex = idx;
    }

    public void clearHighlight() {
        this.highlightIndex = -1;
    }

    public void render() {
        GraphicsContext g = getGraphicsContext2D();

        // Fondo
        g.setFill(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());

        // Título
        g.setFill(Color.BLACK);
        g.setFont(Font.font(16));
        g.fillText("Pila (Stack)", 30, 30);

        if (values.isEmpty()) {
            g.setFont(Font.font(14));
            g.setFill(Color.GRAY);
            g.fillText("La pila está vacía", marginX, marginY);
            return;
        }

        double y = marginY;

        // Dibuja TOP
        g.setFont(Font.font(12));
        g.setFill(Color.BLACK);
        g.fillText("top", marginX - 35, y + 20);

        for (int i = 0; i < values.size(); i++) {
            boolean hl = (i == highlightIndex);
            drawNode(g, marginX, y, values.get(i), hl);

            // Flecha al siguiente
            if (i < values.size() - 1) {
                drawArrow(
                        g,
                        marginX + nodeW / 2,
                        y + nodeH,
                        marginX + nodeW / 2,
                        y + nodeH + spacing
                );
            } else {
                g.setFont(Font.font(12));
                g.setFill(Color.GRAY);
                g.fillText("null", marginX + 30, y + nodeH + 25);
            }

            y += nodeH + spacing;
        }
    }

    private void drawNode(GraphicsContext g, double x, double y, int dato, boolean highlighted) {
        g.setStroke(highlighted ? Color.DODGERBLUE : Color.BLACK);
        g.setLineWidth(highlighted ? 3 : 2);
        g.strokeRoundRect(x, y, nodeW, nodeH, 12, 12);

        g.setFont(Font.font(14));
        g.setFill(Color.BLACK);
        g.fillText(String.valueOf(dato), x + 30, y + 28);

        if (highlighted) {
            g.setFont(Font.font(10));
            g.fillText("TOP", x + 5, y - 6);
        }
    }

    private void drawArrow(GraphicsContext g,
                           double x1, double y1,
                           double x2, double y2) {

        g.setStroke(Color.BLACK);
        g.setLineWidth(2);
        g.strokeLine(x1, y1, x2, y2);

        double angle = Math.atan2(y2 - y1, x2 - x1);
        double len = 10;

        g.strokeLine(
                x2, y2,
                x2 - len * Math.cos(angle - Math.PI / 8),
                y2 - len * Math.sin(angle - Math.PI / 8)
        );

        g.strokeLine(
                x2, y2,
                x2 - len * Math.cos(angle + Math.PI / 8),
                y2 - len * Math.sin(angle + Math.PI / 8)
        );
    }
    
   
}
