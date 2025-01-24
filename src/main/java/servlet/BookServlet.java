package servlet;

import dto.AuthorDto;
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
        String requestURI = request.getRequestURI();
        if (requestURI.contains("/books")) {
            String idStr = request.getParameter("authorId");
            int authorId = parseInt(idStr);
            if (authorId == 0) {
                List<BookDto> books = bookService.findAll();
                sendResponse(response, books, HttpServletResponse.SC_OK);
            } else {
                List<BookDto> books = bookService.findByAuthor(authorId);
                if(!books.isEmpty()) {
                    sendResponse(response, books, HttpServletResponse.SC_OK);
                }
                else{
                    sendResponse(response, "Автора нет в БД или нет книг этого автора в библиотеке",
                            HttpServletResponse.SC_BAD_REQUEST);
                }
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String authorIdStr = request.getParameter("authorId");
        int authorId = parseInt(authorIdStr);
        String title = request.getParameter("title");
        String quantityStr = request.getParameter("quantity");
        int quantity = parseInt(quantityStr);
        if ((authorId != 0) && (title != null) && (quantity != 0)) {
            BookDto book = new BookDto();
            book.setAuthorId(authorId);
            book.setBookTitle(title);
            book.setQuantity(quantity);
            if ((bookService.save(book)) == null) {
                sendResponse(response, "Невозможно добавить книгу '" + title +
                        "', потому что она уже существует", HttpServletResponse.SC_BAD_REQUEST);
            } else {
                sendResponse(response, "Книга '" + title + "' успешно добавлена",
                        HttpServletResponse.SC_OK);
            }
        } else {
            sendResponse(response, "Чтобы добавить книгу, введите название", HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idStr = request.getParameter("id");
        int id = parseInt(idStr);
        String title = request.getParameter("title");
        String quantityStr = request.getParameter("quantity");
        int quantity = parseInt(quantityStr);
        BookDto book = new BookDto();
        book.setId(id);
        book.setBookTitle(title);
        book.setQuantity(quantity);
        if(id==0||title==null||quantity==0){
            sendResponse(response, "Чтобы обновить данные, введите их!", HttpServletResponse.SC_BAD_REQUEST);
        }
        else{
            if((bookService.update(book))){
                sendResponse(response, "Данные обновлены", HttpServletResponse.SC_OK);
            }
            else {
                sendResponse(response, "Нет книги с введенным id!", HttpServletResponse.SC_BAD_REQUEST);
            }
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idStr = request.getParameter("id");
        int id = parseInt(idStr);
        if (id == 0) {
            sendResponse(response, "Чтобы удалить книгу, введите id книги", HttpServletResponse.SC_BAD_REQUEST);
        }
        else {
            if(bookService.delete(id)){
                sendResponse(response,"Книга c id = " + id + " успешно удалена",HttpServletResponse.SC_OK);
            }
            else {
                sendResponse(response,"Книги с id = " + id + " не существует", HttpServletResponse.SC_BAD_REQUEST);
            }
        }
    }
}
