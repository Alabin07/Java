import java.awt.*;
import java.util.List;

public class Eagle {

    private static final double HUNT_RADIOUS = 200;
    private static final double HUNT_STRENGTH = 0.1;
    private static final double MAX_SPEED = 4;
    private static final double AVOID_RADIOUS = 170;
    private static final double AVOID_STRENGTH = 0.2;

    double x, y, vx, vy;

    public Eagle(double x, double y) {
        this.x = x;
        this.y = y;
        this.vx = Math.random() * 2 - 1; // Random velocity
        this.vy = Math.random() * 2 - 1;
    }

    public void Hunt(List<Boid> boids)
    {

        double count = 0;
        double coX = 0, coY = 0;

        for (Boid other : boids)
        {
            double distance = Math.hypot(x - other.x, y - other.y);

            if (distance < HUNT_RADIOUS)
            {
                coX += other.x;
                coY += other.y;
                count ++;
            }

        }

        coX = coX/count;
        coY = coY/count;
        if (count > 0)
        {
            if (coX < x)
            {
                vx -= HUNT_STRENGTH;
            }
            else if (coX > x)
            {
                vx += HUNT_STRENGTH;
            }
            if (coY < y)
            {
                vy -= HUNT_STRENGTH;
            }
            else if (coY > y)
            {
                vy += HUNT_STRENGTH;
            }
        }

    }

    public void Avoid(List<Eagle> eagles)
    {
        System.out.println("jag är rädd");
        double count = 0;
        double coX = 0, coY = 0;

        for (Eagle other : eagles)
        {
            double distance = Math.hypot(x - other.x, y - other.y);

            if (distance < AVOID_RADIOUS)
            {
                coX += other.x;
                coY += other.y;
                count ++;
            }

        }

        coX = coX/count;
        coY = coY/count;
        if (count > 0)
        {
            if (coX < x)
            {
                vx += AVOID_STRENGTH;
            }
            else if (coX > x)
            {
                vx -= AVOID_STRENGTH;
            }
            if (coY < y)
            {
                vy += AVOID_STRENGTH;
            }
            else if (coY > y)
            {
                vy -= AVOID_STRENGTH;
            }
        }
    }

    private void limitSpeed() {
        double speed = Math.hypot(vx, vy);
        if (speed > MAX_SPEED) {
            vx = (vx / speed) * MAX_SPEED;
            vy = (vy / speed) * MAX_SPEED;
        }
    }

    public void update(List<Boid> boids, List<Eagle> eagles) {

        Hunt(boids);
        limitSpeed();
        Avoid(eagles);

        x += vx;
        y += vy;

        if (x < 0) x = 800;
        if (x > 800) x = 0;
        if (y < 0) y = 600;
        if (y > 600) y = 0;
    }



    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillOval((int) x, (int) y, 20, 20);
        System.out.println("jag körs");
    }
}
