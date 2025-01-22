package servlet;

import dto.BookDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/books")
public class BookServlet extends CommonServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<BookDto> books = bookService.findAll();
        sendResponse(response, books, HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BookDto book = new BookDto();
        String authorName = request.getParameter("authorsName");
        int authorId = authorService.getAuthorId(authorName);
        book.setAuthorId(authorId);
        book.setBookTitle(request.getParameter("bookTitle"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        book.setQuantity(quantity);
        if ((bookService.save(book))==null) {
            sendResponse(response, "Невозможно добавить книгу " + request.getParameter("bookTitle") +
                    ", потому что она уже существует или название не введено", HttpServletResponse.SC_BAD_REQUEST);
        } else {
            sendResponse(response, "Книга " + book.getBookTitle() + " успешно добавлена", HttpServletResponse.SC_OK);
        }
    }
}
