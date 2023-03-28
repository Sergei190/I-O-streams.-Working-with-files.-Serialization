import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    private static final Product[] goods = {
            new Product("Хлеб", 60.0),
            new Product("Гречка", 92.60),
            new Product("Молоко", 90.0),
            new Product("Яблоко", 20.0),
            new Product("Тушенка", 385.50),
            new Product("Сгущенка", 127.80),
            new Product("Сахар", 75.0)
    };

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        String s;
        Basket shoppingCart;
        int selectedItem;
        int itemCount;

        var basketFile = new File("basket.txt");
        var jsonFile = new File("basket.json");
        var logFile = new File("log.csv");
        var clientLog = new ClientLog();

        if (jsonFile.exists()) {
            System.out.println("Загрузить корзину<ENTER>? ");
            if (scanner.nextLine().equals("")) {
                shoppingCart = Basket.loadFromJSON(jsonFile);
            } else {
                shoppingCart = new Basket(goods);
            }
        } else {
            shoppingCart = new Basket(goods);
        }

        while (true) {
            shoppingCart.printGoodsList();
            s = scanner.nextLine();
            String[] inputValues = s.split(" ");
            if (inputValues.length == 2) {
                try {
                    selectedItem = Integer.parseInt(inputValues[0]);
                    itemCount = Integer.parseInt(inputValues[1]);

                    if (selectedItem <= 0 || selectedItem > goods.length) {
                        System.out.print("\nНеправильный номер товара\n");
                        continue;
                    }
                    if (itemCount <= 0) {
                        continue;
                    }
                    shoppingCart.addToCart(selectedItem - 1, itemCount);
                    shoppingCart.saveToJSON(jsonFile);
                    clientLog.log(selectedItem, itemCount);
                } catch (NumberFormatException nfe) {
                    System.out.println("\nНужно 2 аргумента - 2 целых числа");
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            } else if (s.equals("end")) {
                break;
            }
            System.out.println("\nНужно 2 аргумента");
        }
        clientLog.exportAsCSV(logFile);
        scanner.close();
        shoppingCart.printCart();
    }
}