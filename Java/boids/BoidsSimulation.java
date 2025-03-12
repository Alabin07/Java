import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BoidsSimulation extends JPanel {

    List<Boid> boids = new ArrayList<>();
    List<Eagle> eagles = new ArrayList<>();

    public BoidsSimulation() {
        setPreferredSize(new Dimension(800, 600));
        for (int i = 0; i < 150; i++) { // Create x:num boids
            boids.add(new Boid(Math.random() * 800, Math.random() * 600));
        }

        for (int i = 0; i < 2; i++) { // Create x:num Eagles
            eagles.add(new Eagle(Math.random() * 800, Math.random() * 600));
        }

        Timer timer = new Timer(16, e -> {
            updateBoids();
            updateEagle();
            repaint();
        });
        timer.start();
    }

    private void updateBoids() {
        for (Boid boid : boids) {
            boid.update(boids, eagles);
        }
    }

    private void updateEagle() {
        for (Eagle eagle : eagles) {
            eagle.update(boids, eagles);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Boid boid : boids) {
            boid.draw(g);
        }
        for (Eagle eagle : eagles) {
            eagle.draw(g);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Boids Simulation");
        BoidsSimulation simulation = new BoidsSimulation();
        frame.add(simulation);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
