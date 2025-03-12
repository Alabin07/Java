import javax.swing.*;
import java.awt.*;
import java.util.List;

class Boid {
    double x, y, vx, vy;

    private static final double MAX_SPEED = 2;
    private static final double ALIGN_RADIOUS = 70;
    private static final double ALIGN_SPEED = 0.015;
    private static final double COHESION_STRENGTH = 0.015;
    private static final double AVOIDANCE_STRENGTH = 0.25;
    private static final double AVOIDANCE_RADIUS = 12;
    private static final double FEAR_RADIOUS = 45;
    private static final double FEAR_STRENGTH = 0.67;



    public Boid(double x, double y) {
        this.x = x;
        this.y = y;
        this.vx = Math.random() * 2 - 1; // Random velocity
        this.vy = Math.random() * 2 - 1;
    }

    public void Fear(List<Eagle> eagles)
    {
        System.out.println("jag är rädd");
        double count = 0;
        double coX = 0, coY = 0;

        for (Eagle other : eagles)
        {
            double distance = Math.hypot(x - other.x, y - other.y);

            if (distance < FEAR_RADIOUS)
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
                vx += FEAR_STRENGTH;
            }
            else if (coX > x)
            {
                vx -= FEAR_STRENGTH;
            }
            if (coY < y)
            {
                vy += FEAR_STRENGTH;
            }
            else if (coY > y)
            {
                vy -= FEAR_STRENGTH;
            }
        }
    }


    public void Alignment(List<Boid> boids)
    {
        double count = 0;
        double alignX = 0, alignY = 0;


        for (Boid other : boids)
        {
            double distance = Math.hypot(x - other.x, y - other.y);

            if (other != this && distance < ALIGN_RADIOUS)
            {
                alignX += other.vx;
                alignY += other.vy;

                count ++;
            }
        }
        if (count > 0)
        {
            vx += (alignX/count) * ALIGN_SPEED;
            vy += (alignY/count) * ALIGN_SPEED;
        }
    }

    public void Separation(List<Boid> boids)
    {
        double count = 0;
        double coX = 0, coY = 0;

        for (Boid other : boids)
        {
            double distance = Math.hypot(x - other.x, y - other.y);

            if (other != this && distance < AVOIDANCE_RADIUS)
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
                vx += AVOIDANCE_STRENGTH;
            }
            else if (coX > x)
            {
                vx -= AVOIDANCE_STRENGTH;
            }
            if (coY < y)
            {
                vy += AVOIDANCE_STRENGTH;
            }
            else if (coY > y)
            {
                vy -= AVOIDANCE_STRENGTH;
            }
        }
    }

    public void Cohesion(List<Boid> boids)
    {
        double count = 0;
        double coX = 0, coY = 0;

        for (Boid other : boids)
        {
            double distance = Math.hypot(x - other.x, y - other.y);

            if (other != this && distance < ALIGN_RADIOUS && distance > AVOIDANCE_RADIUS)
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
                vx -= COHESION_STRENGTH;
            }
            else if (coX > x)
            {
                vx += COHESION_STRENGTH;
            }
            if (coY < y)
            {
                vy -= COHESION_STRENGTH;
            }
            else if (coY > y)
            {
                vy += COHESION_STRENGTH;
            }
        }
    }

    public void update(List<Boid> boids, List<Eagle> eagles) {

        Alignment(boids);
        Cohesion(boids);
        Separation(boids);
        Fear(eagles);

        x += vx;
        y += vy;
        limitSpeed();

        if (x < 0) x = 800;
        if (x > 800) x = 0;
        if (y < 0) y = 600;
        if (y > 600) y = 0;
    }

    private void limitSpeed() {
        double speed = Math.hypot(vx, vy);
        if (speed > MAX_SPEED) {
            vx = (vx / speed) * MAX_SPEED;
            vy = (vy / speed) * MAX_SPEED;
        }
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillOval((int) x, (int) y, 10, 10);
    }
}
