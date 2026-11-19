// TASK-04: DrawPad.java
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class DrawPad extends JFrame {
    private final DrawingCanvas canvas = new DrawingCanvas();
    private final JLabel coordsLabel = new JLabel("X: 0, Y: 0", JLabel.RIGHT);
    private Color currentColor = Color.BLUE;

    public DrawPad() {
        super("Interactive Vector DrawPad");

        setLayout(new BorderLayout());

        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton redButton = new JButton("Red");
        JButton blueButton = new JButton("Blue");
        JButton greenButton = new JButton("Green");
        JButton clearButton = new JButton("Clear Canvas");

        redButton.addActionListener(e -> currentColor = Color.RED);
        blueButton.addActionListener(e -> currentColor = Color.BLUE);
        greenButton.addActionListener(e -> currentColor = Color.GREEN);
        clearButton.addActionListener(e -> canvas.clear());

        controlPanel.add(new JLabel("Brush Color:"));
        controlPanel.add(redButton);
        controlPanel.add(blueButton);
        controlPanel.add(greenButton);
        controlPanel.add(clearButton);

        add(controlPanel, BorderLayout.NORTH);
        add(canvas, BorderLayout.CENTER);

        JPanel statusPanel = new JPanel(new BorderLayout());
        statusPanel.setBorder(BorderFactory.createEmptyBorder(3, 10, 3, 10));
        statusPanel.add(new JLabel("Status: Active"), BorderLayout.WEST);
        statusPanel.add(coordsLabel, BorderLayout.EAST);
        add(statusPanel, BorderLayout.SOUTH);

        MouseAdapter mouseHandler = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                canvas.startLine(e.getPoint(), currentColor);
                updateCoords(e.getPoint());
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                canvas.addPoint(e.getPoint());
                updateCoords(e.getPoint());
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                updateCoords(e.getPoint());
            }
        };

        canvas.addMouseListener(mouseHandler);
        canvas.addMouseMotionListener(mouseHandler);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 500);
        setLocationRelativeTo(null);
    }

    private void updateCoords(Point p) {
        coordsLabel.setText(String.format("X: %d, Y: %d", p.x, p.y));
    }

    private static class Stroke {
        List<Point> points = new ArrayList<>();
        Color color;

        Stroke(Color color) {
            this.color = color;
        }
    }

    private static class DrawingCanvas extends JPanel {
        private final List<Stroke> strokes = new ArrayList<>();
        private Stroke currentStroke;

        public DrawingCanvas() {
            setBackground(Color.WHITE);
        }

        public void startLine(Point p, Color color) {
            currentStroke = new Stroke(color);
            currentStroke.points.add(p);
            strokes.add(currentStroke);
            repaint();
        }

        public void addPoint(Point p) {
            if (currentStroke != null) {
                currentStroke.points.add(p);
                repaint();
            }
        }

        public void clear() {
            strokes.clear();
            currentStroke = null;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setStroke(new BasicStroke(3.0f));

            for (Stroke stroke : strokes) {
                g2.setColor(stroke.color);
                for (int i = 0; i < stroke.points.size() - 1; i++) {
                    Point p1 = stroke.points.get(i);
                    Point p2 = stroke.points.get(i + 1);
                    g2.drawLine(p1.x, p1.y, p2.x, p2.y);
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DrawPad().setVisible(true));
    }
}
