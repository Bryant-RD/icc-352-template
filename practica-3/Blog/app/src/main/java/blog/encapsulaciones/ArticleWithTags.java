package blog.encapsulaciones;

import java.util.List;

public class ArticleWithTags {
    private Article article;
    private List<Tag> tags;

    public ArticleWithTags(Article article, List<Tag> tags) {
        this.article = article;
        this.tags = tags;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
}

