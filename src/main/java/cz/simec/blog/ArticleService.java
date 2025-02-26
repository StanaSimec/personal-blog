package cz.simec.blog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public List<Article> findAll() {
        return articleRepository.findAll();
    }

    public Article findByIdOrThrow(int id) {
        if (isNotUsedId(id)) {
            throw new ArticleNotFoundException("Article with id " + id + " was not found");
        }
        return articleRepository.findById(id);
    }

    public void create(Article article) {
        article.setCreatedAt(Instant.now());
        articleRepository.create(article);
    }

    public void update(Article article) {
        if (isNotUsedId(article.getId())) {
            throw new ArticleNotFoundException("Article with id + " + article.getId() + " was not found");
        }
        articleRepository.update(article);
    }

    public void delete(int id) {
        if (isNotUsedId(id)) {
            throw new ArticleNotFoundException("Article with id " + id + " was not found");
        }
        articleRepository.delete(id);
    }

    public boolean isNotUsedId(int id) {
        return articleRepository.isNotUsedId(id);
    }
}
