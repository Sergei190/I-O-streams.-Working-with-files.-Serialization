import java.io.*;

public class Basket implements Serializable {
    private final Product[] goods;
    private double nameOfProducts = 0;

    public Basket(Product[] goods) {
        this.goods = goods.clone();
    }

    public void addToCart(int productNum, int amount) {
        goods[productNum].changeItemInBasket(amount);
        nameOfProducts += goods[productNum].getPrice() * amount;
    }

    public void printGoodsList() {

        System.out.print(" №. Название. Цена за шт. В корзине. Стоимость\n" + "");

        double currentValue;
        nameOfProducts = 0;
        for (int i = 0; i < goods.length; i++) {
            currentValue = goods[i].getInBasket() * goods[i].getPrice();
            nameOfProducts += currentValue;
            System.out.printf("%2d. %13s %12.2f %10d %17.2f\n", i + 1,
                    goods[i].getName(), goods[i].getPrice(),
                    goods[i].getInBasket(), currentValue);
        }
        System.out.printf("ИТОГО Товаров в корзине на %10.2f\n\n", nameOfProducts);
        System.out.println("Добавьте товар в корзину или для завершения работы введите end.");
        System.out.print("");
    }

    public void printCart() {
        System.out.print("Ваша корзина:\n");
        for (Product item : goods) {
            if (item.getInBasket() > 0) {
                System.out.printf("%13s %3d шт. %6.2f руб/шт. %8.2f в сумме\n",
                        item.getName(), item.getInBasket(), item.getPrice(),
                        item.getInBasket() * item.getPrice());
            }
        }
        System.out.printf("ИТОГО Товаров в корзине на %10.2f\n\n", nameOfProducts);
    }

    public void saveBin(File file) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(this);
        oos.close();
    }

    public static Basket loadFromTxtFile(File textFile) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(textFile);
        ObjectInputStream ois = new ObjectInputStream(fis);
        return (Basket) ois.readObject();
    }
}

