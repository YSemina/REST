package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import service.AuthorService;
import service.BookService;

import java.io.IOException;

public class CommonServlet extends HttpServlet {
    protected final ObjectMapper objectMapper = new ObjectMapper();
    AuthorService authorService = AuthorService.getInstance();
    BookService bookService = BookService.getInstance();

    protected CommonServlet() {
    }

    protected void sendResponse(HttpServletResponse response, Object data, int status) throws IOException {
        response.setStatus(status);
        response.setContentType("text/json");
        response.setCharacterEncoding("UTF-8");
        objectMapper.writeValue(response.getWriter(), data);
    }
}
