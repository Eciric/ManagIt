package res.managit.dbo.relations;

/**
 * Enum created to sort names of event actions
 */
public enum TypeAction {
    /**
     * loading action - amount of products from EventItem are subtracted from stock
     */
    Loading("loading"),

    /**
     * unloading action - amount of products from EventItem are added from stock
     */
    UnLoading("unloading");

    public final String label;

    /**
     * Function which set the label of enum variable
     * @param label - name of label to set
     */
    TypeAction(String label) {
        this.label = label;
    }
}
