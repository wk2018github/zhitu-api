package zhitu.graph.tree;

public class Test {
    public static void main(String[] args) {
        String parentId = "0";
        String name = "人事教育处";
        String[] menuList = queryMenuList(name);
        NodeTest nodeTest = new NodeTest();
        nodeTest.test();

        nodeTest.addNode(parentId, name, menuList, "red");
        nodeTest.test();

        nodeTest.deleteNode("4");
        nodeTest.test();
    }

     public static String[] queryMenuList(String name){
        String[] menuList = null ;
        return menuList;
    }

}
