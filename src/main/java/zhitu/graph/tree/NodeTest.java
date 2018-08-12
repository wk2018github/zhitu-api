package zhitu.graph.tree;

import com.google.gson.Gson;
import zhitu.util.StringHandler;

import java.util.ArrayList;
import java.util.HashMap;

public class NodeTest {

    public static String[] strings = {"功能科目","结算方式","经济科目","项目分类","预算单位","预算类型","预算项目","支付方式",
            "指标来源","资金归属财政内设机构","资金性质"};
    public static Node node0 = new Node("0", "科处室指标", "流程","gray", strings, new ArrayList<>());
    public static Node node1 = new Node("1", "单位指标", "流程","gray", strings, new ArrayList<>());
    public static Node node2 = new Node("2", "计划", "流程","gray", strings, new ArrayList<>());
    public static Node node3 = new Node("3", "支付", "流程","gray", strings, new ArrayList<>());

    public static Node nodes;
    public static int idNumber = 4;

    public NodeTest(){
        node0.children.add(node1);
        node1.children.add(node2);
        node2.children.add(node3);
    }

    public static void main(String[] args) {

    }

    public static void test(){
        HashMap<String,ArrayList> map = toD3Data(node0);
        Gson gson = new Gson();
        String mapToString = gson.toJson(map);
        System.out.println(mapToString);
    }

    //根据节点名称查节点
    public static Node queryNodeById(String id){
        if(StringHandler.isEmptyOrNull(id)){
            return null;
        }
        node0.traverse(new NodeVisitor(){
            @Override
            public void visit(Node node) {
                if(id.equals(node.id)){
                    nodes = node;
                }
            }
        });
        return nodes;
    }

    //根据孩子节点名称查父节点
    public static Node queryNodeByChildrenId(String id){
        if(StringHandler.isEmptyOrNull(id)){
            return null;
        }
        node0.traverse(new NodeVisitor(){
            @Override
            public void visit(Node node) {
                for(Node childNode : node.children){
                    if(id.equals(childNode.id)){
                        nodes = node;
                        break;
                    }
                }
            }
        });
        return nodes;
    }

    public static void addNode(String parentId, String name,String[] menuList, String type){
        if(StringHandler.isEmptyOrNull(parentId)||StringHandler.isEmptyOrNull(name)){
            return ;
        }
        Node node = queryNodeById(parentId);
        if(StringHandler.isEmptyOrNull(node)){
            System.out.println("节点未找到！");
            return;
        }
        Node newNode = new Node(Integer.toString(idNumber),name,"流程",type,menuList,node.children);
        idNumber++;//节点id自加
        ArrayList<Node> childrenList = new ArrayList<Node>();
        childrenList.add(newNode);
        node.setChildren(childrenList);
    }

    public static void deleteNode(String id){
        Node parentNode = queryNodeByChildrenId(id);
        Node node = queryNodeById(id);
        if(StringHandler.isEmptyOrNull(parentNode)||StringHandler.isEmptyOrNull(node)){
            System.out.println("节点未找到！");
            return;
        }
        parentNode.setChildren(node.getChildren());
    }


    public static HashMap<String,ArrayList> toD3Data(Node node){
        HashMap<String,ArrayList> hashMap = new HashMap<String,ArrayList>();
        final ArrayList<Node> nodeInfos = new ArrayList<Node>();
        final ArrayList<Link> edgeInfos = new ArrayList<Link>();

        node.traverse(new NodeVisitor(){
            @Override
            public void visit(Node node) {
                nodeInfos.add(node);
                Link link;
                for(Node child: node.children){
                    link = new Link(node.name,child.name,"");
                    edgeInfos.add(link);
                }
            }
        });
        hashMap.put("node",nodeInfos);
        hashMap.put("link",edgeInfos);

        return hashMap;
    }
}
