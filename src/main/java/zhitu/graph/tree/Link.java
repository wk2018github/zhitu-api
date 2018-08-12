package zhitu.graph.tree;

public class Link {
    public String source;
    public String target;
    public String name;

    public Link(String source, String target, String name) {
        this.source = source;
        this.target = target;
        this.name = name;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Link{" +
                "source='" + source + '\'' +
                ", target='" + target + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
