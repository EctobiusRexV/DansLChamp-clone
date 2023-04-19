package sim.danslchamp.svg;

import sim.danslchamp.circuit.Circuit;
import sim.danslchamp.circuit.Composant;
import sim.danslchamp.circuit.Fil;

public abstract class FXASvg {

    public static String aSvg(Circuit circuit) {
        StringBuilder svg = new StringBuilder();

        svg.append(creerEntete()).append("\n\n");
        circuit.getComposants().forEach(composant ->
                svg.append(composant instanceof Fil
                        ? convertirFil((Fil) composant)
                        : convertirComposant(composant)
                ).append("\n")
        );

        return svg.append("</svg>").toString();
    }

    private static String convertirFil(Fil fil) {
        return "\t<line x1=\"" + fil.getPosX() + "\" y1=\"" + fil.getPosY() + "\" x2=\"".concat(String.valueOf(fil.getPosX()+fil.getEndX())).concat("\" y2=\"").concat(String.valueOf(fil.getPosY()+fil.getEndY())).concat("\" />");
    }

    private static String convertirComposant(Composant composant) {
        return ajouterAttributsEtFermer(composant, new StringBuilder("\t<use xlink:href=\"#" + composant + "\" x=\"" + composant.getPosX() + "\" y=\"" + composant.getPosY() + "\" "));
    }

    private static String ajouterAttributsEtFermer(Composant composant, StringBuilder sb) {
        for (Composant.ValeurNomWrapper valeur :
                composant.getValeursModifiables()) {
            sb.append(valeur.id).append("=\"").append(valeur.valeur.getValeur(Composant.Unite.UNITE)).append("\" ");    // FIXME: 2023-04-19 Valeur à l'unité?
        }

        return sb.append("/>").toString();
    }

    private static String creerEntete() {
        return """
                <svg version="1.1"
                	 xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" style="fill:none;stroke:black;stroke-width:3">
                	<defs>
                		<g id="Bobine" style="fill:none;stroke:black;stroke-width:3">
                			<path d="m 0 10 a 2 2 0 0 0 20 0 a 2 2 0 0 0 20 0 a 2 2 0 0 0 20 0 a 2 2 0 0 0 20 0" />
                			<ellipse cx="20" cy="2" rx="5" ry="10" />
                			<ellipse cx="40" cy="2" rx="5" ry="10" />
                			<ellipse cx="60" cy="2" rx="5" ry="10" />
                		</g>
                                 
                		<g id="Condensateur" style="fill:none;stroke:black;stroke-width:3">
                			<polyline points="0,10 20,10 20,0 20,10 40,10" />
                			<polyline points="0,20 20,20 20,30 20,20 40,20" />
                		</g>
                                 
                		<g id="Générateur" style="fill:none;stroke:black;stroke-width:3">
                			<circle cx="20" cy="20" r="20" />
                			<path d="m 5 20 c 10 -25 20 25 30 1" />
                		</g>
                                 
                		<g id="Pile" style="fill:none;stroke:black;stroke-width:3">
                			<polyline points="0,10 20,10 20,0 20,10 40,10" />
                			<text x="25" y="7" style="stroke-width:1">+</text>
                                 
                			<polyline points="5,20 35,20" style="stroke-width:5" />
                			<text x="25" y="29" style="stroke-width:1">-</text>
                                 
                			<polyline points="20,20 20,30" />
                		</g>
                                 
                		<g id="Résistor">
                			<polyline points="0,10 5,0 15,30 25,0 35,30 45,0 55,30 65,0 75,30 80,10" style="fill:none;stroke:black;stroke-width:3" />
                		</g>
                	</defs>""";
    }
}
