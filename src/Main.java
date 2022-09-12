import java.util.Map;

public class Main
{
    public static void main(String[] args)
    {
        /*Assertion Testing for JSONParser.java

        String input1 = "{\"debug\":\"on\",\"window\":{\"title\":\"sample\",\"size\":500}}";
        String input2 = "{\"debug\":\"on\",\"window\":{\"title\":\"sample\",\"size\":{\"debug\":\"off\"}}}";

        Map<String,Object> output = JSONParser.parse(input1);
        assert output.get("debug").equals("on");
        assert ((Map<String, Object>)(output.get("window"))).get("title").equals("sample");
        assert ((Map<String, Object>)(output.get("window"))).get("size").equals(500);

        output = JSONParser.parse(input2);
        Map<String,Object> test = (Map<String, Object>) output.get("window");
        assert ((Map<String, Object>)(test.get("size"))).get("debug").equals("off");*/

        /*Assertion Testing for TreeSerializer (without cycles)

        Node root = new Node(1);
        root.left = new Node(2);
        root.right = new Node(3);
        root.right.left = new Node(4);
        root.right.right = new Node(5);
        binaryTreeSerializer bts = new binaryTreeSerializer();

        String serializedTree = bts.serialize(root);
        System.out.println(serializedTree); // Should print 1,2,null,null,3,4,null,null,5,null,null
        Node root2 = bts.deserialize(serializedTree);

        String serializedTree2 = bts.serialize(root2);
        System.out.println(serializedTree2); // Should print 1,2,null,null,3,4,null,null,5,null,null*/
    }
}