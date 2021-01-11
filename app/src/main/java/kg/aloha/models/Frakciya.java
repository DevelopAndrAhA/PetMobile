package kg.aloha.models;

public class Frakciya {

    private long f_id;
    private String name;

    public long getF_id() {
        return f_id;
    }

    public void setF_id(long f_id) {
        this.f_id = f_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Frakciya{" +
                "f_id=" + f_id +
                ", name='" + name + '\'' +
                '}';
    }
}
