package danslchamp;

public class Circuit {

    private Source source;

    public Circuit(Source source) {
        this.source = source;
    }

    public Source getSource() {
        return source;
    }

    public void traverser() {
        traverserRecursive(source);
    }

    private void traverserRecursive(Composante composante) {
        System.out.println("Traverse de " + composante);
        for (Composante next : composante.getNexts()) {
            if (next == source) break; // !!
            traverserRecursive(next);   // un thread pour chaque maille?
        }
    }
}
