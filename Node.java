class Node {
    String productId;
    String name;
    String category;
    double price;
    Node left, right, parent;
    boolean isRed; // Red = true, Black = false

    Node(String productId, String name, String category, double price) {
        this.productId = productId;
        this.name = name;
        this.category = category;
        this.price = price;
        this.isRed = true; // New nodes are always red
        this.left = this.right = this.parent = null;
    }
}