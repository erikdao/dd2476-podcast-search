package dd2476.group18.podcastsearch.views;

/**
 * Define different level of views for the RESTfull API
 * List: return JSON will have a minimal set of attributes
 * Detail: return JSON will include some more detailed attributes
 */
public class View {
    public static class NestedList {}
    public static class List extends NestedList{}
    public static class Detail extends List {}
}
