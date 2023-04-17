package fifa;

import java.util.Collection;
import java.util.LinkedList;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Hitboxes {

    private Collection<Node> hitboxes = new LinkedList<Node>(); // Collection to store hitbox nodes

    public Rectangle border; // Border rectangle node

    private final Color color = Color.BLUE; // Color of the hitboxes

    private final double goalpostwidth = 10; // Width of the goalpost hitboxes
    private final double goalpostheight = 60; // Height of the goalpost hitboxes
    private final double R_offset_x = -5; // X-axis offset for right goalpost hitbox
    private final double R_offset_y = 0; // Y-axis offset for right goalpost hitbox
    private final double yBorderOffset = 10; // Offset for y-axis border hitbox
    private final double yBorderHeight = 40; // Height of y-axis border hitbox
    private final double scoreOffset = 5; // Offset for score hitbox
    private Rectangle collisionHitbox; // Rectangle node for collision hitbox

    public Hitboxes(PlayField field) {
        ObservableList<Double> ptsR = field.goalR.getPoints(); // Retrieve points of the right goalpost
        ObservableList<Double> ptsPolygon = field.field.getPoints(); // Retrieve points of the field polygon

        // Create left goalpost hitbox
        Rectangle leftR = new Rectangle(ptsR.get(6) + R_offset_x, ptsR.get(7) + R_offset_y, goalpostwidth, goalpostheight);
        leftR.setFill(color);
        leftR.setOpacity(0.5);

        // Create right goalpost hitbox
        Rectangle rightR = new Rectangle(ptsR.get(0) + R_offset_x, ptsR.get(7) + R_offset_y, goalpostwidth, goalpostheight);
        rightR.setFill(color);
        rightR.setOpacity(0.5);

        // Create bottom hitbox for checking score collisions
        Rectangle bottom = new Rectangle(ptsR.get(4), ptsR.get(5) + R_offset_y - scoreOffset, ptsR.get(0) - ptsR.get(6), goalpostwidth);
        bottom.setFill(color);
        bottom.setOpacity(0.5);
        collisionHitbox = bottom;

        // Create border hitbox for y-axis collisions
        border = new Rectangle(ptsPolygon.get(2) - yBorderOffset, ptsR.get(5) + R_offset_y - yBorderOffset / 2 + scoreOffset,
                ptsPolygon.get(4) - ptsPolygon.get(2) + 2 * yBorderOffset, yBorderHeight);
        border.setFill(color);
        border.setOpacity(0.5);

        add(leftR); // Add left goalpost hitbox to collection
        add(rightR); // Add right goalpost hitbox to collection
        add(bottom); // Add bottom hitbox to collection
    }

    void add(Node node) {
        hitboxes.add(node); // Add node to hitboxes collection
    }

    public Collection<Node> getElementsCollection() {
        return hitboxes; // Return the hitboxes collection
    }

    public void showHitboxes(Collection<Node> elements) {
        for (Node n : hitboxes) {
            elements.add(n); // Add each hitbox node to the provided collection
        }
        elements.add(border); // Add border hitbox to the provided collection
        elements.add(collisionHitbox); // Add collision hitbox to the provided collection
    }

    public void hideHitboxes(Collection<Node> elements) {
        for (Node n : hitboxes) {
            elements.remove(n); // Remove each hitbox node from the provided collection
        }
        elements.remove(border); // Remove border hitbox from the provided collection
        elements.remove(collisionHitbox); // Remove collision hitbox from the provided collection
    }



    public Rectangle getGoalHitbox() {
        return collisionHitbox;
    }

    @SuppressWarnings("unused")
	private Rectangle createRectangle(final double x, final double y, final double width, final double height) {
        return new Rectangle(x, y, width, height);
    }
}
