package kg.aloha.models;

public class Deputat {

    private long d_id;
    private String photo;
    private String fio;
    private String info;
    private long f_id;

    public long getD_id() {
        return d_id;
    }

    public void setD_id(long d_id) {
        this.d_id = d_id;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public long getF_id() {
        return f_id;
    }

    public void setF_id(long f_id) {
        this.f_id = f_id;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }


    @Override
    public String toString() {
        return "Deputat{" +
                "d_id=" + d_id +
                ", photo='" + photo + '\'' +
                ", fio='" + fio + '\'' +
                ", info='" + info + '\'' +
                ", f_id=" + f_id +
                '}';
    }
}
