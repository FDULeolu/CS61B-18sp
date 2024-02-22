import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private Node root;

    private class Node {
        private K key;
        private V value;
        private Node left, right;
        private int size;

        private Node(K k, V v) {
            key = k;
            value = v;
            size = 1;
        }
    }
    @Override
    public void clear() {
        root = null;
    }

    @Override
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    @Override
    public V get(K key) {
        return get(root, key);
    }

    private V get(Node x, K key) {
        if (x == null) {
            return null;
        }
        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            return get(x.left, key);
        } else if (cmp > 0) {
            return get(x.right, key);
        } else {
            return x.value;
        }
    }

    @Override
    public int size() {
        return size(root);
    }

    private int size(Node x) {
        if (x == null) {
            return 0;
        }
        return x.size;
    }

    @Override
    public void put(K key, V value) {
        root = put(root, key, value);
    }

    private Node put(Node x, K key, V value) {
        if (x == null) {
            x = new Node(key, value);
        }
        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            x.left = put(x.left, key, value);
        } else if (cmp > 0) {
            x.right = put(x.right, key, value);
        } else {
            x.value = value;
        }
        x.size = size(x.left) + size(x.right) + 1;
        return x;
    }

    @Override
    public Set keySet() {
        return keySet(root);
    }

    private Set keySet(Node x) {
        Set<K> returnSet = new HashSet<>();
        if (x != null) {
            returnSet.add(x.key);
            returnSet.addAll(keySet(x.left));
            returnSet.addAll(keySet(x.right));
        }
        return returnSet;
    }

    private Node findMaxInLeftTree(Node x) {
        if (x.left == null && x.right == null) {
            return x;
        } else if (x.right != null) {
            return findMaxInLeftTree(x.right);
        } else {
            return findMaxInLeftTree(x.left);
        }
    }

    private Node deleteFromKey(Node x, K key) {
        if (x == null) {
            return null;
        }
        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            x.left = deleteFromKey(x.left, key);
            return x;
        } else if (cmp > 0) {
            x.right = deleteFromKey(x.right, key);
            return x;
        } else {
            if (x.left == null && x.right == null) {
                return null;
            } else if (x.left != null && x.right == null) {
                x = x.left;
                return x;
            } else if (x.left == null && x.right != null) {
                x = x.right;
                return x;
            } else {
                Node maxNode = findMaxInLeftTree(x.left);
                x.value = maxNode.value;
                x.left = deleteFromKey(x.left, maxNode.key);
                return x;
            }
        }
    }

    private Node deleteFromKeyAndValue(Node x, K key, V value) {
        if (x == null) {
            return null;
        }
        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            x.left = deleteFromKeyAndValue(x.left, key, value);
            return x;
        } else if (cmp > 0) {
            x.right = deleteFromKeyAndValue(x.right, key, value);
            return x;
        } else {
            if (x.value != value) {
                return null;
            } else {
                if (x.left == null && x.right == null) {
                    return null;
                } else if (x.left != null && x.right == null) {
                    x = x.left;
                    return x;
                } else if (x.left == null && x.right != null) {
                    x = x.right;
                    return x;
                } else {
                    Node maxNode = findMaxInLeftTree(x.left);
                    x.value = maxNode.value;
                    x.left = deleteFromKey(x.left, maxNode.key);
                    return x;
                }
            }
        }
    }

    @Override
    public V remove(K key) {
        V value = get(key);
        root = deleteFromKey(root, key);
        return value;
    }

    @Override
    public V remove(K key, V value) {
        root = deleteFromKeyAndValue(root, key, value);
        return value;
    }

    @Override
    public Iterator iterator() {
        throw new UnsupportedOperationException();
    }

    public void printInOrder() {
        printInOrder(root);
    }

    private void printInOrder(Node x) {
        if (x.left == null && x.right == null) {
            printNode(x);
        } else if (x.left != null && x.right == null) {
            printInOrder(root.left);
            printNode(root);
        } else if (x.left == null && x.right != null) {
            printNode(x);
            printNode(x.right);
        } else if (x.left != null && x.right != null) {
            printNode(x.left);
            printNode(x);
            printNode(x.right);
        }
    }

    private void printNode(Node x) {
        System.out.println(x.key + ": " + x.value);
    }
}
