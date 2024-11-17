class RedBlackTree {
    private final Node TNULL = new Node(null, null, null, 0.0); // Sentinel node
    private Node root;

    public RedBlackTree() {
        TNULL.isRed = false; // Sentinel is always black
        root = TNULL;
    }

    public void insert(String productId, String name, String category, double price) {
        Node newNode = new Node(productId, name, category, price);
        newNode.left = newNode.right = TNULL;

        Node parent = null;
        Node current = root;

        while (current != TNULL) {
            parent = current;
            if (newNode.productId.compareTo(current.productId) < 0) {
                current = current.left;
            } else if (newNode.productId.compareTo(current.productId) > 0) {
                current = current.right;
            } else {
                System.out.println("Error: Product with ID " + productId + " already exists.");
                return;
            }
        }

        newNode.parent = parent;

        if (parent == null) {
            root = newNode;
        } else if (newNode.productId.compareTo(parent.productId) < 0) {
            parent.left = newNode;
        } else {
            parent.right = newNode;
        }

        newNode.isRed = true; // New node must be red
        fixInsert(newNode);
    }

    private void fixInsert(Node node) {
        Node uncle;
        while (node.parent != null && node.parent.isRed) {
            if (node.parent == node.parent.parent.left) {
                uncle = node.parent.parent.right;
                if (uncle.isRed) {
                    uncle.isRed = false;
                    node.parent.isRed = false;
                    node.parent.parent.isRed = true;
                    node = node.parent.parent;
                } else {
                    if (node == node.parent.right) {
                        node = node.parent;
                        rotateLeft(node);
                    }
                    node.parent.isRed = false;
                    node.parent.parent.isRed = true;
                    rotateRight(node.parent.parent);
                }
            } else {
                uncle = node.parent.parent.left;
                if (uncle.isRed) {
                    uncle.isRed = false;
                    node.parent.isRed = false;
                    node.parent.parent.isRed = true;
                    node = node.parent.parent;
                } else {
                    if (node == node.parent.left) {
                        node = node.parent;
                        rotateRight(node);
                    }
                    node.parent.isRed = false;
                    node.parent.parent.isRed = true;
                    rotateLeft(node.parent.parent);
                }
            }
        }
        root.isRed = false; // Root is always black
    }

    private void rotateLeft(Node node) {
        Node rightChild = node.right;
        node.right = rightChild.left;
        if (rightChild.left != TNULL) {
            rightChild.left.parent = node;
        }
        rightChild.parent = node.parent;
        if (node.parent == null) {
            root = rightChild;
        } else if (node == node.parent.left) {
            node.parent.left = rightChild;
        } else {
            node.parent.right = rightChild;
        }
        rightChild.left = node;
        node.parent = rightChild;
    }

    private void rotateRight(Node node) {
        Node leftChild = node.left;
        node.left = leftChild.right;
        if (leftChild.right != TNULL) {
            leftChild.right.parent = node;
        }
        leftChild.parent = node.parent;
        if (node.parent == null) {
            root = leftChild;
        } else if (node == node.parent.right) {
            node.parent.right = leftChild;
        } else {
            node.parent.left = leftChild;
        }
        leftChild.right = node;
        node.parent = leftChild;
    }

    public Node search(String productId) {
        Node current = root;
        while (current != TNULL) {
            if (productId.equals(current.productId)) {
                return current;
            } else if (productId.compareTo(current.productId) < 0) {
                current = current.left;
            } else {
                current = current.right;
            }
        }
        return null; // Not found
    }

    public void printProductDetails(String productId) {
        Node product = search(productId);
        if (product != null) {
            System.out.println("Product ID: " + product.productId);
            System.out.println("Name: " + product.name);
            System.out.println("Category: " + product.category);
            System.out.println("Price: $" + product.price);
        } else {
            System.out.println("Error: Product with ID " + productId + " not found.");
        }
    }
}