package prototypes;

import javafx.scene.Group;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Sphere;

public class SymboleI {

 private   Cylinder c = new Cylinder(10,100);

  private  Sphere s = new Sphere(10);

  private  Group groupe = new Group();

    public SymboleI() {
        s.setLayoutY(c.getLayoutY() - c.getHeight()/3*2);
        groupe.getChildren().addAll(c,s);
    }

    public Group getGroupe() {
        return groupe;
    }

    public void setGroupe(Group groupe) {
        this.groupe = groupe;
    }
}
