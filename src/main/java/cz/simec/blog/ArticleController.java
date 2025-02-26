package cz.simec.blog;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ArticleController {

    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/")
    public String home(Model model) {
        List<Article> articleList = articleService.findAll();
        model.addAttribute("articles", articleList);
        return "home";
    }

    @GetMapping("/admin")
    public String admin(Model model) {
        List<Article> articleList = articleService.findAll();
        model.addAttribute("articles", articleList);
        return "admin";
    }

    @GetMapping("/article/{id}")
    public String detail(@PathVariable Integer id, Model model) {
        Article article = articleService.findByIdOrThrow(id);
        model.addAttribute("article", article);
        return "detail";
    }

    @GetMapping("/admin/article/create")
    public String createForm(Model model) {
        model.addAttribute("article", new Article());
        return "create";
    }

    @PostMapping("/admin/article/create")
    public String create(@Valid Article article, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "create";
        }

        articleService.create(article);
        return "redirect:/admin";
    }

    @GetMapping("/admin/article/update/{id}")
    public String updateForm(@PathVariable Integer id, Model model) {
        Article article = articleService.findByIdOrThrow(id);
        model.addAttribute("article", article);
        return "update";
    }

    @PostMapping("/admin/article/update")
    public String update(@Valid Article article, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "update";
        }
        articleService.update(article);
        return "redirect:/article/" + article.getId();
    }

    @PostMapping("/admin/article/delete/{id}")
    public String delete(@PathVariable Integer id) {
        if (id == null) {
            return "redirect:/admin";
        }
        articleService.delete(id);
        return "redirect:/admin";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/logout")
    public String logout() {
        return "logout";
    }

    @ExceptionHandler(ArticleNotFoundException.class)
    public String handleArticleNotFound() {
        return "not-found";
    }
}
