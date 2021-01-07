package res.managit.dbo.relations;

public enum TypeAction {
    Loading("loading"),
    UnLoading("unloading");

    public final String label;

    TypeAction(String label) {
        this.label = label;
    }
}
