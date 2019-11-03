import java.util.Scanner;

public class RedBlackTree {

  static final boolean RED = true;
  static final boolean BLACK = false;
  static Node root = null;

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    while (true) {
      int n = sc.nextInt();
      if (root == null) {
        root = new Node(n, null);
      } else {
        if (n == 0) {
          break;
        }
        root.insertNode(n);
      }
    }

    root.print(root);
//    root.deleteNode(80);
//    root.deleteNode(20);
    root.print(root);

  }

  static class Node {

    int val;
    Node left, right;
    boolean color;

    Node() {
    }

    Node(int val, Node parent) {
      this.val = val;
      this.color = parent == null ? BLACK : RED;
    }

    void deleteNode(int value) {
      Node discoverer = root;
      Node n, p, s, x = null;
      while (discoverer != null) {
        if(discoverer.val == value){
          x = discoverer;
          break;
        }
        if (value < discoverer.val) {
          discoverer = discoverer.left;
        } else {
          discoverer = discoverer.right;
        }
      }
      if (x == null) {
        return;
      }
      x = discoverer;
      n = findNodeToBeDeleted(x);
      if (n == root) {
        root = null;
        return;
      }

      if ((n.color || x.color) == RED) {
        p = getParent(n);
        x.color = BLACK;
        x.val = n.val;
      } else {
        p = getParent(n);
        s = (p.left == n) ? p.right : p.left;

        fixDoubleBlack(p, s);

        x.val = n.val;

      }
      if (p.left == n) {
        p.left = (n.left != null) ? n.left : n.right;
      } else {
        p.right = (n.left != null) ? n.left : n.right;
      }

    }

    private void fixDoubleBlack(Node p, Node s) {

      char direction = p.left == s ? 'R' : 'L';

      if (s.color == RED) {
        rotate(p, s, direction);
        s.color = BLACK;
      } else {
        boolean hasSiblingAnyRedChild =
            (s.left != null && s.left.color == RED) || (s.right != null && s.right.color == RED);
        if (hasSiblingAnyRedChild) {
          boolean isReorderingRequired =
              (direction == 'L' && s.right == null) || (direction == 'R' && s.left == null);

          if(isReorderingRequired) reorder(s, direction);

          rotate(p, s, direction);
          if (direction == 'L') {
            s.right.color = BLACK;
          } else {
            s.left.color = BLACK;
          }

        } else {
          s.color = RED;
          if (p.color == RED) {
            p.color = BLACK;
          } else if (p != root) {
            Node gp = getParent(p);
            s = (gp.left == p) ? gp.right : gp.left;
            fixDoubleBlack(gp, s);
          }
        }
      }
    }

    private void reorder(Node node, char direction) {
        int tempVal;
        if (direction == 'L') {
          node.right = node.left;
          node.left = null;
          tempVal = node.val;
          node.val = node.right.val;
          node.right.val = tempVal;
        } else {
          node.left = node.right;
          node.right = null;
          tempVal = node.val;
          node.val = node.left.val;
          node.left.val = tempVal;
        }
    }

    private void rotate(Node p, Node s, char direction) {
      Node gp = null;
      boolean isParentLeftChild = true;
      if (p == root) {
        root = s;
      } else {
        gp = getParent(p);
        isParentLeftChild = gp.left == p ? true : false;
      }
      if (direction == 'L') {
        p.right = s.left;
        s.left = p;
      } else {
        p.left = s.right;
        s.right = p;
      }
      if (gp != null) {
        if (isParentLeftChild) {
          gp.left = s;
        } else {
          gp.right = s;
        }
      }
    }

    private Node findNodeToBeDeleted(Node x) {
      if (x.left != null) {
        while (x.right != null) {
          x = x.right;
        }
      } else {
        while (x.left != null) {
          x = x.left;
        }
      }

      return x;
    }

    void insertNode(int value) {
      Node discoverer = root;
      Node x = null, y = null, z = null, s;
      while (true) {
        if (value < discoverer.val) {
          if (discoverer.left != null) {
            z = discoverer;
            y = discoverer.left;
            discoverer = discoverer.left;
          } else {
            x = discoverer.left = new Node(value, discoverer);
          }
        } else {
          if (discoverer.right != null) {
            z = discoverer;
            y = discoverer.right;
            discoverer = discoverer.right;
          } else {
            x = discoverer.right = new Node(value, discoverer);
          }
        }
        if (x != null) {
          if (discoverer.color != x.color) {
            return;
          }
          s = z != null ? (z.left == y ? z.right : z.left) : null;
          break;
        }
      }
      checkInsertProperties(x, y, z, s);
    }

    void checkInsertProperties(Node x, Node y, Node z, Node s) {

      boolean isUncleRed = (s != null && s.color) ? RED : BLACK;

      if (isUncleRed) {
        s.color = y.color = BLACK;
        z.color = RED;
        if (z == root) {
          z.color = BLACK;
        } else {
          y = getParent(z);
          if (y.color == RED) {
            x = z;
            z = getParent(z);
            s = z != null ? (z.left == y ? z.right : z.left) : null;
            checkInsertProperties(x, y, z, s);
          }
        }
      } else {
        char direction = z.right == y ? 'L' : 'R';
        boolean isReorderingRequired =
            (direction == 'L' && y.right == null) || (direction == 'R' && y.left == null);

        if(isReorderingRequired) reorder(y, direction);
        Node temp;
        temp = new Node();
        swap(y, z, temp);
      }
    }

    private Node getParent(Node child) {
      Node discoverer = root;
      while (discoverer.left != child && discoverer.right != child) {
        if (discoverer.val < child.val) {
          discoverer = discoverer.right;
        } else {
          discoverer = discoverer.left;
        }
      }
      return discoverer;
    }

    void swap(Node n1, Node n2, Node temp) {
      boolean isLeftRotation = (n2.right == n1) ? true : false;
      temp.color = n1.color;
      temp.left = n1.left;
      temp.right = n1.right;
      n1.color = n2.color;
      n2.color = temp.color;
      if (n2 != root) {
        Node parent = getParent(n2);
        if (parent.left == n2) {
          parent.left = n1;
        } else {
          parent.right = n1;
        }
      } else {
        root = n1;
      }
      if (isLeftRotation) {
        n1.left = n2;
        n2.right = temp.left;
      } else {
        n1.right = n2;
        n2.left = temp.right;
      }
    }

    void print(Node node) {
      if (node.left != null) {
        print(node.left);
      }
      System.out.println(node.val + " : " + (node.color ? "RED" : "BLACK"));
      if (node.right != null) {
        print(node.right);
      }
    }
  }
}

