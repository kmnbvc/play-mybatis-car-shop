package v1.post;

public class PostData {

    public PostData() {
    }

    public PostData(String title, String body) {
        this.title = title;
        this.body = body;
    }

    public Long id;
    public String title;
    public String body;
}
