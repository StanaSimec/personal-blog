package cz.simec.blog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public class ArticleRepository {

    private static final RowMapper<Article> rowMapper = (rs, rowNum) -> {
        Article article = new Article();
        article.setId(rs.getInt("id"));
        article.setHeader(rs.getString("header"));
        String content = rs.getString("content");
        if (content != null) {
            article.setContent(content);
        }
        article.setCreatedAt(rs.getTimestamp("created_at").toInstant());
        return article;
    };

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ArticleRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Article> findAll() {
        String sql = "SELECT id, header, content, created_at FROM article";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public Article findById(int id) {
        String sql = "SELECT id, header, content, created_at FROM article WHERE id = ? LIMIT 1";
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    public void update(Article article) {
        String sql = "UPDATE article SET header = ?, content = ? WHERE id = ?";
        jdbcTemplate.update(sql, article.getHeader(), article.getContent(), article.getId());
    }

    public void delete(int id) {
        String sql = "DELETE FROM article WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public void create(Article article) {
        jdbcTemplate.update("INSERT INTO article (header, content, created_at) VALUES (?, ?, ?)",
                article.getHeader(),
                article.getContent(),
                new Timestamp(article.getCreatedAt().toEpochMilli())
        );
    }

    public boolean isNotUsedId(int id) {
        String sql = "SELECT COUNT(*) as count FROM article WHERE id = ?";
        Integer idCount = jdbcTemplate.queryForObject(sql, Integer.class, id);
        if (idCount == null) {
            return true;
        }
        return idCount == 0;
    }
}
