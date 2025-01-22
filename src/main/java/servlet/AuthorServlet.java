package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import dto.AuthorDto;

import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/authors")
public class AuthorServlet extends CommonServlet {



    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<AuthorDto> authors = authorService.findAll();
        sendResponse(response, authors, HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AuthorDto author = new AuthorDto();
        author.setName(request.getParameter("authorsName"));

        if ((authorService.save(author))==null) {
            sendResponse(response, "Невозможно добавить автора " + request.getParameter("authorsName") +
                    ", потому что он уже существует или имя не введено", HttpServletResponse.SC_BAD_REQUEST);
        } else {
            sendResponse(response, "Автор " + author.getName() + " успешно добавлен", HttpServletResponse.SC_OK);
        }
    }
}
