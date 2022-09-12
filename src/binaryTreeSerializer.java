import java.util.List;
import java.util.ArrayList;

public class binaryTreeSerializer implements TreeSerializer {

    public binaryTreeSerializer()
    {

    }

    @Override
    public String serialize(Node root) {
        if(root == null)
        {
            return "null";
        }

        String left = serialize(root.left);
        String right = serialize(root.right);
        String serial = root.num + "," + left + "," + right;
        return serial;
    }

    @Override
    public Node deserialize(String str) {
        String[] serial = str.split(","); // Returns a String[] where each entry represents a Node
        List<String> list = new ArrayList<>();
        for(String val: serial)
        {
            list.add(val);
        }

        return reconstruct(list);
    }

    private Node reconstruct(List<String> list)
    {
        String str = list.remove(0);
        if(str.equals("null"))
        {
            return null;
        }

        int val = Integer.parseInt(str);
        Node node = new Node(val);
        node.left = reconstruct(list);
        node.right = reconstruct(list);

        return node;
    }
}
