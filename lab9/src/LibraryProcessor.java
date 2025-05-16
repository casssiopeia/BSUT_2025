import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.*;
import java.io.File;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

public class LibraryProcessor {
    public static void main(String[] args) {
        try {
            File inputFile = new File("data/library.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            NodeList bookList = doc.getElementsByTagName("book");
            double totalPrice = 0.0;
            int count = bookList.getLength();

            System.out.println("Список книг:\n");

            // Сбор уникальных жанров
            Set<String> genres = new TreeSet<>();

            for (int i = 0; i < count; i++) {
                Node bookNode = bookList.item(i);
                if (bookNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element book = (Element) bookNode;
                    String title = book.getElementsByTagName("title").item(0).getTextContent();
                    String author = book.getElementsByTagName("author").item(0).getTextContent();
                    String year = book.getElementsByTagName("year").item(0).getTextContent();
                    String genre = book.getElementsByTagName("genre").item(0).getTextContent();
                    String price = book.getElementsByTagName("price").item(0).getTextContent();

                    genres.add(genre.trim().toLowerCase());

                    totalPrice += Double.parseDouble(price);

                    System.out.printf("Название: %s\nАвтор: %s\nГод: %s\nЖанр: %s\nЦена: %s\n\n",
                            title, author, year, genre, price);
                }
            }

            double avg = totalPrice / count;
            DecimalFormat df = new DecimalFormat("#.00");
            System.out.println("Средняя цена книг: " + df.format(avg));

            // Выбор жанра пользователем
            Scanner scanner = new Scanner(System.in);
            List<String> genreList = new ArrayList<>(genres);
            System.out.println("\nДоступные жанры:");
            for (int i = 0; i < genreList.size(); i++) {
                System.out.printf("%d. %s\n", i + 1, capitalize(genreList.get(i)));
            }

            System.out.print("Введите номер жанра для фильтрации: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline
            if (choice < 1 || choice > genreList.size()) {
                System.out.println("Неверный выбор жанра.");
                return;
            }

            String selectedGenre = genreList.get(choice - 1);
            System.out.println("\nКниги в жанре: " + capitalize(selectedGenre) + "\n");

            for (int i = 0; i < count; i++) {
                Element book = (Element) bookList.item(i);
                String genre = book.getElementsByTagName("genre").item(0).getTextContent().trim().toLowerCase();
                if (genre.equals(selectedGenre)) {
                    String title = book.getElementsByTagName("title").item(0).getTextContent();
                    System.out.println("Название: " + title);
                }
            }

        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private static String capitalize(String str) {
        if (str == null || str.isEmpty()) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}

