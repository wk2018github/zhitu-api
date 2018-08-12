package zhitu.graph.tree;

import java.util.ArrayList;
import java.util.Arrays;

public class Node{
    public String id;
    public String name;
    public String des;
    public String type;
    public String[] menuList;
    public transient ArrayList<Node> children;

    public Node(String id, String name, String des, String type, String[] menuList, ArrayList<Node> children) {
        this.id = id;
        this.name = name;
        this.des = des;
        this.type = type;
        this.menuList = menuList;
        this.children = children;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String[] getMenuList() {
        return menuList;
    }

    public void setMenuList(String[] menuList) {
        this.menuList = menuList;
    }

    public ArrayList<Node> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<Node> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "Node{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", des='" + des + '\'' +
                ", type='" + type + '\'' +
                ", menuList=" + Arrays.toString(menuList) +
                ", children=" + children +
                '}';
    }

    public void traverse(NodeVisitor nodeVisitor){
        nodeVisitor.visit(this);
        for(Node child: this.children){
            child.traverse(nodeVisitor);
        }
    }
}
