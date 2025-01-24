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
        String requestURI = request.getRequestURI();
        if (requestURI.contains("/authors")) {
            String idStr = request.getParameter("id");
            int id = parseInt(idStr);
            if (id == 0) {
                List<AuthorDto> authors = authorService.findAll();
                sendResponse(response, authors, HttpServletResponse.SC_OK);
            } else {
                AuthorDto author = authorService.findById(id);
                sendResponse(response, author, HttpServletResponse.SC_OK);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        if (name != null) {
            AuthorDto author = new AuthorDto();
            author.setName(name);
            if ((authorService.save(author)) == null) {
                sendResponse(response, "Невозможно добавить автора " + name +
                        ", потому что он уже существует", HttpServletResponse.SC_BAD_REQUEST);
            } else {
                sendResponse(response, "Автор " + author.getName() + " успешно добавлен",
                        HttpServletResponse.SC_OK);
            }
        } else {
            sendResponse(response, "Чтобы добавить автора, введите имя", HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idStr = request.getParameter("id");
        int id = parseInt(idStr);
        String name = request.getParameter("name");
        AuthorDto author = new AuthorDto();
        author.setName(name);
        author.setId(id);
        if(id==0||name==null){
            sendResponse(response, "Чтобы обновить данные, введите их!", HttpServletResponse.SC_BAD_REQUEST);
        }
        else{
            if((authorService.update(author))){
                sendResponse(response, "Данные обновлены", HttpServletResponse.SC_OK);
            }
            else {
                sendResponse(response, "Нет автора с введенным id!", HttpServletResponse.SC_BAD_REQUEST);
            }
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idStr = request.getParameter("id");
        int id = parseInt(idStr);
        if (id == 0) {
            sendResponse(response, "Чтобы удалить автора, введите id автора", HttpServletResponse.SC_BAD_REQUEST);
        }
        else {
            if(authorService.delete(id)){
                sendResponse(response,"Автор c id = " + id + " успешно удален",HttpServletResponse.SC_OK);
            }
            else {
                sendResponse(response,"Автора с id = " + id + " не существует", HttpServletResponse.SC_BAD_REQUEST);
            }
        }
    }
}
